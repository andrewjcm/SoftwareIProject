package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class object representing the first screen view of the Inventory
 * Management system.
 * @author Andrew Cesar-Metzgus
 */
public class FirstScreen implements Initializable {

    public TableView partTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partPriceCol;
    public TableView productTable;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productInventoryLevelCol;
    public TableColumn productPriceCol;
    public Button modifyPartButton;
    public Button deletePartButton;
    public Button addPartButton;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProductButton;
    public TextField queryTextFieldParts;
    public TextField queryTextFieldProducts;
    public Label errorMessageLabel;
    public Label searchErrorProducts;
    public Label searchErrorParts;

    /**
     * Initializing method for the first screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        Inventory.addPart(new Outsourced(1, "Breaks", 15.00, 10, 1, 15, "Company"));
        Inventory.addPart(new Outsourced(2, "Wheel", 11.00, 16, 1, 20, "Company"));
        Inventory.addPart(new Outsourced(3, "Seat", 15.00, 10, 1, 30, "Company"));
        Inventory.addProduct(new Product(1000, "Giant Bike", 299.99, 5, 1, 10));
        Inventory.addProduct(new Product(1001, "Tricycle", 99.99, 3, 1, 10));

        // Set next part ID
        ObservableList<Part> partInv = Inventory.getAllParts();
        if (partInv.size() > 0){
            AddPart.setNextId(partInv.get(partInv.size() - 1).getId());
        }
        else {
            AddPart.setNextId(0);
        }

        // Set next Product ID
        ObservableList<Product> productInv = Inventory.getAllProducts();
        if (productInv.size() > 0){
            AddProduct.setNextId(productInv.get(productInv.size() - 1).getId());
        }
        else {
            AddPart.setNextId(0);
        }

    }

    /**
     * On add part button click initializes the Add Part view.
     * @param actionEvent Add part button clicked.
     * @throws IOException
     */
    public void onAddPartButton(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddPart.fxml"));
        Parent root = loader.load();

        // Set stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 400));

        stage.show();
    }

    /**
     *  Initializes the modify part view.
     * @param actionEvent Modify part button clicked.
     * @throws IOException
     */
    public void onModifyPartButton(ActionEvent actionEvent) throws IOException {
        // Get selected item in the table
        Part selectedPart = (Part)partTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null)
            errorMessageLabel.setText("No part selected");
        else {
            errorMessageLabel.setText("");

            ModifyPart.setPartToMod(selectedPart);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
            Parent root = loader.load();

            // Set stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));

            stage.show();

        }
    }

    /**
     * Removes the selected Part from system and confirms with dialog window.
     * @param actionEvent Delete part button clicked.
     */
    public void onDeletePartButton(ActionEvent actionEvent){
        // Get selected item in the table
        Part selectedPart = (Part)partTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null)
            errorMessageLabel.setText("No part selected");
        else {
            errorMessageLabel.setText("");

            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "This is not reversible.");
            confirmDelete.setTitle("Confirm");
            confirmDelete.setHeaderText("Delete " + selectedPart.getName());

            Optional<ButtonType> userResponse = confirmDelete.showAndWait();

            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Initializes the Add Product view.
     * @param actionEvent Add product button clicked.
     * @throws IOException
     */
    public void onAddProductButton(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddProduct.fxml"));
        Parent root = loader.load();

        // Set stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 600));

        stage.show();
    }

    /**
     * Initializes the modify product view.
     * @param actionEvent Modify product button clicked.
     * @throws IOException
     */
    public void onModifyProductButton(ActionEvent actionEvent) throws IOException {
        // Get selected item in the table
        Product selectedProduct = (Product)productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null)
            errorMessageLabel.setText("No product selected");
        else {
            errorMessageLabel.setText("");

            ModifyProduct.setProductToMod(selectedProduct);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent root = loader.load();

            // Set stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 800, 600));

            stage.show();
        }
    }

    /**
     * Removes selected product from the system.
     * @param actionEvent Delete product button clicked.
     */
    public void onDeleteProductButton(ActionEvent actionEvent) {
        // Get selected item in the table
        Product selectedProduct = (Product)productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null)
            errorMessageLabel.setText("No product selected");
        else {
            errorMessageLabel.setText("");

            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, "This is not reversible.");
            confirmDelete.setTitle("Confirm");
            confirmDelete.setHeaderText("Delete " + selectedProduct.getName());

            Optional<ButtonType> userResponse = confirmDelete.showAndWait();

            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                try {
                    Inventory.deleteProduct(selectedProduct);
                } catch (Exception e) {
                    errorMessageLabel.setText(e.getMessage());
                }
            }
        }
    }

    /**
     * Exits the program.
     * @param actionEvent Exit button clicked.
     */
    public void onExit(ActionEvent actionEvent) {
        errorMessageLabel.setText("Exiting program...");
        System.out.println("Exiting program...");
        System.exit(0);
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
     * @param keyEvent User begins to type.
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
        }

        if (result.size() == 0)
            searchErrorParts.setText("No results. Refine search criteria.");
        else
            searchErrorParts.setText("");


        partTable.setItems(result);
    }

    /**
     * Filters the product table based on user input.
     *
     * <p><br>
     * <b>LOGICAL ERROR</b>
     * When looking up by both (int) ID and (String) name,
     * if the name included a number, the filter would return
     * the same Product twice. I added a logical check if
     * an instance of that Product was already in the results
     * list to ensure that are not duplicates.
     * </p>
     * @param keyEvent User begins to type.
     */
    public void onProductQueryTyped(KeyEvent keyEvent) {
        String qry = queryTextFieldProducts.getText();

        ObservableList<Product> result = Inventory.lookupProduct(qry);
        try {
            int qryProductId = Integer.parseInt(qry);
            ObservableList<Product> resultId = Inventory.lookupProduct(qryProductId);
            boolean present = false;
            for (Product product: result){
                present = product.getId() == resultId.get(0).getId();
                if (present)
                    break;
            }
            if (!present)
                result.addAll(resultId);
        } catch (NumberFormatException e) {
            // Ignore
        }

        if (result.size() == 0)
            searchErrorProducts.setText("No results. Refine search criteria.");
        else
            searchErrorProducts.setText("");

        productTable.setItems(result);
    }
}
