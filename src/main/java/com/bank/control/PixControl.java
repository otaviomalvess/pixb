package com.bank.control;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.bank.model.Account;
import com.bank.model.Entry;
import com.bank.model.Pix;
import com.bank.model.PixDTO;
import com.bank.model.PixRequestUpdateDTO;
import com.bank.model.PixRollbacker;
import com.bank.service.PixService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

/**
 * PixControl is a class to handle any pix related processes.
 */
@ApplicationScoped
public class PixControl {

    @Inject
    @RestClient
    private PixService pixService;

    @Inject
    private AccountControl accountControl;
    
    @Inject
    private Logger logger;

    private HashMap<Long, PixRollbacker> rollbackers = new HashMap<>();

    /**
     * Draws from the accounts balances and requests the creation of new Pix Requests.
     * The requests are only built with the returned consulted entries.
     * <p>
     * All successful account balances drawn operation is saved for rollbacking purposes.
     * 
     * @param pixDTOs The pix DTO object
     * @see PixDTO
     */
    public void createPixRequests(final PixDTO[] pixDTOs) {
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

            if (resp.getStatus() != 200)
                throw new Exception("Status code " + resp.getStatus());
            if (!resp.hasEntity())
                throw new Exception("Empty body.");
            
        } catch (final Exception e) {
            logger.error("(createPixRequest)" + e);
            return;
        }

        logger.info("(createPixRequest) Consult key: OK.");

        final Pix[] pixes;
        final Entry[] entries;
        try {
            pixes = resp.readEntity(Pix[].class);
            entries = resp.readEntity(Entry[].class);
        } catch (final Exception e) {
            logger.error("(createPixRequest) " + e);
            return;
        }

        logger.info("(createPixRequest) Convert JSON to BankPix: OK.");

        // Draw from accounts balances
        final Map<String, Double> pixDTOmap = new ObjectMapper()
                        .convertValue(pixDTOs, new TypeReference<Map<String, Double>>() {});
        
        for (final Entry entry : entries) {
            if (entry == null) {
                logger.error("(createPixRequest) Null entry.");
                continue;
            }

            final Pix pix = new Pix(
                    entry.endToEndId,
                    entry.owner,
                    pixDTOmap.get(entry.key),
                    Pix.ResolvedStates.REQUEST,
                    entry.getBankingDomicile(),
                    entry.getCPF()
            );

            try {
                accountControl.drawFrom(pix.getCPF(), pix.value);
            } catch (final Exception e) {
                logger.error("(createPixRequest) " + e);
                continue;
            }

            final Account account = accountControl.getAccount(entry.getCPF());
            final PixRollbacker value = rollbackers.put(entry.endToEndId, new PixRollbacker(account, pix.value));
            if (value != null) {
                logger.warn("(createPixRequest) There was a previous value associated to the key. K: " + entry.endToEndId + ", V: " + entry.endToEndId);
            }

            logger.info("(createPixRequest) Drew from account " + account.cpf + ". Backed up operation in rollbackers map.");
        }

        try {
            resp = pixService.createPixRequests(pixes);
            
            if (resp.getStatus() != 200)
                throw new Exception("Pix request.");
            
        } catch (final Exception e) {
            logger.error("(createPixRequest) " + e);
            return;
        }

        logger.info("(createPixRequest) Pix request: OK.");
        logger.info("(createPixRequest) SUCCESS!");
    }

    /**
     * Consults existent Pix Requests and makes a deposit to the associated accounts.
     * An update is sent informing which deposits did or not work. 
     */
    public void consultPixRequests() {
        logger.info("(consultPixRequest) Start.");

        Response resp;

        try {
            resp = pixService.consultPixRequests();

            if (resp.getStatus() != 200)
                throw new Exception("Pix request.");
            if (!resp.hasEntity())
                throw new Exception("No entity.");
            
        } catch (final Exception e) {
            logger.error("(consultPixRequest) " + e);
            return;
        }

        logger.info("(consultPixRequest) Consult pix request: OK.");

        final Pix[] pixes;
        try {
            pixes = resp.readEntity(Pix[].class);
        } catch (final Exception e) {
            logger.error("(consultPixRequest) " + e);
            return;
        }

        logger.info("(consultPixRequest) Convert JSON to BankPix: OK.");

        final PixRequestUpdateDTO[] resolvedPixes = new PixRequestUpdateDTO[pixes.length];
        
        for (int i = 0; i < resolvedPixes.length; i++) {
            final Pix pix = pixes[i];
            if (pix == null) {
                logger.error("(createPixRequest) Null pix.");
                continue;
            }
            
            final PixRequestUpdateDTO pixUpdate = new PixRequestUpdateDTO(pix.endToEndId);
            resolvedPixes[i] = pixUpdate;

            final Account account = accountControl.getAccount(pix.cpf);

            if (account == null) {
                continue;
            }

            try {
                account.deposit(pix.value);
                db.updateAccountBalance(account);
                pixUpdate.resolved = Pix.ResolvedStates.SUCCESS;
            } catch (final Exception e) {
                pixUpdate.resolved = Pix.ResolvedStates.FAIL;
            }
        }

        try {
            resp = pixService.closePixRequests(resolvedPixes);

            if (resp.getStatus() != 200)
                throw new Exception("Pix request status: " + resp.getStatus());

        } catch (final Exception e) {
            logger.error("(consultPixRequest) " + e);
            return;
        }
        
        logger.info("(consultPixRequest) Close pix request: OK.");
        logger.info("(consultPixRequest) Pix request: OK.");
        logger.info("(consultPixRequest) SUCCESS!");
    }

    /**
     * Consults updated Pix Requests and rollbacks the operation on those which failed.
     */
    public void consultUpdatedPixes() {
        logger.info("(consultUpdatedPixes) Start.");

        final Response resp;

        try {
            resp = pixService.consultUpdatedPixes();

            if (resp.getStatus() != 200)
                throw new Exception("Pix requests states.");
            if (!resp.hasEntity())
                throw new Exception("No entity.");
            
        } catch (final Exception e) {
            logger.error("(consultUpdatedPixes) " + e);
            return;
        }

        logger.info("(consultUpdatedPixes) Consult updated pixes: OK.");

        final Pix[] pixes;
        try {
            pixes = resp.readEntity(Pix[].class);
        } catch (final Exception e) {
            logger.error("(consultUpdatedPixes) " + e);
            return;
        }

        logger.info("(consultUpdatedPixes) Convert JSON to BankPix: OK.");

        for (final Pix pix : pixes) {

            switch (pix.resolved) {
                case Pix.ResolvedStates.FAIL:
                    final PixRollbacker rollbacker = rollbackers.get(pix.endToEndId);
                    try {
                        rollbacker.account.deposit(rollbacker.value);
                        db.updateAccountBalance(rollbacker.account);
                    } catch (final Exception e) {
                        logger.error("(consultUpdatedPixes)" + e);
                    }
                    rollbackers.remove(pix.endToEndId);
                    break;
                
                case Pix.ResolvedStates.SUCCESS:
                    rollbackers.remove(pix.endToEndId);
                    break;
                
                case Pix.ResolvedStates.REQUEST:
                    logger.info("(consultUpdatedPixes) Pix with Request state found in consult.");
                    break;
            }
        }

        logger.info("(consultUpdatedPixes) End.");
    }
}
