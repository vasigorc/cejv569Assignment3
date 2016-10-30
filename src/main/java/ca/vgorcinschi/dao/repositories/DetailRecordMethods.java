package ca.vgorcinschi.dao.repositories;

import java.io.Serializable;
import java.util.List;

/**
 * we have to introduce this interface to allow the implementing repositories
 * to update batch of records per patient and get all detail records by patient id.
 * These methods should be called from a higher level Service
 * e.g.: surgicalRepository.updateBatch(patient.getSurgicals())
 * @author vgorcinschi
 */
public interface DetailRecordMethods<E extends Serializable> {
    public boolean updateBatch(final List<E> entities);
    public List<E> getPatientDetails(int patientId);
}
