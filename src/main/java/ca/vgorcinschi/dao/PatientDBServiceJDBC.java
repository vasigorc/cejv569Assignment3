package ca.vgorcinschi.dao;

import ca.vgorcinschi.dao.repositories.InpatientRepository;
import ca.vgorcinschi.dao.repositories.MedicationRepository;
import ca.vgorcinschi.dao.repositories.PatientRepository;
import ca.vgorcinschi.dao.repositories.SurgicalRepository;
import ca.vgorcinschi.model.Inpatient;
import ca.vgorcinschi.model.Medication;
import ca.vgorcinschi.model.Patient;
import ca.vgorcinschi.model.Surgical;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vgorcinschi
 */
@Repository
@Qualifier("jdbc")
public class PatientDBServiceJDBC implements PatientDBService {

    private final Logger log
            = LoggerFactory.getLogger(this.getClass().getName());

    private final InpatientRepository inpatientRepository;
    private final MedicationRepository medicationRepository;
    private final SurgicalRepository surgicalRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public PatientDBServiceJDBC(InpatientRepository inpatientRepository, MedicationRepository medicationRepository, SurgicalRepository surgicalRepository, PatientRepository patientRepository) {
        this.inpatientRepository = inpatientRepository;
        this.medicationRepository = medicationRepository;
        this.surgicalRepository = surgicalRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public boolean savePatient(Patient p) {
        if (p.getPatientId() > 0) {//meaning it's an update
            return patientRepository.update(p);
        } else {//i.e. we're creating a new record
            return patientRepository.add(p);
        }
    }

    @Override
    public boolean addDetailRecord(Object detailRecord) {
        String simpleName = detailRecord.getClass().getSimpleName();
        switch (simpleName) {
            case "Inpatient":
                return inpatientRepository.add((Inpatient) detailRecord);
            case "Surgical":
                return surgicalRepository.add((Surgical) detailRecord);
            case "Medication":
                return medicationRepository.add((Medication) detailRecord);
            default:
                log.error(detailRecord+" of type "+simpleName+" couldn't be saved "
                        + "into the DB. No matching type.");
                return false;
        }
    }

    @Override
    public boolean updateDetailRecord(Object detailRecord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteDetailRecord(Object detailRecord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Patient> findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<List<Patient>> findByName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<List<Patient>> allPatients() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletePatient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
