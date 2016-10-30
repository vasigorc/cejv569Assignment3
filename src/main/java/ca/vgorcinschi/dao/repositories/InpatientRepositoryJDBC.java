package ca.vgorcinschi.dao.repositories;

import static ca.vgorcinschi.CommonUtil.localToSql;
import ca.vgorcinschi.model.Inpatient;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Inpatient implementation of GenericRepository and DetailRecordMethods
 *
 * @author vgorcinschi
 */
@Repository
@Qualifier("jdbc")
public class InpatientRepositoryJDBC implements InpatientRepository {

    /**
     * note we will only use this logger to log methods that return boolean. For
     * methods which declare 'throws' we will use logger one level up in the
     * service interface
     */
    private final Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

    //configured in src.main.resources.application.yml
    private final JdbcTemplate jdbcTemplate;

    //injection point
    @Autowired
    public InpatientRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(Inpatient entity) {
        //our prepared statement
        String sql = "INSERT INTO INPATIENT (PATIENTID, DATEOFSTAY, ROOMNUMBER,"
                + "DAILYRATE, SUPPLIES, SERVICES) VALUES (?, ?, ?, ?, ?, ?)";
        //using try-catch to log two scenarions
        try {
            /**
             * this method does auto-conversion provided the sequence of the
             * arguments is the same -
             * @link http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html#update-java.lang.String-java.lang.Object...-
             */
            jdbcTemplate.update(sql, entity.getPatientId(), localToSql.apply(entity.getDateOfStay()),
                    entity.getRoomNumber(), entity.getDailyRate(), entity.getSupplies(),
                    entity.getServices());
            log.info("Patient " + entity + " was successfully saved to the DB.");
            return true;
        } catch (DataAccessException e) {
            //log error and return false
            log.error("SQL query for adding Patient " + entity + " failed. "
                    + e.getMostSpecificCause().toString());
            return false;
        }
    }

    @Override
    public boolean update(Inpatient entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateBatch(List<Inpatient> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Inpatient> getPatientDetails(int patientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
