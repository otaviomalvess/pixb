package com.bank;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class BankPixControl {

    // TODO: still haven't figured out why PixService can't get injected in this class.

    /**
     * Create pix request.
     *
     * @see BankStartPixDTO 
     */
    public static void createPixRequest(final BankStartPixDTO pixDTO, final BankPixService pixService) {
        System.out.println("[INFO] (pixb.PixControl.createPixRequest) Start.");

        if (pixDTO.pixKey.isBlank()) {
            System.out.println("[INFO] (pixb.PixControl.createPixRequest) Given pix key is blank or empty.");
            return;
        }

        Response resp;
        try {
            resp = pixService.consultKey(pixDTO.pixKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] (pixb.PixControl.createPixRequest) Consult key request.");
            return;
        }

        if (resp.getStatus() != 200) {
            System.out.println("[ERROR] (pixb.PixControl.createPixRequest) Consult key: status code " + resp.getStatus());
            return;
        }

        if (!resp.hasEntity()) {
            System.out.println("[ERROR] (pixb.PixControl.createPixRequest) Consult key: empty body.");
            return;
        }

        System.out.println("[INFO] (pixb.PixControl.createPixRequest) Consult key: OK.");

        final BankPix pix;
        try {
            pix = resp.readEntity(BankPix.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] (pixb.PixControl.createPixRequest) Convert JSON to BankPix.");
            return;
        }

        System.out.println("[INFO] (pixb.PixControl.createPixRequest) Convert JSON to BankPix: OK.");

        final Account account = BankDB.getAccount(pix.cpf);
        
        if (account == null) {
            System.out.println("[INFO] (pixb.PixControl.createPixRequest) No account returned.");
            return;
        }

        Exception ex = account.draw(pixDTO.value);
        
        if (ex != null) {
            System.out.println("[ERROR] (pixb.PixControl.createPixRequest + Account.draw) " + ex.getMessage());
            return;
        }

        ex = BankDB.updateAccountBalance(account);

        if (ex != null) {
            System.out.println("[ERROR] (pixb.PixControl.createPixRequest) " + ex.getMessage());
            return;
        }
        
        pix.value = pixDTO.value;

        try {
            resp = pixService.pixRequest(pix);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] (pixb.PixControl.createPixRequest) Register pix request.");
            return;
        }

        if (resp.getStatus() != 200) {
            System.out.println("[ERROR] (pixb.PixControl.createPixRequest) Pix request.");
            return;
        }

        System.out.println("[INFO] (pixb.PixControl.createPixRequest) Pix request: OK.");
        System.out.println("[INFO] (pixb.PixControl.createPixRequest) SUCCESS!");
    }

    /**
     * Consult pix requests.
     */
    public static void consultPixRequest(final BankPixService pixService) {
        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) Start consult pix");

        Response resp;

        try {
            resp = pixService.consultPixRequest();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] (pixb.PixControl.consultPixRequest) Consult pix request.");
            return;
        }

        if (resp.getStatus() != 200) {
            System.out.println("[ERROR] (pixb.PixControl.consultPixRequest) Pix request.");
            return;
        }

        if (!resp.hasEntity()) {
            System.out.println("[INFO] (pixb.PixControl.consultPixRequest) No entity.");
            return;
        }

        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) Consult pix request: OK.");

        final BankPix[] pixes;
        try {
            pixes = resp.readEntity(BankPix[].class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] (pixb.PixControl.consultPixRequest) Convert JSON to BankEntry. Entity: " + resp.getEntity());
            return;
        }

        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) Convert JSON to BankEntry: OK.");

        final BankPixRequestUpdateDTO[] resolvedPixes = new BankPixRequestUpdateDTO[pixes.length];
        
        for (int i = 0; i < resolvedPixes.length; i++) {
            final BankPix pix = pixes[i];
            final BankPixRequestUpdateDTO pixUpdate = new BankPixRequestUpdateDTO(pix.endToEndId);
            final Account a = BankDB.getAccount(pix.cpf);
            
            Exception ex = a.deposit(pix.value);
            
            if (ex != null) {
                pixUpdate.resolved = BankPix.ResolvedStates.FAIL;
            } else {
                ex = BankDB.updateAccountBalance(a);
                if (ex != null) {
                    pixUpdate.resolved = BankPix.ResolvedStates.FAIL;
                } else {
                    pixUpdate.resolved = BankPix.ResolvedStates.SUCCESS;
                }
            }

            resolvedPixes[i] = pixUpdate;
        }

        try {
            resp = pixService.closePixRequests(resolvedPixes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("[ERROR] (pixb.PixControl.consultPixRequest) Update pix request.");
            return;
        }

        if (resp.getStatus() != 200) {
            System.out.println("[ERROR] (pixb.PixControl.consultPixRequest) Pix request status: " + resp.getStatus());
            return;
        }
        
        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) Close pix request: OK.");
        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) Pix request: OK.");
        System.out.println("[INFO] (pixb.PixControl.consultPixRequest) SUCCESS!");
    }
}
