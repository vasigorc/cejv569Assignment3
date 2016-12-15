/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.vgorcinschi.controller;

import ca.vgorcinschi.model.Patient;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

/**
 *
 * @author vgorcinschi
 */
@Component
public class ReportController implements Command {

    //report    
    @FXML
    GridPane reportGrid;
    @FXML
    Text repPtId;
    @FXML
    Text repPtLasName;
    @FXML
    Text repPtFirstName;
    @FXML
    Text repPtAdmDate;
    @FXML
    Text repPtRelDate;
    @FXML
    Text repTotalPriceInpatients;
    @FXML
    Text repTotalPriceMedication;
    @FXML
    Text repTotalPriceSurgery;
    @FXML
    Text repGrandTotal;
    @FXML
    Button repPtCloseBtn;
    //to print human readable date and time format in the report
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    //set currency formatter
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.CANADA_FRENCH);

    private Patient currentPatient;
    private TabMediator mediator;
    
    @FXML
    private void initialize(){}

    public TabMediator getMediator() {
        return mediator;
    }

    public void setMediator(TabMediator mediator) {
        this.mediator = mediator;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
        //update the report
        rebindReport();
    }

    @Override
    public void execute() {
        mediator.closePopUp();
    }

    public void rebindReport() {
        if (getCurrentPatient() != null) {
            System.out.println("fields not instantiated: " + (repPtId == null));
            repPtId.setText(String.valueOf(getCurrentPatient().getPatientId()));
            repPtLasName.setText(getCurrentPatient().getLastName());
            repPtFirstName.setText(getCurrentPatient().getFirstName());
            if (getCurrentPatient().getAdmissionDate() != null) {
                repPtAdmDate.setText(getCurrentPatient().getAdmissionDate().format(formatter));
            }
            if (getCurrentPatient().getReleaseDate() != null) {
                repPtRelDate.setText(getCurrentPatient().getReleaseDate().format(formatter));
            }
            BigDecimal totalInps = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalMeds = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalSurgs = new BigDecimal(BigInteger.ZERO);
            if (getCurrentPatient().getInpatients() != null && !getCurrentPatient().getInpatients().isEmpty()) {
                totalInps = getCurrentPatient().getInpatients()
                        .stream().map(i -> i.getDailyRate().add(i.getServices()).add(i.getSupplies()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            repTotalPriceInpatients.setText(currencyFormatter.format(totalInps.doubleValue()));
            if (getCurrentPatient().getSurgicals() != null && !getCurrentPatient().getSurgicals().isEmpty()) {
                totalSurgs = getCurrentPatient().getSurgicals()
                        .stream().map(i -> i.getRoomFee().add(i.getSupplies()).add(i.getSurgeonFee()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            repTotalPriceSurgery.setText(currencyFormatter.format(totalSurgs.doubleValue()));
            if (getCurrentPatient().getMedications() != null && !getCurrentPatient().getSurgicals().isEmpty()) {
                totalMeds = getCurrentPatient().getMedications()
                        .stream().map(i -> i.getUnitCost().multiply(i.getUnits()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            repTotalPriceMedication.setText(currencyFormatter.format(totalMeds.doubleValue()));
            BigDecimal sum = totalInps.add(totalMeds).add(totalSurgs);
            repGrandTotal.setText(currencyFormatter.format(sum));
        }
    }
    
    @FXML
    public void closePopUp(){
        execute();
    }
}
