package ca.vgorcinschi.dao;

import ca.vgorcinschi.dao.repositories.InpatientRepository;
import ca.vgorcinschi.dao.repositories.MedicationRepository;
import ca.vgorcinschi.dao.repositories.PatientRepository;
import ca.vgorcinschi.dao.repositories.SurgicalRepository;
import ca.vgorcinschi.model.Identifiable;
import ca.vgorcinschi.model.Patient;
import static java.lang.Integer.valueOf;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.containsAny;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author vgorcinschi
 */
@Service
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
    public boolean saveDetailRecord(Object detailRecord) {
        //get the detail record's simple class name
        String simpleName = detailRecord.getClass().getSimpleName();
        //if it is not one of the detail records' types log error, return false
        if (!containsAny(simpleName, "Medication", "Inpatient", "Surgical")) {
            log.error("Skipped saving object " + detailRecord + ", class " + simpleName
                    + " is not supported");
            return false;
        }
        //find out the correct operation
        String action = ((Identifiable) detailRecord).getId() > 0 ? "update" : "add";
        try {
            return actOnDetailByReflection(simpleName, detailRecord, action);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException e) {
            log.error("Couldn't save " + detailRecord + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteDetailRecord(Object detailRecord) {
        //get the detail record's simple class name
        String simpleName = detailRecord.getClass().getSimpleName();
        //if it is not one of the detail records' types log error, return false
        if (!containsAny(simpleName, "Medication", "Inpatient", "Surgical")) {
            log.error("Skipped deleting object " + detailRecord + ", class " + simpleName
                    + " is not supported");
            return false;
        }
        Integer idOfTheRecord = valueOf(((Identifiable) detailRecord).getId());
        //invoke the method of the correct repository
        try {
            return actOnDetailByReflection(simpleName, idOfTheRecord, "delete");
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException e) {
            log.error("Couldn't delete " + detailRecord + ": " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Patient> findById(int id) {
        Optional<Patient> patient = empty();
        try {
            patient = ofNullable(patientRepository.findById(id));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Patient with the id " + id + " couldn't be found in the DB. "
                    + "Returning empty optional object");
        }
        if (patient.isPresent()) {
            patient.get().getInpatients().addAll(inpatientRepository.getPatientDetails(id));
            patient.get().getMedications().addAll(medicationRepository.getPatientDetails(id));
            patient.get().getSurgicals().addAll(surgicalRepository.getPatientDetails(id));
        }
        return patient;
    }

    @Override
    public Optional<List<Patient>> findByName(String lastName) {
        Optional<List<Patient>> optional = ofNullable(patientRepository.findByLastName(lastName));
        //if the returned list is not empty...
        if (optional.isPresent() && !optional.get().isEmpty()) {
            List<Patient> patients = optional.get();
            patients.stream().map((p) -> {
                p.getInpatients().addAll(inpatientRepository.getPatientDetails(p.getPatientId()));
                return p;
            }).map((p) -> {
                p.getSurgicals().addAll(surgicalRepository.getPatientDetails(p.getPatientId()));
                return p;
            }).forEach((p) -> {
                p.getMedications().addAll(medicationRepository.getPatientDetails(p.getPatientId()));
            });
        } else {
            log.warn("Query for patient(s) \"" + lastName + "\" didn't return any records.");
        }
        return optional;
    }

    @Override
    public Optional<List<Patient>> allPatients() {
        if (patientRepository.getAll() == null && patientRepository.getAll().isEmpty()) {
            log.warn("Query for all patients returned 0 records.");
        }
        return ofNullable(patientRepository.getAll());
    }

    @Override
    public boolean deletePatient(int id) {
        return patientRepository.delete(id);
    }

    /**
     * this method should detect the right repository bean and invoke the
     * correct method on it
     *
     * @param simpleName - type of the detail record (String)
     * @param detailRecord - the detail record
     * @param operation - "add" or "update"
     * @return did we succeed in saving the record
     * @throws NoSuchFieldException - bean field not discovered
     * @throws IllegalArgumentException - incorrect field arguments passed-in
     * @throws IllegalAccessException - field not discovered
     * @throws NoSuchMethodException - field method doesn't exist
     * @throws ClassNotFoundException - model class couldn't be discovered
     * @throws InvocationTargetException - underlying method throws an exception
     */
    @Override
    public boolean actOnDetailByReflection(String simpleName, Object detailRecord,
            String operation)
            throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, NoSuchMethodException,
            ClassNotFoundException, InvocationTargetException {
        //lowercase the class name to find the bean reference by name
        String lowerCased = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);
        // get the field definition & make it accessible
        Field field = this.getClass().getDeclaredField(lowerCased + "Repository");
        field.setAccessible(true);
        // obtain the field value from the object instance
        Object fieldValue = field.get(this);
        /**
         * Depending on argument type: 1. obtain the method reference from the
         * field, method name, arg type and the parent class 2. invoke the
         * method, passing-in the right arg
         */
        Method method;
        if (operation.equals("delete")) {
            method = fieldValue.getClass().getDeclaredMethod(operation,
                    int.class);
        } else {
            method = fieldValue.getClass().getDeclaredMethod(operation,
                    Class.forName("ca.vgorcinschi.model." + simpleName));
        }
        return (Boolean) method.invoke(fieldValue, detailRecord);
    }
}
