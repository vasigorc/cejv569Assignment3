/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.dao.repositories;

import ca.vgorcinschi.model.Inpatient;

/**
 *
 * @author vgorcinschi
 */
public interface InpatientRepository extends GenericRepository<Inpatient>,
        DetailRecordMethods<Inpatient>{
}
