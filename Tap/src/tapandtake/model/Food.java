package tapandtake.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is to represent a Food that contain the foodName, unitPrice, quantity
 * @author XiaoyuLiang
 */
public class Food {
    
    private final StringProperty foodName;
    private final DoubleProperty unitPrice;
    private final IntegerProperty quantity;
    
    /**
     * Default food
     */
    public Food() {
        this(null, 0);
    }
    
    /**
     * Constructor with some initial data
     * @param foodName
     * @param unitPrice 
     */
    public Food(String foodName, double unitPrice) {
        this.foodName = new SimpleStringProperty(foodName);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
        this.quantity = new SimpleIntegerProperty(1);
    }

    public String getFoodName() {
        return foodName.get();
    }
    
    public StringProperty foodNameProperty() {
        return foodName;
    }

    public Double getUnitPrice() {
        return unitPrice.get();
    }
    
    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }
    
    public int getQuantity() {
        return quantity.get();
    }
    
    public IntegerProperty quantityProperty() {
        return quantity;
    }
    
    public void setQuantity(int quantity){
        this.quantity.set(quantity);
    }
    
    public double getSubotal(){
        return this.getUnitPrice() * this.getQuantity();
    }
    
    public DoubleProperty subotalProperty() {
        return new SimpleDoubleProperty(this.getSubotal());
    }
    
    /**
     * This method  is to create the String in specific format that include the 
     * name of the food, the unit price of the food and the quantity of the food 
     * @return 
     */
    public String toFood(){
        String format = "%-20s%6.2f%8d%12.2f";
        return String.format(format,this.foodName.get(), this.getUnitPrice(),this.quantity.get(), this.getSubotal());
    }
    
    /**
     * This method is to override the toString
     * @return the name of the food
     */
    @Override
    public String toString() {
        return foodName.get();
    }
}
