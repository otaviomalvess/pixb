package com.bacen;

import java.util.List;
import java.util.ArrayList;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SPI {

    @Inject
    private BacenDB db;

    @Inject
    private Logger logger;

    private ArrayList<BacenPixRequestUpdateDTO> updatedPixes = new ArrayList<BacenPixRequestUpdateDTO>();

    /**
     * Consults the DB to create a pix request.
     *
     * @param pixes .
     */
    public void createRequests(final BacenPix[] pixes) {
        if (pixes == null || pixes.length == 0) {
            logger.info("(createPixRequest) Given Pixes array is null or empty.");
            return;
        }
        
        logger.info("(createPixRequest) Start.");

        for (final BacenPix pix : pixes) {
            pix.resolved = BacenPix.ResolvedStates.REQUEST;
            db.insertPixRequest(pix);
        }

        logger.info("(createPixRequest) End.");
    }

    /**
     * Consult request.
     *
     * @return a {@code List} of {@code BacenPix} from the DB.
     */
    public List<BacenPix> getPixRequests(final boolean byResolvedState) {
        return db.getRequests(byResolvedState);
    }

    /**
     * Get failed pixes.
     *
     * @return a {@code List} of {@code BacenPix}.
     */
    public ArrayList<BacenPixRequestUpdateDTO> consultUpdatedPixes() {
        final ArrayList<BacenPixRequestUpdateDTO> temp = updatedPixes;
        updatedPixes.clear();
        return temp;
    }

    /**
     * Closes the given Pix Requests.
     *
     * @param toUpdate an array of {@code BacenPixRequestUpdateDTO}.
     */
    public void updatePixRequests(final BacenPixRequestUpdateDTO[] toUpdate) {
        if (toUpdate == null) {
            logger.error("(updatePixRequests) Given end-to-end list is null.");
            return;
        }

        logger.info("(updatePixRequests) Start.");

        if (toUpdate.length == 0) {
            return;
        }

        for (final BacenPixRequestUpdateDTO pix : toUpdate) {
            if (pix.resolved == BacenPix.ResolvedStates.REQUEST) {
                // TODO: figure out what to do here.
                logger.error("(updatePixRequests) Pix " + pix.endToEndId + " with Request resolved state. Skip.");
                continue;
            }

            db.updatePixRequestState(pix);
            updatedPixes.add(pix);

            logger.info("(updatePixRequests) Pix updated.");
        }

        logger.info("(updatePixRequests) End.");
    }
}
