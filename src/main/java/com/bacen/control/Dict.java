package com.bacen.control;

import java.util.ArrayList;

import com.bacen.db.BacenDB;
import com.bacen.model.Entry;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Dict is a class managing pix entries.
 */
@ApplicationScoped
public class Dict {

    @Inject
    private BacenDB db;

    /**
     * Creates a new entry.
     * 
     * @param entry The entry to create.
     * @see Entry
     */
    public void createEntry(final Entry entry) {
        db.insertEntry(entry);
    }

    /**
     * Returns a list of pix entries.
     *
     * @param keys A list of entry keys.
     * @return The list of entries.
     * @see Entry 
     */
    public ArrayList<Entry> getEntries(final String[] keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        
        return db.getEntries(keys);
    }
}