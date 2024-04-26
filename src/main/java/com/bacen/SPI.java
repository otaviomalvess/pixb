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
        
        logger.info("(createPixRequest) Start.");
        
        BacenDB.createPixRequest(pix);
        
        logger.info("(createPixRequest) End.");
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
            logger.error("(updatePixRequests) Given end-to-end list is null.");
            return;
        }

        logger.info("(updatePixRequests) Start.");

        if (toUpdate.length == 0) {
            return;
        }

        for (final BacenPixRequestUpdateDTO pix : toUpdate) {

            logger.info("(updatePixRequests) Pix updated.");
        }

        logger.info("(updatePixRequests) End.");
    }
}
