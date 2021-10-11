package model;

/**
 * Class object of the Outsourced subclass of parts.
 * @author Andrew Cesar-Metzgus
 */
public class Outsourced extends Part {
    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        validateMinMax(min, max);
        validateStock(stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter method for the company name.
     * @param companyName String.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     *  Getter method for the company name.
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
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
