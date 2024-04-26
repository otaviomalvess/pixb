package com.bacen;

import java.util.LinkedHashMap;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Dict {

    @Inject
    private BacenDB db;

    @Inject
    private Logger logger;
    
    /**
     * Checks if there are any {@code Entry} matching the given keys.
     *
     * @param keys array of {@code Entry} keys.
     * @return a {@code LinkedHashMap} containing the array of keys and an array of booleans. 
     */
    public LinkedHashMap<String, Object> consultExistentKeys(final String[] keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }

        final BacenEntry[] entries = db.getEntries(keys);
        final boolean[] existent = new boolean[keys.length];
        for (int i = 0; i < keys.length; i++) {
            existent[i] = entries[i] != null;
        }
        
        final LinkedHashMap<String, Object> r = new LinkedHashMap<String, Object>();
        r.put("keys", keys);
        r.put("entries", existent);

        return r;
    }

    /**
     * Checks if there are any {@code Entry} matching the given keys.
     *
     * @param keys array of {@code Entry} keys.
     * @return the returned {@code Entry}. 
     */
    public BacenEntry consultEntry(final String key) {
        if (key.isBlank()) {
            return null;
        }

        logger.info("(consultEntry) Start. Key: " + key);
        logger.info("(consultEntry) Get entry: " + db.getEntry(key));
        
        return db.getEntry(key);
    }
}