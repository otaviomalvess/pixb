package com.bacen;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bacen.db.BacenDB;
import com.bacen.model.Entry;
import com.bacen.model.Pix;
import com.bacen.model.PixRequestUpdateDTO;
import com.bacen.model.Pix.ResolvedStates;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class BacenDBTest {

    @Inject
    private BacenDB db;

    private Entry entry;

    @Test
    public void testGetEntry() {
        entry = db.getEntry("22222222201");
        assertNotNull(entry);
    }

    @Test
    public void testGetEntries() {
        final String[] keys = new String[] {
            "11111111101",
            "22222222201", // Valid
            "33333333301",
            "22222222201" // Valid
        };
        
        final ArrayList<Entry> objects = db.getEntries(keys);
        
        assertNotNull(objects);
        
        final Entry[] entries = objects.toArray(new Entry[0]);  

        assertNotNull(entries);
        for (final Entry o : entries) {
            assertNotNull(o);
        }
        assertArrayEquals(entries, new Entry[]{entry, entry});
    }

    @Test
    public void testInsertPixRequest() {
        final Pix pix = new Pix(0, "Bob", 50.0, ResolvedStates.REQUEST, 0, 0, 0, "22222222201");

        db.insertPixRequest(pix);
    }

    @Test
    public void getUnresolvedRequests() {
        final List<Pix> objects = db.getUnResolvedRequests();
        
        assertNotNull(objects);

        final Pix[] requests = objects.toArray(new Pix[0]);

        assertNotNull(requests);
        assertEquals(requests.length, 1);
        assertNotNull(requests[0]);
        assertEquals(requests[0].resolved, ResolvedStates.REQUEST);
    }

    @Test
    public void testUpdatePixRequestState() {
        Pix[] requests = db.getUnResolvedRequests().toArray(new Pix[0]);
        
        final PixRequestUpdateDTO pixUpdate = new PixRequestUpdateDTO(ResolvedStates.SUCCESS, requests[0].getEndToEndId());

        db.updatePixRequestState(pixUpdate);

        requests = db.getUnResolvedRequests().toArray(new Pix[0]);
        assertNull(requests);

        requests = db.getResolvedRequests().toArray(new Pix[0]);
        assertNotNull(requests);
        assertEquals(requests.length, 1);
        assertNotNull(requests[0]);
        assertEquals(requests[0].resolved, ResolvedStates.SUCCESS);
    }

    @Test
    public void testGetResolvedRequests() {
        final List<Pix> objects = db.getResolvedRequests();
        
        assertNotNull(objects);

        final Pix[] requests = objects.toArray(new Pix[0]);

        assertNotNull(requests);
        assertEquals(requests.length, 1);
        assertNotNull(requests[0]);
        assertNotEquals(requests[0].resolved, ResolvedStates.REQUEST);
    }
}
