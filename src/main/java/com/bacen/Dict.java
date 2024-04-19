package com.bacen;

import java.util.LinkedHashMap;

public class Dict {

    public static LinkedHashMap<String, Object> consultExistentKeys(final String[] keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }

        final BacenEntry[] entries = BacenDB.getEntries(keys);
        final boolean[] existent = new boolean[keys.length];
        for (int i = 0; i < keys.length; i++) {
            existent[i] = entries[i] != null;
        }
        
        final LinkedHashMap<String, Object> r = new LinkedHashMap<String, Object>();
        r.put("keys", keys);
        r.put("entries", existent);

        return r;
    }

    public static BacenEntry consultEntry(final String key) {
        if (key.isBlank()) {
            return null;
        }

        System.out.println("[INFO] (bacen.Dict) Start consult entry. Key: " + key);
        System.out.println("[INFO] (bacen.Dict) Get entry: " + BacenDB.getEntry(key));
        
        return BacenDB.getEntry(key);
    }
}