/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    JdbcTemplate jdbcTemplate;

    //point of injection
    @Autowired
    public PatientRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //general select for patient(s)
    String generalSelect = "SELECT * FROM PATIENT";

    @Override
    public Patient findById(int patientId) throws DataAccessException, IncorrectResultSizeDataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Patient> findByLastName(String lastName) throws DataAccessException, IncorrectResultSizeDataAccessException {
        //appending the condition to the general query
        String byLastName = "SELECT * FROM PATIENT WHERE LASTNAME = ?";
        //the list that we will return
        List<Patient> patients = new ArrayList<>();
        //a list of all rows
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

    @Override
    public List<Patient> getAll() throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
