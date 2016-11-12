package ca.vgorcinschi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * this is a detail record for the Patient class, db table MEDICATION
 *
 * @author vgorcinschi
 */
public class Medication implements Serializable, Identifiable{

    //primary key
    private int id;
    //foreign key
    private int patientId;
    //db column "DATEOFMED", we will convert to Timestamp when talking to db
    private LocalDateTime dateOfMedication;
    //db column "MED"
    private String med;
    //columns: "UNITCOST", "UNITS"
    private BigDecimal unitCost, units;

    /**
     * no-args constructor
     */
    public Medication() {
    }

    /*
     constructor that initializes all fields;
     */
    public Medication(int id, int patientId, LocalDateTime dateOfSurgery, 
            String med, BigDecimal unitCost, BigDecimal units) {
        this.id = id;
        this.patientId = patientId;
        this.dateOfMedication = dateOfSurgery;
        this.med = med;
        this.unitCost = unitCost;
        this.units = units;
    }

    @Override
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

    public LocalDateTime getDateOfMedication() {
        return dateOfMedication;
    }

    public void setDateOfMedication(LocalDateTime dateOfMedication) {
        this.dateOfMedication = dateOfMedication;
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.id;
        hash = 83 * hash + this.patientId;
        hash = 83 * hash + Objects.hashCode(this.dateOfMedication);
        hash = 83 * hash + Objects.hashCode(this.med);
        hash = 83 * hash + Objects.hashCode(this.unitCost);
        hash = 83 * hash + Objects.hashCode(this.units);
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
        final Medication other = (Medication) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.patientId != other.patientId) {
            return false;
        }
        if (!Objects.equals(this.dateOfMedication, other.dateOfMedication)) {
            return false;
        }
        if (!Objects.equals(this.med, other.med)) {
            return false;
        }
        if (!Objects.equals(this.unitCost, other.unitCost)) {
            return false;
        }
        return Objects.equals(this.units, other.units);
    }

    @Override
    public String toString() {
        return "Medication{" + "id=" + id + ", patientId=" + patientId + ", dateOfSurgery=" + dateOfMedication + ", med=" + med + ", unitCost=" + unitCost + ", units=" + units + '}';
    }
}