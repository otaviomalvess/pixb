package com.bacen.control;

import java.util.List;
import java.util.ArrayList;

import org.jboss.logging.Logger;

import com.bacen.db.BacenDB;
import com.bacen.model.Pix;
import com.bacen.model.PixRequestUpdateDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * SPI is a class managing pix requests and its states.
 */
@ApplicationScoped
public class SPI {

    @Inject
    private BacenDB db;

    @Inject
    private Logger logger;

    private ArrayList<PixRequestUpdateDTO> updatedPixes = new ArrayList<PixRequestUpdateDTO>();

    /**
     * Creates new pix requests.
     *
     * @param pixes A list of pix.
     * @see Pix
     */
    public void createRequests(final Pix[] pixes) {
        if (pixes == null || pixes.length == 0) {
            logger.info("(createPixRequest) Given Pixes array is null or empty.");
            return;
        }
        
        logger.info("(createPixRequest) Start.");

        for (final Pix pix : pixes) {
            pix.resolved = Pix.ResolvedStates.REQUEST;
            db.insertPixRequest(pix);
        }

        logger.info("(createPixRequest) End.");
    }

    /**
     * Returns a list of pix requests.
     *
     * @param resolved A boolean to specify the search method.
     * @return The list of pix requests.
     * @see Pix
     */
    public List<Pix> getRequests(final boolean resolved) {
        return resolved ? db.getResolvedRequests() : db.getUnResolvedRequests();
    }

    /**
     * Returns a list of updated pix requests.
     *
     * @return The list of updated pix requests.
     * @see PixRequestUpdateDTO
     */
    public ArrayList<PixRequestUpdateDTO> consultUpdatedPixes() {
        final ArrayList<PixRequestUpdateDTO> temp = updatedPixes;
        updatedPixes.clear();
        return temp;
    }

    /**
     * Updates the resolved state of the requested pix requests.
     *
     * @param toUpdate A list of pix requests to update.
     * @see PixRequestUpdateDTO
     */
    public void updateRequests(final PixRequestUpdateDTO[] toUpdate) {
        if (toUpdate == null) {
            logger.error("(updatePixRequests) Given end-to-end list is null.");
            return;
        }

        logger.info("(updatePixRequests) Start.");

        if (toUpdate.length == 0) {
            return;
        }

        for (final PixRequestUpdateDTO pix : toUpdate) {
            if (pix.resolved == Pix.ResolvedStates.REQUEST) {
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
