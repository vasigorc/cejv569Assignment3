package ca.vgorcinschi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *this is the master record, db table PATIENT
 * 
 * @author vgorcinschi
 */
public class Patient implements Serializable{
    //primary key
    private int patientId;
    //columns: "LASTNAME", "FIRSTNAME", "DIAGNOSIS"
    private String lastName, firstName, diagnosis;
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
    }

    /*
     constructor that initializes all fields with the exception of the lists;
     */
    public Patient(int patientId, String lastName, String firstName, 
            String diagnosis, LocalDateTime admissionDate, LocalDateTime releaseDate) {
        //call the default constructor first
        this();
        this.patientId = patientId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.diagnosis = diagnosis;
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
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
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
}
