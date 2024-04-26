package com.bacen;

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
    public BacenEntry[] getEntries(final String[] keys) {
        final BacenEntry[] entries = new BacenEntry[keys.length];
        
        for (int i = 0; i < keys.length; i++) {
            entries[i] = getEntry(keys[i]);
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
    public void createPixRequest(final BacenPix pix) {
        try {
            pix.persist();
            // final Session s = PanacheEntityBase.getEntityManager().unwrap(Session.class);
            // s.merge(p);
        } catch (final Exception e) {
            logger.error(e);
            logger.error("(createPixRequest) Saving pix request.");
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
            logger.error(e);
            logger.error("(getRequests) Consult pix request.");
            return null;
        }
    }

    /**
     * Updates a Pix Request response state.
     *
     * @param pix .
     */
    @Transactional
    public void updatePixRequestState(final BacenPixRequestUpdateDTO pix) {
        try {
            // BUG: this following error happens when using the `update` method:
            //       org.hibernate.query.SemanticException: Could not interpret path expression 'end_to_end_id'
            // Pix.update("resolved = ?1 WHERE end_to_end_id = ?2", true, id);
            
            final BacenPix p = BacenPix.findById(pix.endToEndId);
            p.resolved = pix.resolved;
            BacenPix.persist(p);
        } catch (final Exception e) {
            logger.error(e);
            logger.error("(updatePixRequest) Update pix request. Id: " + pix.endToEndId);
        }
    }
}
