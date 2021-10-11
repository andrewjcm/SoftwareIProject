package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class object for the add product view.
 * @author Andrew Cesar-Metzgus
 */
public class AddProduct implements Initializable {
    public TextField idTextField;
    public TextField nameTextField;
    public TextField stockTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public Button saveButton;
    public Button cancelButton;
    public TableView allPartsTable;
    public TableColumn allPartIdCol;
    public TableColumn allPartNameCol;
    public TableColumn allInventoryLevelCol;
    public TableColumn allPriceCol;
    public Button addPartButton;
    public Button removePartButton;
    public TableView associatedPartsTable;
    public TableColumn assPartIdCol;
    public TableColumn assPartNameCol;
    public TableColumn assInventoryLevelCol;
    public TableColumn assPriceCol;
    public Label addProductErrorLabel;
    public TextField queryTextFieldParts;
    public Label searchErrorLabel;

    private ObservableList<Part> allAvailParts = FXCollections.observableArrayList();
    private ObservableList<Part> allAssociatedParts = FXCollections.observableArrayList();

    private static int nextId;

    /**
     * Initialization method for the add product view.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allAvailParts.addAll(Inventory.getAllParts());

        allPartsTable.setItems(allAvailParts);
        associatedPartsTable.setItems(allAssociatedParts);

        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Sets the nextId static variable to the last ID that was used when first screen initializes.
     * @param id Unique product id
     */
    public static void setNextId(int id){ nextId = id; }

    /**
     * Auto increments the next product ID.
     * @return Unique product id.
     */
    public static int genNextId(){ return ++nextId; }

    /**
     * Saves new product that was created in the add product view.
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
        try {
            Product newProduct = new Product(
                    genNextId(),
                    nameTextField.getText(),
                    Double.parseDouble(priceTextField.getText()),
                    Integer.parseInt(stockTextField.getText()),
                    Integer.parseInt(minTextField.getText()),
                    Integer.parseInt(maxTextField.getText())
            );

            newProduct.addAssociatedPart(allAssociatedParts);
            Inventory.addProduct(newProduct);
            closeWindow();
        } catch (IllegalArgumentException e){
            if (e.getMessage().contains("empty String")){
                addProductErrorLabel.setText("All fields required.");
            }
            else if (e.getMessage().contains("For input string")){
                addProductErrorLabel.setText("Verify input values are valid.");
            }
            else {
                addProductErrorLabel.setText(e.getMessage());
                System.out.println(e.getMessage());
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
     * Filters the part table based on user input.
     *
     * <p><br>
     * <b>LOGICAL ERROR</b>
     * When looking up by both (int) ID and (String) name,
     * if the name included a number, the filter would return
     * the same part twice. I added a logical check if
     * an instance of that part was already in the results
     * list to ensure that are not duplicates.
     * </p>
     * @param keyEvent User begins typing.
     */
    public void onPartQueryTyped(KeyEvent keyEvent) {
        String qry = queryTextFieldParts.getText();

        ObservableList<Part> result = Inventory.lookupPart(qry);
        try {
            int qryPartId = Integer.parseInt(qry);
            ObservableList<Part> resultId = Inventory.lookupPart(qryPartId);
            boolean present = false;
            for (Part part: result){
                present = part.getId() == resultId.get(0).getId();
                if (present)
                    break;
            }
            if (!present)
                result.addAll(resultId);
        } catch (NumberFormatException e) {
            // Ignore
            System.out.println(e.getMessage());
        }

        if (result.size() == 0)
            searchErrorLabel.setText("No results. Refine search criteria.");
        else
            searchErrorLabel.setText("");

        allPartsTable.setItems(result);
    }

    /**
     * Adds part to the product's associated parts list.
     * @param actionEvent Add button clicked.
     */
    public void onAddPartButton(ActionEvent actionEvent) {
        // Get selected item in the table
        Part selectedPart = (Part)allPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null)
            addProductErrorLabel.setText("No part selected");
        else {
            addProductErrorLabel.setText("");
            allAssociatedParts.add(selectedPart);
        }
    }

    /**
     * Removes part from the product's associated parts list and confirms action with dialog window.
     * @param actionEvent Remove Associated Part button is clicked.
     */
    public void onRemovePartButton(ActionEvent actionEvent) {
        // Get selected item in the table
        Part selectedPart = (Part)associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null)
            addProductErrorLabel.setText("No part selected");
        else {
            addProductErrorLabel.setText("");
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "You can always re-add.");
            confirmDelete.setTitle("Confirm");
            confirmDelete.setHeaderText("Remove " + selectedPart.getName());

            Optional<ButtonType> userResponse = confirmDelete.showAndWait();

            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                allAssociatedParts.remove(selectedPart);
            }
        }
    }

    /**
     * Closes the add part view.
     */
    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
