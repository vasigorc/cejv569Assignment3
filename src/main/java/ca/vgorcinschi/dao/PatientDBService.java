package ca.vgorcinschi.dao;

import ca.vgorcinschi.model.Patient;
import java.util.List;
import java.util.Optional;

/**
 * DAO interface that will be used by the client
 *
 * @author vgorcinschi
 */
public interface PatientDBService {

    public boolean savePatient(Patient p);

    public boolean addDetailRecord(Object detailRecord);

    public boolean updateDetailRecord(Object detailRecord);

    public boolean deleteDetailRecord(Object detailRecord);

    public Optional<Patient> findById(int id);

    public Optional<List<Patient>> findByName();

    public Optional<List<Patient>> allPatients();

    public boolean deletePatient();
}