package ca.vgorcinschi.dao.repositories.helpers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

/**
 * AbstractTransactionCallback abstract class. It implements Spring's
 * TransactionCallback interface with type Boolean. This class is a template for all
 * detail records' repositories implementations. It wraps query execution
 * and observes that the later produced the expected result (e.g. updated only 
 * one record). If this check is successful it returns true (TransactionCallback's
 * doInTransaction method), else it rollbacks the query execution and returns false;
 * @author vgorcinschi
 */
public abstract class AbstractTransactionCallback implements TransactionCallback<Boolean>{
    
    //different for every implementation
    protected final String sqlStatement;
    //we will pass the JdbcTemplate singleton bean at the construction of the instance
    protected final JdbcTemplate jdbcTemplate;

    public AbstractTransactionCallback(String sqlStatement, JdbcTemplate jdbcTemplate) {
        this.sqlStatement = sqlStatement;
        this.jdbcTemplate = jdbcTemplate;
    }

    //to be implemented by subclasses
    @Override
    public abstract Boolean doInTransaction(TransactionStatus ts);    
}