package ca.vgorcinschi.dao;

import ca.vgorcinschi.model.Patient;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * DAO interface that will be used by the client
 *
 * @author vgorcinschi
 */
public interface PatientDBService {

    public boolean savePatient(Patient p);

    public boolean saveDetailRecord(Object detailRecord);

    public boolean deleteDetailRecord(Object detailRecord);

    public Optional<Patient> findById(int id);

    public Optional<List<Patient>> findByName(String lastName);

    public Optional<List<Patient>> allPatients();

    public boolean deletePatient();

    public boolean actOnDetailByReflection(String simpleName, Object detailRecord,
            String operation)
            throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, NoSuchMethodException,
            ClassNotFoundException, InvocationTargetException;
}
