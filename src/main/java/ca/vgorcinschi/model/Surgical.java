package ca.vgorcinschi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * this is a detail record for the Patient class, db table SURGICAL
 *
 * @author vgorcinschi
 */
public class Surgical implements Serializable, Identifiable {

    //primary key
    private IntegerProperty id;
    //foreign key
    private IntegerProperty patientId;
    //db column "DATEOFSURGERY", we will convert to Timestamp when talking to db
    private ObjectProperty<LocalDateTime> dateOfSurgery;
    //column "SURGERY"
    private StringProperty surgery;
    //columns: "ROOMFEE", "SURGEONFEE", "SUPPLIES"
    private ObjectProperty<BigDecimal> roomFee, surgeonFee, supplies;

    /**
     * no-args constructor
     */
    public Surgical() {
        id = new SimpleIntegerProperty();
        patientId = new SimpleIntegerProperty();
        dateOfSurgery = new SimpleObjectProperty<>();
        surgery = new SimpleStringProperty();
        roomFee = new SimpleObjectProperty<>();
        surgeonFee = new SimpleObjectProperty<>();
        supplies = new SimpleObjectProperty<>();
    }

    /*
     constructor that initializes all fields;
     */
    public Surgical(int id, int patientId, LocalDateTime dateOfSurgery, String surgery,
            BigDecimal roomFee, BigDecimal surgeonFee, BigDecimal supplies) {
        this.id = new SimpleIntegerProperty(id);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.dateOfSurgery = new SimpleObjectProperty<>(dateOfSurgery);
        this.surgery = new SimpleStringProperty(surgery);
        this.roomFee = new SimpleObjectProperty<>(roomFee);
        this.surgeonFee = new SimpleObjectProperty<>(surgeonFee);
        this.supplies = new SimpleObjectProperty<>(supplies);
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

    public final LocalDateTime getDateOfSurgery() {
        return dateOfSurgery.get();
    }

    public final void setDateOfSurgery(LocalDateTime dateOfSurgery) {
        this.dateOfSurgery.set(dateOfSurgery);
    }

    public final ObjectProperty<LocalDateTime> dateOfSurgery() {
        return dateOfSurgery;
    }

    public final String getSurgery() {
        return surgery.get();
    }

    public final void setSurgery(String surgery) {
        this.surgery.set(surgery);
    }

    public final StringProperty surgeryProperty() {
        return surgery;
    }

    public final BigDecimal getRoomFee() {
        return roomFee.get();
    }

    public final void setRoomFee(BigDecimal roomFee) {
        this.roomFee.set(roomFee);
    }

    public final ObjectProperty<BigDecimal> roomFeeProperty(){
        return roomFee;
    }
    
    public final BigDecimal getSurgeonFee() {
        return surgeonFee.get();
    }

    public final void setSurgeonFee(BigDecimal surgeonFee) {
        this.surgeonFee.set(surgeonFee);
    }

    public final ObjectProperty<BigDecimal> surgeonFeeProperty(){
        return surgeonFee;
    }
    
    public final BigDecimal getSupplies() {
        return supplies.get();
    }

    public final void setSupplies(BigDecimal supplies) {
        this.supplies.set(supplies);
    }

    public final ObjectProperty<BigDecimal> suppliesProperty(){
        return supplies;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id.get();
        hash = 23 * hash + this.patientId.get();
        hash = 23 * hash + Objects.hashCode(this.dateOfSurgery.get());
        hash = 23 * hash + Objects.hashCode(this.surgery.get());
        hash = 23 * hash + Objects.hashCode(this.roomFee.get());
        hash = 23 * hash + Objects.hashCode(this.surgeonFee.get());
        hash = 23 * hash + Objects.hashCode(this.supplies.get());
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
        if (this.id.get() != other.id.get()) {
            return false;
        }
        if (this.patientId.get() != other.patientId.get()) {
            return false;
        }
        if (!Objects.equals(this.dateOfSurgery.get(), other.dateOfSurgery.get())) {
            return false;
        }
        if (!Objects.equals(this.surgery.get(), other.surgery.get())) {
            return false;
        }
        if (!Objects.equals(this.roomFee.get(), other.roomFee.get())) {
            return false;
        }
        if (!Objects.equals(this.surgeonFee.get(), other.surgeonFee.get())) {
            return false;
        }
        return Objects.equals(this.supplies.get(), other.supplies.get());
    }

    @Override
    public String toString() {
        return "Surgical{" + "id=" + id.get() + ", patientId=" + patientId.get()
                + ", dateOfSurgery=" + dateOfSurgery.get() + ", surgery=" + surgery.get()
                + ", roomFee=" + roomFee.get() + ", surgeonFee=" + surgeonFee.get()
                + ", supplies=" + supplies.get() + '}';
    }
}
