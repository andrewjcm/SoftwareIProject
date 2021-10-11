package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class object of the product.
 * @author Andrew Cesar-Metzgus
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {
        validateMinMax(min, max);
        validateStock(stock, min, max);
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part associated Part instance
     */
    public void addAssociatedPart(Part part) { associatedParts.add(part); }

    /**
     * @param part associated Part instance
     */
    public void addAssociatedPart(ObservableList<Part> parts) { associatedParts.addAll(parts); }

    /**
     * Removes associated part.
     * @param selectedAssociatedPart to remove
     * @return boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart ) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Remove multiple assocaited parts.
     * @param parts to remove
     * @return boolean
     */
    public boolean deleteAssociatedPart(ObservableList<Part> parts ) {
        return associatedParts.removeAll(parts);
    }

    /**
     * @return the list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() { return this.associatedParts; }

    /**
     * Checks that instance has no associated parts.
     * @throws Exception
     */
    public void validateDeletable() throws Exception {
        if (associatedParts.size() > 0){
            throw new Exception("Deletion denied. First you must remove all associated parts.");
        }
    }

    /**
     * Validator method that checks that min is less than max.
     * @param min int
     * @param max int
     */
    public void validateMinMax(int min, int max){
        if (min > max) throw new IllegalArgumentException("Max must be greater than min.");
    }

    /**
     * Validator method that checks that stock input is within the bounds of min and max.
     * @param stock int
     * @param min int
     * @param max int
     */
    public void validateStock(int stock, int min, int max){
        if (min > stock || max < stock) throw new IllegalArgumentException("Inv must be within bounds of max and min.");
    }
}
