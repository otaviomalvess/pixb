package com.bank;

import java.util.HashMap;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class BankPixControl {

    @Inject
    @RestClient
    private BankPixService pixService;

    @Inject
    private BankDB db;

    @Inject
    private Logger logger;

    @Inject
    private AccountControl accountControl;

    private HashMap<Long, BankPixRollbacker> rollbackers = new HashMap<>();

    /**
     * Create pix request.
     *
     * @see BankPixDTO
     */
    public void createPixRequests(final BankPixDTO[] pixDTOs) {
        if (pixDTOs == null || pixDTOs.length == 0) {
            logger.info("(createPixRequest) Given keys array is null or empty.");
            return;
        }

        logger.info("(createPixRequest) Start.");

        Response resp;
        try {
            final String[] keys = new String[pixDTOs.length];
            for (int i = 0; i < keys.length; i++) {
                keys[i] = pixDTOs[i].pixKey;
            }

            resp = pixService.getEntries(keys);

            if (resp.getStatus() != 200) {
                throw new Exception("Status code " + resp.getStatus());
            }
    
            if (!resp.hasEntity()) {
                throw new Exception("Empty body.");
            }
        } catch (final Exception e) {
            logger.error("(createPixRequest)" + e);
            return;
        }

        logger.info("(createPixRequest) Consult key: OK.");

        final BankPix[] pixes;
        try {
            pixes = resp.readEntity(BankPix[].class);
        } catch (final Exception e) {
            logger.error("(createPixRequest) " + e);
            return;
        }

        logger.info("(createPixRequest) Convert JSON to BankPix: OK.");

        // Draw from accounts balances
        for (final BankPix pix : pixes) {
            if (pix == null) {
                logger.error("(createPixRequest) Null pix.");
                continue;
            }

            final Account account = accountControl.getAccount(pix.cpf);
            if (account == null) {
                continue;
            }

            for (final BankPixDTO dto : pixDTOs) {
                if (dto.pixKey.equals(pix.key)) {
                    pix.value = dto.value;
                    break;
                }
            }

            try {
                account.draw(pix.value);
                db.updateAccountBalance(account);
            } catch (final Exception e) {
                logger.error("(createPixRequest) " + e);
                continue;
            }

            // This returns a value if an entry with the key existed.
            rollbackers.put(pix.endToEndId, new BankPixRollbacker(account, pix.value));

            logger.info("(createPixRequest) Drew from account " + account.cpf + ". Backed up operation in rollbackers map.");
        }

        try {
            resp = pixService.createPixRequests(pixes);
            
            if (resp.getStatus() != 200) {
                throw new Exception("Pix request.");
            }
        } catch (final Exception e) {
            logger.error("(createPixRequest) " + e);
            return;
        }

        logger.info("(createPixRequest) Pix request: OK.");
        logger.info("(createPixRequest) SUCCESS!");
    }

    /**
     * Consult pix requests.
     */
    public void consultPixRequests() {
        logger.info("(consultPixRequest) Start.");

        Response resp;

        try {
            resp = pixService.consultPixRequests();

            if (resp.getStatus() != 200) {
                throw new Exception("Pix request.");
            }
    
            if (!resp.hasEntity()) {
                throw new Exception("No entity.");
            }
        } catch (final Exception e) {
            logger.error("(consultPixRequest) " + e);
            return;
        }

        logger.info("(consultPixRequest) Consult pix request: OK.");

        final BankPix[] pixes;
        try {
            pixes = resp.readEntity(BankPix[].class);
        } catch (final Exception e) {
            logger.error("(consultPixRequest) " + e);
            return;
        }

        logger.info("(consultPixRequest) Convert JSON to BankPix: OK.");

        final BankPixRequestUpdateDTO[] resolvedPixes = new BankPixRequestUpdateDTO[pixes.length];
        
        for (int i = 0; i < resolvedPixes.length; i++) {
            final BankPix pix = pixes[i];
            if (pix == null) {
                logger.error("(createPixRequest) Null pix.");
                continue;
            }
            
            final BankPixRequestUpdateDTO pixUpdate = new BankPixRequestUpdateDTO(pix.endToEndId);
            resolvedPixes[i] = pixUpdate;

            final Account account = accountControl.getAccount(pix.cpf);

            if (account == null) {
                continue;
            }

            try {
                account.deposit(pix.value);
                db.updateAccountBalance(account);
                pixUpdate.resolved = BankPix.ResolvedStates.SUCCESS;
            } catch (final Exception e) {
                pixUpdate.resolved = BankPix.ResolvedStates.FAIL;
            }
        }

        try {
            resp = pixService.closePixRequests(resolvedPixes);

            if (resp.getStatus() != 200) {
                throw new Exception("(consultPixRequest) Pix request status: " + resp.getStatus());
            }
        } catch (final Exception e) {
            logger.error("(consultPixRequest) " + e);
            return;
        }
        
        logger.info("(consultPixRequest) Close pix request: OK.");
        logger.info("(consultPixRequest) Pix request: OK.");
        logger.info("(consultPixRequest) SUCCESS!");
    }

    /**
     * Check pix request state.
     */
    public void consultUpdatedPixes() {
        logger.info("(consultUpdatedPixes) Start.");

        final Response resp;

        try {
            resp = pixService.consultUpdatedPixes();

            if (resp.getStatus() != 200) {
                throw new Exception("(consultUpdatedPixes) Pix requests states.");
            }
    
            if (!resp.hasEntity()) {
                throw new Exception("(consultUpdatedPixes) No entity.");
            }
        } catch (final Exception e) {
            logger.error("(consultUpdatedPixes) " + e);
            return;
        }

        logger.info("(consultUpdatedPixes) Consult updated pixes: OK.");

        final BankPix[] pixes;
        try {
            pixes = resp.readEntity(BankPix[].class);
        } catch (final Exception e) {
            logger.error("(consultUpdatedPixes) " + e);
            return;
        }

        logger.info("(consultUpdatedPixes) Convert JSON to BankPix: OK.");

        for (final BankPix pix : pixes) {

            switch (pix.resolved) {
                case BankPix.ResolvedStates.FAIL:
                    final BankPixRollbacker rollbacker = rollbackers.get(pix.endToEndId);
                    try {
                        rollbacker.account.deposit(rollbacker.value);
                        db.updateAccountBalance(rollbacker.account);
                    } catch (final Exception e) {
                        logger.error("(consultUpdatedPixes)" + e);
                    }
                    rollbackers.remove(pix.endToEndId);
                    break;
                
                case BankPix.ResolvedStates.SUCCESS:
                    rollbackers.remove(pix.endToEndId);
                    break;
                
                case BankPix.ResolvedStates.REQUEST:
                    logger.info("(consultUpdatedPixes) Pix with Request state found in consult.");
                    break;
            }
        }

        logger.info("(consultUpdatedPixes) End.");
    }
}
