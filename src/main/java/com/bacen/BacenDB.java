package com.bacen;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BacenDB {

    @Inject
    private Logger logger;

    /**
     * Gets an array entries matching the given keys.
     *
     * @param keys an array of entry keys. 
     * @return an array of {@code BacenEntry}.
     */
    public ArrayList<BacenEntry> getEntries(final String[] keys) {
        final ArrayList<BacenEntry> entries = new ArrayList<>();
        
        for (final String key : keys) {
            final BacenEntry entry = getEntry(key);
            if (entry != null) {
                entries.add(entry);
            }
        }

        return entries;
    }

    /**
     * Gets an entry matching the given key.
     *
     * @param key the entry key.
     * @return a {@code BacenEntry}.
     */
    public BacenEntry getEntry(final String key) {
        return BacenEntry.findById(key);
    }

    /**
     * Creates a new Pix Request.
     *
     * @param pix .
     */
    @Transactional
    public void insertPixRequest(final BacenPix pix) {
        try {
            pix.persist();
        } catch (final Exception e) {
            logger.error("(insertPixRequest) " + e);
        }
    }

    /**
     * Gets the pix requests of the type {@code REQUEST} from the DB.
     *
     * @param byResolvedState .
     * @return a {@code List} of {@code BacenPix}.
     */
    public List<BacenPix> getRequests(final boolean byResolvedState) {
        final String query = byResolvedState ? "resolved" : "NOT resolved";
        try {
            return BacenPix.list(query, BacenPix.ResolvedStates.REQUEST);
        } catch (final Exception e) {
            logger.error("(getRequests) " + e);
            return null;
        }
    }

    /**
     * Updates a Pix Request response state.
     *
     * @param pix .
     */
    @Transactional
    public void updatePixRequestState(final BacenPixRequestUpdateDTO pixUpdate) {
        try {
            // BUG: the following error happens when using the `update` method:
            //       org.hibernate.query.SemanticException: Could not interpret path expression 'end_to_end_id'
            // Pix.update("resolved = ?1 WHERE end_to_end_id = ?2", true, id);
            
            final BacenPix pix = BacenPix.findById(pixUpdate.endToEndId);
            pix.resolved = pixUpdate.resolved;
            BacenPix.persist(pix);
        } catch (final Exception e) {
            logger.error("(updatePixRequest) " + e);
        }
    }
}
