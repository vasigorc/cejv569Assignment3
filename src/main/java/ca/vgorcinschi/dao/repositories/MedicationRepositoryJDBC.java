package ca.vgorcinschi.dao.repositories;

import static ca.vgorcinschi.CommonUtil.localToSql;
import ca.vgorcinschi.dao.repositories.helpers.MedicationPreparedStatementSetter;
import ca.vgorcinschi.model.Medication;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Medication implementation of GenericRepository and DetailRecordMethods NB:
 * Please note that I didn't write an abstract class AbstractRepository <E>
 * a) because all methods would have to be overwritten. The underlying generic E
 * is too different so property types and number that we will extract in each
 * method will vary too much b) in terms of spring beans -that would leave us to
 * either create another abstract class per Detail Record or make beans from
 * final classes which is not optimal for testing different profiles
 *
 * @author v_gorcin
 */
@Repository
@Qualifier("jdbc")
public class MedicationRepositoryJDBC implements MedicationRepository {

    /**
     * we will only use this logger to log methods that return boolean. For
     * methods which declare 'throws' we will use logger one level up in the
     * service interface
     */
    private final Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

    //configured in src.main.resources.application.yml
    private final JdbcTemplate jdbcTemplate;

    //Spring's transaction template is used to rollback the transaction
    private final TransactionTemplate transactionTemplate;

    //prepared statement
    private final String updatePreparedStatement = "UPDATE MEDICATION SET PATIENTID = ?,"
            + " DATEOFMED=?, MED=?, UNITCOST=?, UNITS=? WHERE ID=?";

    @Autowired
    public MedicationRepositoryJDBC(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public boolean add(Medication entity) {
        //our prepared statement
        String sql = "INSERT INTO MEDICATION (PATIENTID, DATEOFMED, MED,"
                + "UNITCOST, UNITS) VALUES (?, ?, ?, ?, ?)";
        try {
            /**
             * this method does auto-conversion provided the sequence of the
             * arguments is the same -
             * @link http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html#update-java.lang.String-java.lang.Object...-
             */
            jdbcTemplate.update(sql, entity.getPatientId(), localToSql.apply(entity.getDateOfMedication()),
                    entity.getMed(), entity.getUnitCost(), entity.getUnits());
            log.info("Medication " + entity + " was successfully saved to the DB.");
            return true;
        } catch (DataAccessException e) {
            //log error and return false
            log.error("SQL query for adding Medication " + entity + " failed. "
                    + e.getMostSpecificCause().toString());
            return false;
        }
    }

    @Override
    public boolean update(Medication entity) {
        if (entity.getId() > 0) {
            /*
             creating a new controlled transaction. obtain a callback function
             (new TransactionCallback<Boolean>(TransactionStatus)) from the 
             transaction template. This function takes only one argument - Transaction
             status which can rollback a transaction.
             */
            return transactionTemplate.execute((TransactionStatus transactionStatus) -> {
                //update the db if the # of affected rows is 1
                if (jdbcTemplate.update(updatePreparedStatement,
                        new MedicationPreparedStatementSetter(entity)) == 1) {
                    log.info("Medication successfully updated in the database: " + entity);
                    return true;
                }
                //else roll back transaction and log error
                transactionStatus.setRollbackOnly();
                log.error("Couldn't update " + entity + ". Query tried to modify "
                        + "more/less then one row. Check db design.");
                return false;
            });
        } else {
            //log error and return false
            log.error(entity + " doesn't have a valid id. Persistance skipped.");
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateBatch(List<Medication> entities) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Medication> getPatientDetails(int patientId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}