package model;

/**
 * Class object of the InHouse subclass of parts.
 * @author Andrew Cesar-Metzgus
 */
public class InHouse extends Part{
    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        validateMinMax(min, max);
        validateStock(stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Setter method for the machine id.
     * @param machineId int
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Getter method fo the machine id.
     * @return machineId int
     */
    public int getMachineId() {
        return machineId;
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
