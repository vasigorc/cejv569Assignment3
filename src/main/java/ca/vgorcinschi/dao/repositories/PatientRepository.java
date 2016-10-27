package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Patient;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * this extends the generic repository, but adds additional methods
 *
 * @author vgorcinschi
 */
public interface PatientRepository extends GenericRepository<Patient> {

    /**
     * when a record is found then all of its detail records must also be
     * retrieved
     *
     * @param patientId - the id of the patient
     * @return a patient object or
     */
    public Patient findById(int patientId) throws DataAccessException,
            IncorrectResultSizeDataAccessException;

    /**
     * when a record(s) is found then all of its detail records must also be
     * retrieved
     *
     * @param lastName of the patient
     * @return a list of patient
     */
    public List<Patient> findByLastName(String lastName) throws DataAccessException;
    
    /**
     * 
     * @return list of all patients
     */
    public List<Patient> getAll() throws DataAccessException;
}