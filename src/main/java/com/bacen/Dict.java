package com.bacen;

import java.util.ArrayList;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Dict {

    @Inject
    private BacenDB db;

    /**
     * Checks if there are any {@code Entry} matching the given keys.
     *
     * @param keys array of {@code Entry} keys.
     * @return the returned {@code Entry}. 
     */
    public ArrayList<BacenEntry> getEntries(final String[] keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        
        return db.getEntries(keys);
    }
}