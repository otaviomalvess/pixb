package com.bacen;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SPI {

    @Inject
    private BacenDB db;

    @Inject
    private Logger logger;

    /**
     * Create pix request.
     *
     * @param cpf .
     * @return .
     */
    public static void createPixRequest(BacenPix pix) {
        if (pix == null) {
            return;
        }
        
        pix.resolved = BacenPix.ResolvedStates.REQUEST;
        
        System.out.println("[INFO] (bacen.SPI.createPixRequest) Start.");
        
        BacenDB.createPixRequest(pix);
        
        System.out.println("[INFO] (bacen.SPI.createPixRequest) End.");
    }

    /**
     * Consult request.
     *
     * @param cpf .
     * @return .
     */
    public static List<BacenPix> consultRequest() {
        return BacenDB.consultRequest();
    }

    /**
     * Close requests.
     *
     * @param cpf .
     * @return .
     */
    public static void closeRequests(BacenPixRequestUpdateDTO[] toUpdate) {
        if (toUpdate == null) {
            System.out.println("[ERROR] (bacen.SPI.closeRequests) Given end-to-end list is null.");
            return;
        }

        if (toUpdate.length == 0) {
            return;
        }

        for (final BacenPixRequestUpdateDTO pix : toUpdate) {
            BacenDB.updatePixRequestState(pix);
        }
    }
}
