package ca.vgorcinschi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *this is the master record, db table PATIENT
 * 
 * @author vgorcinschi
 */
public class Patient implements Serializable{
    
    //default patient
    public static Patient DEFAULT_PATIENT = new Patient(0, "", "", "", 
            LocalDateTime.now(ZoneId.systemDefault()), LocalDateTime.now(ZoneId.systemDefault()));
    
    //primary key
    private IntegerProperty patientId;
    //columns: "LASTNAME", "FIRSTNAME", "DIAGNOSIS"
    private SimpleStringProperty lastName, firstName, diagnosis;
    //columns: "ADMISSIONDATE", "RELEASEDATE"
    private ObjectProperty<LocalDateTime> admissionDate, releaseDate;
    //one-to-many with Inpatient
    private List<Inpatient> inpatients;
    //one-to-many with Medication
    private List<Medication> medications;
    //one-to-many with Surgical
    private List<Surgical> surgicals;

    /**
     * default no-args constructor
     */
    public Patient() {
        //initialize all lists to avoid NPEs
        patientId = new SimpleIntegerProperty();
        inpatients = new ArrayList<>();
        medications = new ArrayList<>();
        surgicals = new ArrayList<>();
        this.lastName = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.diagnosis = new SimpleStringProperty();
        this.admissionDate = new SimpleObjectProperty<>();
        this.releaseDate = new SimpleObjectProperty<>();
    }

    /*
     constructor that initializes all fields with the exception of the lists;
     */
    public Patient(int patientId, String lastName, String firstName, 
            String diagnosis, LocalDateTime admissionDate, LocalDateTime releaseDate) {
        //call the default constructor first
        this();
        this.patientId = new SimpleIntegerProperty(patientId);
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.admissionDate = new SimpleObjectProperty<>(admissionDate);
        this.releaseDate = new SimpleObjectProperty<>(releaseDate);
    }

    //getters and setters
    public final int getPatientId() {
        return patientId.get();
    }

    public final void setPatientId(int patientId) {
        this.patientId.set(patientId);
    }
    
    public final IntegerProperty patientIdProperty(){
        return patientId;
    }

    public final String getLastName() {
        return lastName.get();
    }

    public final void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public final StringProperty lastNameProperty(){
        return lastName;
    }

    public final String getFirstName() {
        return firstName.get();
    }

    public final void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public final StringProperty firstNameProperty(){
        return firstName;
    }
    
    public final String getDiagnosis() {
        return diagnosis.get();
    }

    public void setInpatients(List<Inpatient> inpatients) {
        this.inpatients = inpatients;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public void setSurgicals(List<Surgical> surgicals) {
        this.surgicals = surgicals;
    }

    public final void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }

    public final StringProperty diagnosisProperty(){
        return diagnosis;
    }
    
    public final LocalDateTime getAdmissionDate() {
        return admissionDate.get();
    }

    public final void setAdmissionDate(LocalDateTime admissionDate) {
        this.admissionDate.set(admissionDate);
    }
    
    public final ObjectProperty<LocalDateTime> admissionDateProperty(){
        return admissionDate;
    }

    public final LocalDateTime getReleaseDate() {
        return releaseDate.get();
    }

    public final void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public final ObjectProperty<LocalDateTime> releaseDateProperty(){
        return releaseDate;
    }
    //only getters, no setters for children lists
    public List<Inpatient> getInpatients() {
        return inpatients;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public List<Surgical> getSurgicals() {
        return surgicals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.patientId.get();
        hash = 73 * hash + Objects.hashCode(this.lastName.get());
        hash = 73 * hash + Objects.hashCode(this.firstName.get());
        hash = 73 * hash + Objects.hashCode(this.diagnosis.get());
        hash = 73 * hash + Objects.hashCode(this.admissionDate.get());
        hash = 73 * hash + Objects.hashCode(this.releaseDate.get());
        hash = 73 * hash + Objects.hashCode(this.inpatients);
        hash = 73 * hash + Objects.hashCode(this.medications);
        hash = 73 * hash + Objects.hashCode(this.surgicals);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (this.patientId.get() != other.patientId.get()) {
            return false;
        }
        if (!Objects.equals(this.lastName.get(), other.lastName.get())) {
            return false;
        }
        if (!Objects.equals(this.firstName.get(), other.firstName.get())) {
            return false;
        }
        if (!Objects.equals(this.diagnosis.get(), other.diagnosis.get())) {
            return false;
        }
        if (!Objects.equals(this.admissionDate.get(), other.admissionDate.get())) {
            return false;
        }
        if (!Objects.equals(this.releaseDate.get(), other.releaseDate.get())) {
            return false;
        }
        if (!Objects.equals(this.inpatients, other.inpatients)) {
            return false;
        }
        if (!Objects.equals(this.medications, other.medications)) {
            return false;
        }
        return Objects.equals(this.surgicals, other.surgicals);
    }

    @Override
    public String toString() {
        return "Patient{" + "patientId=" + patientId.get()+ ", lastName=" + lastName.get()
                + ", firstName=" + firstName.get() + ", diagnosis=" + 
                diagnosis.get() + ", admissionDate=" + admissionDate.get() 
                + ", releaseDate=" + releaseDate.get() + ", inpatients=" + 
                inpatients + ", medications=" + medications + ", surgicals=" + surgicals + '}';
    }
}