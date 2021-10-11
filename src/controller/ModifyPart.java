package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class object for the Modify Part view.
 * @author Andrew Cesar-Metzgus
 */
public class ModifyPart implements Initializable {
    public Label manufactureLabel;
    public RadioButton inhouseRadio;
    public ToggleGroup tGroup;
    public RadioButton outsourcedRadio;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField stockTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public TextField manufactureTextField;
    public Button saveButton;
    public Button cancelButton;
    public Label modPartErrorLabel;

    private static Part selectedPart;
    private static int selectedPartIndex;

    /**
     * Static method to pass data from the first screen to the modify part screen.
     * @param modPart The Part to modify.
     */
    public static void setPartToMod(Part modPart){
        selectedPart = modPart;
        selectedPartIndex = Inventory.getAllParts().indexOf(selectedPart);
    }

    /**
     * Modify Part view initializer.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setModPart();
    }

    /**
     * Sets the part's data to the text fields.
     * <p><br>
     * <b>LOGICAL ERROR</b>
     * Since the part being passed is an instance of the Part class,
     * this method checks if it's actually an instances of one of Part's
     * subclass and casts tot that part. This allows all form fields to be
     * filled and the user can change subclass type within modification.
     * </p>
     */
    public void setModPart(){
        if (selectedPart instanceof InHouse){
            InHouse modPart = (InHouse) selectedPart;
            idTextField.setText(String.valueOf(modPart.getId()));
            nameTextField.setText(modPart.getName());
            stockTextField.setText(String.valueOf(modPart.getStock()));
            priceTextField.setText(String.valueOf(modPart.getPrice()));
            maxTextField.setText(String.valueOf(modPart.getMax()));
            minTextField.setText(String.valueOf(modPart.getMin()));
            manufactureTextField.setText(String.valueOf(modPart.getMachineId()));
        }
        else if (selectedPart instanceof Outsourced){
            Outsourced modPart = (Outsourced) selectedPart;
            idTextField.setText(String.valueOf(modPart.getId()));
            nameTextField.setText(modPart.getName());
            stockTextField.setText(String.valueOf(modPart.getStock()));
            priceTextField.setText(String.valueOf(modPart.getPrice()));
            maxTextField.setText(String.valueOf(modPart.getMax()));
            minTextField.setText(String.valueOf(modPart.getMin()));
            manufactureTextField.setText(modPart.getCompanyName());
            tGroup.selectToggle(outsourcedRadio);
            manufactureLabel.setText("Company Name");
        }
    }

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
     * Saves new part that was created in the add part view. Uses the static
     * method within the inventory class to replace the part at the same
     * index as the original part that was modified.
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
                        Integer.parseInt(idTextField.getText()),
                        nameTextField.getText(),
                        Double.parseDouble(priceTextField.getText()),
                        Integer.parseInt(stockTextField.getText()),
                        Integer.parseInt(minTextField.getText()),
                        Integer.parseInt(maxTextField.getText()),
                        Integer.parseInt(manufactureTextField.getText())
                );

                Inventory.updatePart(selectedPartIndex, newPart);
                closeWindow();
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("empty String")) {
                    modPartErrorLabel.setText("All fields required.");
                }
                else if (e.getMessage().contains("For input string")){
                    modPartErrorLabel.setText("Verify input values are valid.");
                }
                else {
                    modPartErrorLabel.setText(e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
        }
        else {
            try {
                Outsourced newPart = new Outsourced(
                        Integer.parseInt(idTextField.getText()),
                        nameTextField.getText(),
                        Double.parseDouble(priceTextField.getText()),
                        Integer.parseInt(stockTextField.getText()),
                        Integer.parseInt(minTextField.getText()),
                        Integer.parseInt(maxTextField.getText()),
                        manufactureTextField.getText()
                );

                Inventory.updatePart(selectedPartIndex, newPart);
                closeWindow();
            } catch (IllegalArgumentException e){
                if (e.getMessage().contains("empty String")){
                    modPartErrorLabel.setText("All fields required.");
                }
                else if (e.getMessage().contains("For input string")){
                    modPartErrorLabel.setText("Verify input values are valid.");
                }
                else {
                    modPartErrorLabel.setText(e.getMessage());
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
