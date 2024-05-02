package com.bacen.common;

import java.util.List;

import com.bacen.model.Pix;
import com.bacen.model.PixRequestUpdateDTO;

public interface IPixDB {
    
    /**
     * Returns any resolved pix.
     *
     * @return The list of resolved pix.
     */
    public List<Pix> getResolvedRequests();

    /**
     * Returns any unresolved pix.
     *
     * @return The list of unresolved pix.
     */
    public List<Pix> getUnResolvedRequests();

    /**
     * Returns a list of pix.
     *
     * @param byResolvedState The search parameter defining whether the response state should be resolved or not.
     * @return The list of pix.
     */
    public List<Pix> getRequests(final boolean byResolvedState);

    /**
     * Persists a new pix.
     *
     * @param pix The pix to persist.
     */
    public void insertPixRequest(final Pix pix);
    
    /**
     * Updates a pix response state.
     *
     * @param pixUpdate The pix state DTO container.
     * @see PixRequestUpdateDTO
     */
    public void updatePixRequestState(final PixRequestUpdateDTO pixUpdate);
}
