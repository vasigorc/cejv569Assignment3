package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Patient;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * the JDBC implementation of PatientRepository
 *
 * @author vgorcinschi
 */
@Qualifier("jdbc")
@Repository
public class PatientRepositoryJDBC implements PatientRepository {

    //general select for patient(s)
    private final String generalSelect = "SELECT * FROM PATIENT";

    //configured in src.main.resources.application.yml
    JdbcTemplate jdbcTemplate;

    //point of injection
    @Autowired
    public PatientRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * retrieving just one object by an unique id
     *
     * @param patientId - is the id of the searched employee
     * @return - a patient object
     * @throws DataAccessException - failed query, incl. no result
     * @throws IncorrectResultSizeDataAccessException - result set contained >1
     * row
     */
    @Override
    public Patient findById(int patientId) throws DataAccessException, IncorrectResultSizeDataAccessException {
        //appending the condition to the general query
        String byId = generalSelect + " WHERE PATIENTID = ?";
        return jdbcTemplate.queryForObject(byId, (rs, rowCount) -> {
            //Patient object to be filled
            Patient candidate = new Patient();
            //populate the candidate 
            candidate.setLastName(rs.getString("LASTNAME"));
            candidate.setFirstName(rs.getString("FIRSTNAME"));
            candidate.setDiagnosis(rs.getString("DIAGNOSIS"));
            candidate.setAdmissionDate(rs.getTimestamp("ADMISSIONDATE").toLocalDateTime());
            candidate.setReleaseDate(rs.getTimestamp("RELEASEDATE").toLocalDateTime());
            return candidate;
        }, patientId);
    }

    /**
     * return 1-n number of Patients matching
     *
     * @param lastName of an employee/employee(s)
     * @return a list of patient objects
     * @throws DataAccessException query failed
     */
    @Override
    public List<Patient> findByLastName(String lastName) throws DataAccessException {
        //appending the condition to the general query
        String byLastName = generalSelect + " WHERE LASTNAME = ?";
        //the list that we will return
        List<Patient> patients = new ArrayList<>();
        //a list of all rows matching the query
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(byLastName, lastName);
        //iterate through the result set rows
        rows.stream().forEach((row) -> {
            //create a patient
            Patient p = new Patient((int) row.get("PATIENTID"), (String) row.get("LASTNAME"),
                    (String) row.get("FIRSTNAME"), (String) row.get("DIAGNOSIS"),
                    ((Timestamp) row.get("ADMISSIONDATE")).toLocalDateTime(),
                    ((Timestamp) row.get("RELEASEDATE")).toLocalDateTime());
            patients.add(p);
        });
        return patients;
    }

    /**
     * similar to the previous query, but there is no filter. Return all
     * patients.
     *
     * @return
     * @throws DataAccessException
     */
    @Override
    public List<Patient> getAll() throws DataAccessException {
        //the list that we will return
        List<Patient> patients = new ArrayList<>();
        //a list of all rows
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(generalSelect);
        //iterate through the result set rows
        rows.stream().forEach((row) -> {
            //create a patient
            Patient p = new Patient((int) row.get("PATIENTID"), (String) row.get("LASTNAME"),
                    (String) row.get("FIRSTNAME"), (String) row.get("DIAGNOSIS"),
                    ((Timestamp) row.get("ADMISSIONDATE")).toLocalDateTime(),
                    ((Timestamp) row.get("RELEASEDATE")).toLocalDateTime());
            patients.add(p);
        });
        return patients;
    }

    @Override
    public void add(Patient entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Patient entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Patient entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
