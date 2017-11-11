package tapandtake.model;

import java.util.Date;
import javafx.collections.ObservableList;

/**
 * This class is to represent a Order that contain the list of food, order date
 * @author XiaoyuLiang
 */
public class Order{
    
    private Date date;
    private ObservableList<Food> foodList;
    
    /**
     * Constructor with some initial data
     * @param foodList 
     */
    public Order(ObservableList<Food> foodList) {
        this.foodList = foodList;
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public ObservableList<Food> getFoodList() {
        return foodList;
    }
    
    /**
     * Calculate the total price of the order
     * @return the double value of total price
     */
    public double getTotal(){
        double total = 0;
        for (int i = 0; i < foodList.size(); i++) {
            total += foodList.get(i).getSubotal();
        }
        return total;
    }
    
    /**
     * Format the total price
     * @return the String value of formated total price
     */
    public String getStringTotal(){
        return String.format("%.2f", this.getTotal());
    }
    
    /**
     * Calculate the total price plus tax
     * @return the double value of total price plus tax
     */
    public double getTotalPlusTax(){
        return this.getTotal() * 1.13;
    }
    
    /**
     * Format the total price plus tax
     * @return the String value of formated total price plus tax
     */
    public String getStringTotalPlusTax(){
        return String.format("%.2f", this.getTotalPlusTax());
    } 
    
    /**
     * This method is to override the toString
     * @return all the formated food value that user ordered
     */
    @Override
    public String toString() {
        //create a format  
        String format = "";

        //use a loop to combine all elements' information
        for (int i = 0; i < foodList.size(); i++) {
            format += foodList.get(i).toFood() + "\n";
        }
        return format;
    }
}
