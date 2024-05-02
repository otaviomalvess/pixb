package com.bacen.db;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.bacen.common.IEntryDB;
import com.bacen.common.IPixDB;
import com.bacen.model.Entry;
import com.bacen.model.Pix;
import com.bacen.model.PixRequestUpdateDTO;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

/**
 * BacenDB is a class handling any database operations related to bacen.
 */
@Singleton
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

    public Entry getEntry(final String key) {
        return Entry.find("pkey", key).singleResult();
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

    public List<Pix> getResolvedRequests() {
       return getRequests(true); 
    }

    public List<Pix> getUnResolvedRequests() {
        return getRequests(false); 
    }

    public List<Pix> getRequests(final boolean byResolvedState) {
        final String query = byResolvedState ? "resolved" : "NOT resolved";
        try {
            return Pix.list(query, Pix.ResolvedStates.REQUEST);
        } catch (final Exception e) {
            logger.error("(getRequests) " + e);
            return null;
        }
    }

    @Transactional
    public void updatePixRequestState(final PixRequestUpdateDTO pixUpdate) {
        final int updates = Pix.update("resolved = ?1 WHERE end_to_end_id = ?2", pixUpdate.resolved, pixUpdate.endToEndId);
        if (updates == 0) {
            logger.warn("(updateAccountBalance) 0 entities updated.");
        } else if (updates > 1) {
            logger.error("(updateAccountBalance) %d entities updated.".formatted(updates));
        }
    }
}
