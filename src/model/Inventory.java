package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Static class object of active inventory.
 * @author Andrew Cesar-Metzgus
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to active inventory.
     * @param newPart Part to add.
     */
    public static void addPart(Part newPart) { allParts.add(newPart); }

    /**
     * Adds product to active inventory.
     * @param newProduct Product to add.
     */
    public static void addProduct(Product newProduct) { allProducts.add(newProduct); }

    /**
     * Searches active inventory for a part by ID.
     * @param partId Part id to search.
     * @return ObservableList with all matching results.
     */
    public static ObservableList<Part> lookupPart(int partId) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for (Part part: allParts){
            if (part.getId() == partId)
                filteredParts.add(part);
        }

        return filteredParts;
    }

    /**
     * Searches active inventory for a product by id.
     * @param productId Part id to search.
     * @return ObservableList with all matching results.
     */
    public static ObservableList<Product> lookupProduct(int productId) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for (Product product: allProducts){
            if (product.getId() == productId)
                filteredProducts.add(product);
        }

        return filteredProducts;
    }

    /**
     * Searches active inventory for a part by name.
     * @param partName Part name to search.
     * @return ObservableList with all matching results.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for (Part part: allParts){
            if (part.getName().toLowerCase().contains(partName.toLowerCase()))
                filteredParts.add(part);
        }

        return filteredParts;
    }

    /**
     * Searches active inventory for a product by name.
     * @param productName Part name to search.
     * @return ObservableList with all matching results.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for (Product product: allProducts){
            if (product.getName().toLowerCase().contains(productName.toLowerCase()))
                filteredProducts.add(product);
        }

        return filteredProducts;
    }

    /**
     * Replaces existing part with new instance of the part. ID remains constance and is inserted at the same
     * index of previous part.
     * @param index Index of the original part.
     * @param newPart Instance of the new part.
     */
    public static void updatePart(int index, Outsourced newPart) {
        allParts.set(index, newPart);
    }

    /**
     * Replaces existing part with new instance of the part. ID remains constance and is inserted at the same
     * index of previous part.
     * @param index Index of the original part.
     * @param newPart Instance of the new part.
     */
    public static void updatePart(int index, InHouse newPart) {
        allParts.set(index, newPart);
    }

    /**
     * Replaces existing product with new instance of the product. ID remains constance and is inserted at the same
     * index of previous product.
     * @param index Index of the original product.
     * @param newPart Instance of the new product.
     */
    public static void updateProduct(int index, Product newProduct) { allProducts.set(index, newProduct); }

    /**
     * Removes part from active inventory.
     * @param selectedPart Part to remove.
     */
    public static void deletePart(Part selectedPart) { allParts.remove(selectedPart); }

    /**
     * Removes product from active inventory after checking that no parts are associated.
     * @param selectedProduct Product to delete.
     * @throws Exception
     */
    public static void deleteProduct(Product selectedProduct) throws Exception {
        selectedProduct.validateDeletable();
        allProducts.remove(selectedProduct);
    }

    /**
     * Getter method for all active parts.
     * @return ObservableList of active parts.
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * Getter method for all active products.
     * @return ObservableList of all active products.
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }

}
