package ca.vgorcinschi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *this is the master record, db table PATIENT
 * 
 * @author vgorcinschi
 */
public class Patient implements Serializable{
    //primary key
    private int patientId;
    //columns: "LASTNAME", "FIRSTNAME", "DIAGNOSIS"
    private SimpleStringProperty lastName, firstName, diagnosis;
    //columns: "ADMISSIONDATE", "RELEASEDATE"
    private LocalDateTime admissionDate, releaseDate;
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
        inpatients = new ArrayList<>();
        medications = new ArrayList<>();
        surgicals = new ArrayList<>();
        this.lastName = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.diagnosis = new SimpleStringProperty();
    }

    /*
     constructor that initializes all fields with the exception of the lists;
     */
    public Patient(int patientId, String lastName, String firstName, 
            String diagnosis, LocalDateTime admissionDate, LocalDateTime releaseDate) {
        //call the default constructor first
        this();
        this.patientId = patientId;
        this.lastName = new SimpleStringProperty(lastName);
        this.firstName = new SimpleStringProperty(firstName);
        this.diagnosis = new SimpleStringProperty(diagnosis);
        this.admissionDate = admissionDate;
        this.releaseDate = releaseDate;
    }

    //getters and setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public StringProperty lastNameProperty(){
        return lastName;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty(){
        return firstName;
    }
    
    public String getDiagnosis() {
        return diagnosis.get();
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }

    public StringProperty diagnosisProperty(){
        return diagnosis;
    }
    
    public LocalDateTime getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDateTime admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
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
        hash = 73 * hash + this.patientId;
        hash = 73 * hash + Objects.hashCode(this.lastName.get());
        hash = 73 * hash + Objects.hashCode(this.firstName.get());
        hash = 73 * hash + Objects.hashCode(this.diagnosis.get());
        hash = 73 * hash + Objects.hashCode(this.admissionDate);
        hash = 73 * hash + Objects.hashCode(this.releaseDate);
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
        if (this.patientId != other.patientId) {
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
        if (!Objects.equals(this.admissionDate, other.admissionDate)) {
            return false;
        }
        if (!Objects.equals(this.releaseDate, other.releaseDate)) {
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
        return "Patient{" + "patientId=" + patientId + ", lastName=" + lastName.get()
                + ", firstName=" + firstName.get() + ", diagnosis=" + 
                diagnosis.get() + ", admissionDate=" + admissionDate + ", releaseDate=" + releaseDate + ", inpatients=" + inpatients + ", medications=" + medications + ", surgicals=" + surgicals + '}';
    }
}
