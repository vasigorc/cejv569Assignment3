package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Inpatient;

/**
 * repository type for inpatient operations.
 * @author vgorcinschi
 */
public interface InpatientRepository extends GenericRepository<Inpatient>,
        DetailRecordMethods<Inpatient>{}