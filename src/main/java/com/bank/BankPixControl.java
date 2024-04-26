package com.bank;

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


    /**
     * Create pix request.
     *
     * @see BankStartPixDTO 
     */
    public void createPixRequest(final BankStartPixDTO pixDTO) {
        logger.info("(createPixRequest) Start.");

        if (pixDTO.pixKey.isBlank()) {
            logger.info("(createPixRequest) Given pix key is blank or empty.");
            return;
        }

        Response resp;
        try {
            resp = pixService.consultKey(pixDTO.pixKey);
        } catch (final Exception e) {
            logger.error(e);
            logger.error("(createPixRequest) Consult key request.");
            return;
        }

        if (resp.getStatus() != 200) {
            logger.error("(createPixRequest) Consult key: status code " + resp.getStatus());
            return;
        }

        if (!resp.hasEntity()) {
            logger.error("(createPixRequest) Consult key: empty body.");
            return;
        }

        logger.info("(createPixRequest) Consult key: OK.");

        final BankPix pix;
        try {
            pix = resp.readEntity(BankPix.class);
        } catch (final Exception e) {
            logger.error(e);
            logger.error("(createPixRequest) Convert JSON to BankPix.");
            return;
        }

        logger.info("(createPixRequest) Convert JSON to BankPix: OK.");

        final Account account = BankDB.getAccount(pix.cpf);
        
        if (account == null) {
            logger.info("(createPixRequest) No account returned.");
            return;
        }

        Exception ex = account.draw(pixDTO.value);
        
        if (ex != null) {
            logger.error("(createPixRequest + Account.draw) " + ex.getMessage());
            return;
        }

        ex = BankDB.updateAccountBalance(account);

        if (ex != null) {
            logger.error("(createPixRequest) " + ex.getMessage());
            return;
        }
        
        pix.value = pixDTO.value;

        try {
            resp = pixService.pixRequest(pix);
        } catch (final Exception e) {
            logger.error(e);
            logger.error("(createPixRequest) Register pix request.");
            return;
        }

        if (resp.getStatus() != 200) {
            logger.error("(createPixRequest) Pix request.");
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
        } catch (final Exception e) {
            logger.error(e);
            logger.error("(consultPixRequest) Consult pix request.");
            return;
        }

        if (resp.getStatus() != 200) {
            logger.error("(consultPixRequest) Pix request.");
            return;
        }

        if (!resp.hasEntity()) {
            logger.info("(consultPixRequest) No entity.");
            return;
        }

        logger.info("(consultPixRequest) Consult pix request: OK.");

        final BankPix[] pixes;
        try {
            pixes = resp.readEntity(BankPix[].class);
        } catch (final Exception e) {
            logger.error(e);
            logger.error("(consultPixRequest) Convert JSON to BankPix. Entity: " + resp.getEntity());
            return;
        }

        logger.info("(consultPixRequest) Convert JSON to BankPix: OK.");

        final BankPixRequestUpdateDTO[] resolvedPixes = new BankPixRequestUpdateDTO[pixes.length];
        
        for (int i = 0; i < resolvedPixes.length; i++) {
            final BankPix pix = pixes[i];
            final BankPixRequestUpdateDTO pixUpdate = new BankPixRequestUpdateDTO(pix.endToEndId);
            final Account account = db.getAccount(pix.cpf);
            
            Exception ex = a.deposit(pix.value);
            
            if (ex != null) {
                pixUpdate.resolved = BankPix.ResolvedStates.FAIL;
            } else {
                ex = db.updateAccountBalance(account);
                pixUpdate.resolved = (ex != null) ? BankPix.ResolvedStates.FAIL : BankPix.ResolvedStates.SUCCESS;
            }

            resolvedPixes[i] = pixUpdate;
        }

        try {
            resp = pixService.closePixRequests(resolvedPixes);
        } catch (final Exception e) {
            logger.error(e);
            logger.error("(consultPixRequest) Update pix request.");
            return;
        }

        if (resp.getStatus() != 200) {
            logger.error("(consultPixRequest) Pix request status: " + resp.getStatus());
            return;
        }
        
        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) Close pix request: OK.");
        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) Pix request: OK.");
        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) SUCCESS!");
    }
}
