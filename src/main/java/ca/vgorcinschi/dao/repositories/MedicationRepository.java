package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Medication;

public interface MedicationRepository extends GenericRepository<Medication>,
        DetailRecordMethods<Medication>{    
}