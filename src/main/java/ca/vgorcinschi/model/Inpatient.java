package ca.vgorcinschi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * this is a detail record for the Patient class, db table INPATIENT
 *
 * @author vgorcinschi
 */
public class Inpatient implements Serializable{

    //primary key
    private int id;
    //foreign key
    private int patientId;
    //db column "DATEOFSTAY", we will convert to Timestamp when talking to db
    private LocalDateTime dateOfStay;
    //column "ROOMNUMBER"
    private String roomNumber;
    //columns: "DAILYRATE", "SUPPLIES", "SERVICES"
    private BigDecimal dailyRate, supplies, services;

    /**
     * no-args constructor
     */
    public Inpatient() {
    }

    /*
     constructor that initializes all fields;
     */
    public Inpatient(int id, int patientId, LocalDateTime dateOfStay, String roomNumber, BigDecimal dailyRate, BigDecimal supplies, BigDecimal services) {
        this.id = id;
        this.patientId = patientId;
        this.dateOfStay = dateOfStay;
        this.roomNumber = roomNumber;
        this.dailyRate = dailyRate;
        this.supplies = supplies;
        this.services = services;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getDateOfStay() {
        return dateOfStay;
    }

    public void setDateOfStay(LocalDateTime dateOfStay) {
        this.dateOfStay = dateOfStay;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public BigDecimal getSupplies() {
        return supplies;
    }

    public void setSupplies(BigDecimal supplies) {
        this.supplies = supplies;
    }

    public BigDecimal getServices() {
        return services;
    }

    public void setServices(BigDecimal services) {
        this.services = services;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.id;
        hash = 43 * hash + this.patientId;
        hash = 43 * hash + Objects.hashCode(this.dateOfStay);
        hash = 43 * hash + Objects.hashCode(this.roomNumber);
        hash = 43 * hash + Objects.hashCode(this.dailyRate);
        hash = 43 * hash + Objects.hashCode(this.supplies);
        hash = 43 * hash + Objects.hashCode(this.services);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inpatient other = (Inpatient) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.patientId != other.patientId) {
            return false;
        }
        if (!Objects.equals(this.dateOfStay, other.dateOfStay)) {
            return false;
        }
        if (!Objects.equals(this.roomNumber, other.roomNumber)) {
            return false;
        }
        if (!Objects.equals(this.dailyRate, other.dailyRate)) {
            return false;
        }
        if (!Objects.equals(this.supplies, other.supplies)) {
            return false;
        }
        return Objects.equals(this.services, other.services);
    }

    @Override
    public String toString() {
        return "Inpatient{" + "id=" + id + ", patientId=" + patientId + ", dateOfStay=" + dateOfStay + ", roomNumber=" + roomNumber + ", dailyRate=" + dailyRate + ", supplies=" + supplies + ", services=" + services + '}';
    }
}