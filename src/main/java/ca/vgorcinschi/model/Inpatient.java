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
 * this is a detail record for the Patient class, db table INPATIENT
 *
 * @author vgorcinschi
 */
public class Inpatient implements Serializable, Identifiable {

    //primary key
    private IntegerProperty id;
    //foreign key
    private IntegerProperty patientId;
    //db column "DATEOFSTAY", we will convert to Timestamp when talking to db
    private ObjectProperty<LocalDateTime> dateOfStay;
    //column "ROOMNUMBER"
    private StringProperty roomNumber;
    //columns: "DAILYRATE", "SUPPLIES", "SERVICES"
    private ObjectProperty<BigDecimal> dailyRate, supplies, services;

    /**
     * no-args constructor
     */
    public Inpatient() {
        id = new SimpleIntegerProperty();
        patientId = new SimpleIntegerProperty();
        dateOfStay = new SimpleObjectProperty<>();
        roomNumber = new SimpleStringProperty();
        dailyRate = new SimpleObjectProperty<>();
        supplies = new SimpleObjectProperty<>();
        services = new SimpleObjectProperty<>();
    }

    /*
     constructor that initializes all fields;
     */
    public Inpatient(int id, int patientId, LocalDateTime dateOfStay, String roomNumber, BigDecimal dailyRate, BigDecimal supplies, BigDecimal services) {
        this.id = new SimpleIntegerProperty(id);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.dateOfStay = new SimpleObjectProperty<>(dateOfStay);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.dailyRate = new SimpleObjectProperty<>(dailyRate);
        this.supplies = new SimpleObjectProperty<>(supplies);
        this.services = new SimpleObjectProperty<>(services);
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

    public final LocalDateTime getDateOfStay() {
        return dateOfStay.get();
    }

    public final void setDateOfStay(LocalDateTime dateOfStay) {
        this.dateOfStay.set(dateOfStay);
    }

    public final ObjectProperty<LocalDateTime> dateOfStayProperty() {
        return dateOfStay;
    }

    public final String getRoomNumber() {
        return roomNumber.get();
    }

    public final void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public final StringProperty roomNumberProperty() {
        return roomNumber;
    }

    public final BigDecimal getDailyRate() {
        return dailyRate.get();
    }

    public final void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate.set(dailyRate);
    }

    public final ObjectProperty<BigDecimal> dailyRateProperty() {
        return dailyRate;
    }

    public final BigDecimal getSupplies() {
        return supplies.get();
    }

    public final void setSupplies(BigDecimal supplies) {
        this.supplies.set(supplies);
    }

    public final ObjectProperty<BigDecimal> suppliesProperty() {
        return supplies;
    }

    public final BigDecimal getServices() {
        return services.get();
    }

    public final void setServices(BigDecimal services) {
        this.services.set(services);
    }

    public final ObjectProperty<BigDecimal> servicesProperty() {
        return services;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.id.get();
        hash = 43 * hash + this.patientId.get();
        hash = 43 * hash + Objects.hashCode(this.dateOfStay.get());
        hash = 43 * hash + Objects.hashCode(this.roomNumber.get());
        hash = 43 * hash + Objects.hashCode(this.dailyRate.get());
        hash = 43 * hash + Objects.hashCode(this.supplies.get());
        hash = 43 * hash + Objects.hashCode(this.services.get());
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
        if (this.id.get() != other.id.get()) {
            return false;
        }
        if (this.patientId.get() != other.patientId.get()) {
            return false;
        }
        if (!Objects.equals(this.dateOfStay.get(), other.dateOfStay.get())) {
            return false;
        }
        if (!Objects.equals(this.roomNumber.get(), other.roomNumber.get())) {
            return false;
        }
        if (!Objects.equals(this.dailyRate.get(), other.dailyRate.get())) {
            return false;
        }
        if (!Objects.equals(this.supplies.get(), other.supplies.get())) {
            return false;
        }
        return Objects.equals(this.services.get(), other.services.get());
    }

    @Override
    public String toString() {
        return "Inpatient{" + "id=" + id.get() + ", patientId="
                + patientId.getName() + ", dateOfStay=" + dateOfStay.get()
                + ", roomNumber=" + roomNumber.get() + ", dailyRate=" + dailyRate.get()
                + ", supplies=" + supplies.get() + ", services=" + services.get() + '}';
    }
}
