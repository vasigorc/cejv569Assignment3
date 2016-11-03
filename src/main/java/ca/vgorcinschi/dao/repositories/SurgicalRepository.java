package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Surgical;

/**
 * repository type for surgical operations.
 * @author vgorcinschi
 */
public interface SurgicalRepository extends GenericRepository<Surgical>,
        DetailRecordMethods<Surgical> {}