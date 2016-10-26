package ca.vgorcinschi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * this is a detail record for the Patient class, db table SURGICAL
 *
 * @author vgorcinschi
 */
public class Surgical implements Serializable{

    //primary key
    private int id;
    //foreign key
    private int patientId;
    //db column "DATEOFSURGERY", we will convert to Timestamp when talking to db
    private LocalDateTime dateOfSurgery;
    //column "SURGERY"
    private String surgery;
    //columns: "ROOMFEE", "SURGEONFEE", "SUPPLIES"
    private BigDecimal roomFee, surgeonFee, supplies;

    /**
     * no-args constructor
     */
    public Surgical() {
    }

    /*
     constructor that initializes all fields;
     */
    public Surgical(int id, int patientId, LocalDateTime dateOfSurgery, String surgery, 
            BigDecimal roomFee, BigDecimal surgeonFee, BigDecimal supplies) {
        this.id = id;
        this.patientId = patientId;
        this.dateOfSurgery = dateOfSurgery;
        this.surgery = surgery;
        this.roomFee = roomFee;
        this.surgeonFee = surgeonFee;
        this.supplies = supplies;
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

    public LocalDateTime getDateOfSurgery() {
        return dateOfSurgery;
    }

    public void setDateOfSurgery(LocalDateTime dateOfSurgery) {
        this.dateOfSurgery = dateOfSurgery;
    }

    public String getSurgery() {
        return surgery;
    }

    public void setSurgery(String surgery) {
        this.surgery = surgery;
    }

    public BigDecimal getRoomFee() {
        return roomFee;
    }

    public void setRoomFee(BigDecimal roomFee) {
        this.roomFee = roomFee;
    }

    public BigDecimal getSurgeonFee() {
        return surgeonFee;
    }

    public void setSurgeonFee(BigDecimal surgeonFee) {
        this.surgeonFee = surgeonFee;
    }

    public BigDecimal getSupplies() {
        return supplies;
    }

    public void setSupplies(BigDecimal supplies) {
        this.supplies = supplies;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + this.patientId;
        hash = 23 * hash + Objects.hashCode(this.dateOfSurgery);
        hash = 23 * hash + Objects.hashCode(this.surgery);
        hash = 23 * hash + Objects.hashCode(this.roomFee);
        hash = 23 * hash + Objects.hashCode(this.surgeonFee);
        hash = 23 * hash + Objects.hashCode(this.supplies);
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
        final Surgical other = (Surgical) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.patientId != other.patientId) {
            return false;
        }
        if (!Objects.equals(this.dateOfSurgery, other.dateOfSurgery)) {
            return false;
        }
        if (!Objects.equals(this.surgery, other.surgery)) {
            return false;
        }
        if (!Objects.equals(this.roomFee, other.roomFee)) {
            return false;
        }
        if (!Objects.equals(this.surgeonFee, other.surgeonFee)) {
            return false;
        }
        return Objects.equals(this.supplies, other.supplies);
    }

    @Override
    public String toString() {
        return "Surgical{" + "id=" + id + ", patientId=" + patientId + ", dateOfSurgery=" + dateOfSurgery + ", surgery=" + surgery + ", roomFee=" + roomFee + ", surgeonFee=" + surgeonFee + ", supplies=" + supplies + '}';
    }
}