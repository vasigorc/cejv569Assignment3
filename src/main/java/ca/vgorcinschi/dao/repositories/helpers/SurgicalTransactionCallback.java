package ca.vgorcinschi.dao.repositories.helpers;

import ca.vgorcinschi.model.Surgical;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;

/**
 * Class SurgicalTransactionCallback is the implementation of
 * AbstractTransactionCallback for Surgical
 *
 * @author vgorcinschi
 */
public class SurgicalTransactionCallback extends AbstractTransactionCallback {
    
    private final Logger log
            = LoggerFactory.getLogger(this.getClass().getName());
    
    //list is created at construction time
    private final List<Surgical> surgicals;

    private final SurgicalBatchPreparedStatementSetter preparedStatementSetter;

    public SurgicalTransactionCallback(SurgicalBatchPreparedStatementSetter preparedStatementSetter,
            String sqlStatement, JdbcTemplate jdbcTemplate) {
        super(sqlStatement, jdbcTemplate);
        this.preparedStatementSetter = preparedStatementSetter;
        surgicals = preparedStatementSetter.getSurgicals();
    }

    @Override
    public Boolean doInTransaction(TransactionStatus ts) {
        /**
         * @param sqlStatement is invoked on every item in the surgicals passed to
         * @param preparedStatementSetter
         * @return an array of integers (where integers is number of rows
         * affected) by each query
         */
        int[] arrayOfaffectedRows = jdbcTemplate.batchUpdate(sqlStatement, preparedStatementSetter);
        /*
         "Did every query update only one row in the DB?" if N -> rollback.
         */
        if (Arrays.stream(arrayOfaffectedRows).allMatch(i -> i == 1)) {
            log.info(surgicals.size() + " surgical records for patient "
                    + "with id " + surgicals.get(0).getPatientId() + " have been successfully "
                    + "updated.");
            return true;
        }
        ts.setRollbackOnly();
        log.error("Rolled back batch update of " + surgicals.size() + " records for patient "
                + "with id " + surgicals.get(0).getPatientId() + " for the attempt "
                + "to modify <>1 record(s) with a single update statement");
        return false;
    }
}