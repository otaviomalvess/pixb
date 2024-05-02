package com.bacen.db;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.bacen.common.IEntryDB;
import com.bacen.common.IPixDB;
import com.bacen.model.Entry;
import com.bacen.model.Pix;
import com.bacen.model.PixRequestUpdateDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * BacenDB is a class handling any database operations related to bacen.
 */
@ApplicationScoped
public class BacenDB implements IEntryDB, IPixDB {

    @Inject
    private Logger logger;

    @Transactional
    public void deleteEntry(final String key) {
        final boolean success = Entry.deleteById(key);
        if (!success) {
            logger.info("(deleteAccount) Entity not found.");
        }
    }

    public ArrayList<Entry> getEntries(final String[] keys) {
        final ArrayList<Entry> entries = new ArrayList<>();
        
        for (final String key : keys) {
            final Entry entry = getEntry(key);
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
    public Entry getEntry(final String key) {
        return Entry.findById(key);
    }

    @Transactional
    public void insertEntry(final Entry entry) {
        try {
            entry.persist();
        } catch (final Exception e) {
            logger.error("(insertEntry) " + e);
        }
    }

    @Transactional
    public void updateEntryKey(final String currentKey, final String newKey) {
        final int updates = Entry.update("key = ?1 WHERE key = ?2", newKey, currentKey);
        if (updates == 0) {
            logger.warn("(updateAccountBalance) 0 entities updated.");
        } else if (updates > 1) {
            logger.error("(updateAccountBalance) %d entities updated.".formatted(updates));
        }
    }

    @Transactional
    public void insertPixRequest(final Pix pix) {
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
    public List<Pix> getRequests(final boolean byResolvedState) {
        final String query = byResolvedState ? "resolved" : "NOT resolved";
        try {
            return Pix.list(query, Pix.ResolvedStates.REQUEST);
        } catch (final Exception e) {
            logger.error("(getRequests) " + e);
            return null;
        }
    }

    /**
     * Updates a Pix Request response state.
     *
     * @param pixUpdate .
     */
    @Transactional
    public void updatePixRequestState(final PixRequestUpdateDTO pixUpdate) {
        try {
            // BUG: the following error happens when using the `update` method:
            //       org.hibernate.query.SemanticException: Could not interpret path expression 'end_to_end_id'
            // Pix.update("resolved = ?1 WHERE end_to_end_id = ?2", true, id);
            
            final Pix pix = Pix.findById(pixUpdate.endToEndId);
            pix.resolved = pixUpdate.resolved;
            Pix.persist(pix);
        } catch (final Exception e) {
            logger.error("(updatePixRequest) " + e);
        }
    }
}
