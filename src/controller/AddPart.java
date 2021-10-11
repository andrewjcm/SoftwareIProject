package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import javax.management.Notification;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class object for the Add Part view.
 * @author Andrew Cesar-Metzgus
 */
public class AddPart implements Initializable {
    public Label manufactureLabel;
    public RadioButton inhouseRadio;
    public ToggleGroup tGroup;
    public RadioButton outsourcedRadio;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField stockTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField manufactureTextField;
    public TextField minTextField;
    public Button saveButton;
    public Button cancelButton;
    public Label addPartErrorLabel;

    private static int nextId;

    /**
     * Initialization method for the add part view.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Sets the nextId static variable to the last ID that was used when first screen initializes.
     * @param id Unique part id
     */
    public static void setNextId(int id){ nextId = id; }

    /**
     * Auto increments the next part ID.
     * @return Unique part id.
     */
    public static int genNextId(){ return ++nextId; }

    /**
     * Sets the manufacture label when In-House radio button is selected.
     * @param actionEvent In-House radio button selected.
     */
    public void onInhouseRadio(ActionEvent actionEvent) {
        manufactureLabel.setText("Machine ID");
    }

    /**
     * Sets the manufacture label when Outsourced radio button is selected.
     * @param actionEvent Outsourced radio button selected.
     */
    public void onOutsourcedRadio(ActionEvent actionEvent) {
        manufactureLabel.setText("Company Name");
    }

    /**
     * Saves new part that was created in the add part view.
     * <p><br>
     * <b>RUNTIME ERROR</b>
     *  If the user input the incorrect data type, the program would
     *  crash. This was fixed by catching the exceptions and adding an
     *  error message to the screen.
     *
     *  <b>FUTURE ENHANCEMENT</b>
     *  Catch the exception at each input field and highlighting the
     *  field with an asterisk and individual error message.
     * </></p>
     * @param actionEvent Save button clicked.
     */
    public void onSaveButton(ActionEvent actionEvent) {
        String mfg = manufactureLabel.getText();
        if (mfg.equals("Machine ID")) {
            try {
                InHouse newPart = new InHouse(
                        genNextId(),
                        nameTextField.getText(),
                        Double.parseDouble(priceTextField.getText()),
                        Integer.parseInt(stockTextField.getText()),
                        Integer.parseInt(minTextField.getText()),
                        Integer.parseInt(maxTextField.getText()),
                        Integer.parseInt(manufactureTextField.getText())
                );

                Inventory.addPart(newPart);
                closeWindow();
            } catch (IllegalArgumentException e){
                if (e.getMessage().contains("empty String")){
                    addPartErrorLabel.setText("All fields required.");
                }
                else if (e.getMessage().contains("For input string")){
                    addPartErrorLabel.setText("Verify input values are valid.");
                }
                else {
                    addPartErrorLabel.setText(e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
        }
        else {
            try {
                Outsourced newPart = new Outsourced(
                        genNextId(),
                        nameTextField.getText(),
                        Double.parseDouble(priceTextField.getText()),
                        Integer.parseInt(stockTextField.getText()),
                        Integer.parseInt(minTextField.getText()),
                        Integer.parseInt(maxTextField.getText()),
                        manufactureTextField.getText()
                );

                Inventory.addPart(newPart);
                closeWindow();

            } catch (IllegalArgumentException e){
                if (e.getMessage().contains("empty String")){
                    addPartErrorLabel.setText("All fields required.");
                }
                else if (e.getMessage().contains("For input string")){
                    addPartErrorLabel.setText("Verify input values are valid.");
                }
                else {
                    addPartErrorLabel.setText(e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Cancels the add part action and closes the view.
     * @param actionEvent Cancel button clicked.
     */
    public void onCancelButton(ActionEvent actionEvent) {
        closeWindow();
    }

    /**
     * Closes the add part view.
     */
    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
