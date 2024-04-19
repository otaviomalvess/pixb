package com.bacen;

import java.util.List;

import jakarta.transaction.Transactional;

public class BacenDB {

     /**
     * Get entries.
     *
     * @param cpf .
     * @return .
     */
    public static BacenEntry[] getEntries(final String[] keys) {
        final BacenEntry[] entries = new BacenEntry[keys.length];
        
        for (int i = 0; i < keys.length; i++) {
            entries[i] = getEntry(keys[i]);
        }

        return entries;
    }

     /**
     * Get entry.
     *
     * @param cpf .
     * @return .
     */
    public static BacenEntry getEntry(final String key) {
        return BacenEntry.findById(key);
    }

     /**
     * Create pix request.
     *
     * @param cpf .
     * @return .
     */
    @Transactional
    public static void createPixRequest(final BacenPix p) {
        try {
            p.persist();
            // final Session s = PanacheEntityBase.getEntityManager().unwrap(Session.class);
            // s.merge(p);
        } catch (final Exception e) {
            System.out.println(e);
            System.out.println("[ERROR] (bacen.DB.createPixRequest) Saving pix request.");
        }
    }

     /**
     * Consult request.
     *
     * @param cpf .
     * @return .
     */
    public static List<BacenPix> consultRequest() {
        try {
            return BacenPix.list("resolved", BacenPix.ResolvedStates.REQUEST);
        } catch (final Exception e) {
            System.out.println(e);
            System.out.println("[ERROR] (bacen.DB.consultRequest) Consult pix request.");
            return null;
        }
    }

     /**
     * Update pix request.
     *
     * @param cpf .
     * @return .
     */
    @Transactional
    public static void updatePixRequestState(final BacenPixRequestUpdateDTO pix) {
        try {
            // BUG: this following error happens when using the `update` method:
            //       org.hibernate.query.SemanticException: Could not interpret path expression 'end_to_end_id'
            // Pix.update("resolved = ?1 WHERE end_to_end_id = ?2", true, id);
            
            final BacenPix p = BacenPix.findById(pix.endToEndId);
            p.resolved = pix.resolved;
            BacenPix.persist(p);
        } catch (final Exception e) {
            System.out.println(e);
            System.out.println("[ERROR] (bacen.DB.updatePixRequest) Update pix request. Id: " + pix.endToEndId);
        }
    }
}
