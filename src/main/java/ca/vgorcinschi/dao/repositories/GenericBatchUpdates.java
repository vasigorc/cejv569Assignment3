/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import java.io.Serializable;
import java.util.List;

/**
 * we have to introduce this interface to allow the implementing repositories
 * to update batch of records per patient. This method should be called from 
 * a higher level Service. e.g.: surgicalRepository.updateBatch(patient.getSurgicals())
 * @author vgorcinschi
 */
public interface GenericBatchUpdates<E extends Serializable> {
    public boolean updateBatch(final List<E> entities);
}
