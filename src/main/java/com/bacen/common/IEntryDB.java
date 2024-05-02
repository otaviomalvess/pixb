package com.bacen.common;

import java.util.ArrayList;

import com.bacen.model.Entry;

public interface IEntryDB {
    
    /**
     * Deletes an entry.
     * 
     * @param key The entry key.
     */
    public void deleteEntry(String key); 

    /**
     * Persists a new entry.
     * 
     * @param entry The entry to persist.
     */
    public void insertEntry(Entry entry);

    /**
     * Returns an list of entries.
     *
     * @param keys The entries keys.
     * @return The list of entries.
     */
    public ArrayList<Entry> getEntries(String[] keys);

    /**
     * Returns an entry.
     *
     * @param key The entry key.
     * @return The entry.
     */
    public Entry getEntry(String key);

    /**
     * Changes the entry key.
     * 
     * @param currentKey The current entry key.
     * @param newKey The new entry key.
     */
    public void updateEntryKey(String currentKey, String newKey);
}
