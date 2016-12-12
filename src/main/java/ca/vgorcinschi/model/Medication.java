package ca.vgorcinschi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * this is a detail record for the Patient class, db table MEDICATION
 *
 * @author vgorcinschi
 */
public class Medication implements Serializable, Identifiable {    
    
    //primary key
    private IntegerProperty id;
    //foreign key
    private IntegerProperty patientId;
    //db column "DATEOFMED", we will convert to Timestamp when talking to db
    private ObjectProperty<LocalDateTime> dateOfMedication;
    //db column "MED"
    private StringProperty med;
    //columns: "UNITCOST", "UNITS"
    private ObjectProperty<BigDecimal> unitCost, units;

    /**
     * no-args constructor
     */
    public Medication() {
        id = new SimpleIntegerProperty();
        patientId = new SimpleIntegerProperty();
        dateOfMedication = new SimpleObjectProperty<>();
        med = new SimpleStringProperty();
        unitCost = new SimpleObjectProperty<>();
        units = new SimpleObjectProperty<>();
    }

    /*
     constructor that initializes all fields;
     */
    public Medication(int id, int patientId, LocalDateTime dateOfSurgery,
            String med, BigDecimal unitCost, BigDecimal units) {
        this.id = new SimpleIntegerProperty(id);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.dateOfMedication = new SimpleObjectProperty<>(dateOfSurgery);
        this.med = new SimpleStringProperty(med);
        this.unitCost = new SimpleObjectProperty<>(unitCost);
        this.units = new SimpleObjectProperty<>(units);
    }

    @Override
    public final int getId() {
        return id.get();
    }

    public final void setId(int id) {
        this.id.set(id);
    }

    public final IntegerProperty idProperty() {
        return id;
    }

    public final int getPatientId() {
        return patientId.get();
    }

    public final void setPatientId(int patientId) {
        this.patientId.set(patientId);
    }

    public final IntegerProperty patientIdProperty() {
        return patientId;
    }

    public final LocalDateTime getDateOfMedication() {
        return dateOfMedication.get();
    }

    public final void setDateOfMedication(LocalDateTime dateOfMedication) {
        this.dateOfMedication.set(dateOfMedication);
    }

    public final ObjectProperty<LocalDateTime> dateOfMedicationProperty() {
        return dateOfMedication;
    }

    public final String getMed() {
        return med.get();
    }

    public final void setMed(String med) {
        this.med.set(med);
    }

    public final StringProperty medProperty() {
        return med;
    }

    public final BigDecimal getUnitCost() {
        return unitCost.get();
    }

    public final void setUnitCost(BigDecimal unitCost) {
        this.unitCost.set(unitCost);
    }

    public final ObjectProperty<BigDecimal> unitCostProperty() {
        return unitCost;
    }

    public final BigDecimal getUnits() {
        return units.get();
    }

    public final void setUnits(BigDecimal units) {
        this.units.set(units);
    }

    public final ObjectProperty<BigDecimal> unitsProperty() {
        return units;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.id.get();
        hash = 83 * hash + this.patientId.get();
        hash = 83 * hash + Objects.hashCode(this.dateOfMedication.get());
        hash = 83 * hash + Objects.hashCode(this.med.get());
        hash = 83 * hash + Objects.hashCode(this.unitCost.get());
        hash = 83 * hash + Objects.hashCode(this.units.get());
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
        if (this.id.get() != other.id.get()) {
            return false;
        }
        if (this.patientId.get() != other.patientId.get()) {
            return false;
        }
        if (!Objects.equals(this.dateOfMedication.get(), other.dateOfMedication.get())) {
            return false;
        }
        if (!Objects.equals(this.med.get(), other.med.get())) {
            return false;
        }
        if (!Objects.equals(this.unitCost.get(), other.unitCost.get())) {
            return false;
        }
        return Objects.equals(this.units.get(), other.units.get());
    }
    
    public static Medication defaultMedication(int patientId){
        return new Medication(0, patientId, LocalDateTime.now(ZoneId.systemDefault()), "", BigDecimal.ZERO, BigDecimal.ZERO);

    }

    @Override
    public String toString() {
        return "Medication{" + "id=" + id.get() + ", patientId=" + patientId.get()
                + ", dateOfSurgery=" + dateOfMedication.get() + ", med=" + med.get()
                + ", unitCost=" + unitCost.get() + ", units=" + units.get() + '}';
    }
}