package tapandtake;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import tapandtake.model.Customer;
import tapandtake.model.Food;
import tapandtake.model.Order;
import tapandtake.util.DateUtil;

/**
 *
 * @author Hamnah Atif & XiaoyuLiang
 */
public class MenuPage extends Application {
    
    //global variables for application layout
    private Stage stage;
    private BorderPane mainPane;
    private Scene scene;

    //data fields and global variable for login page and signup page
    private final TextField tfPhoneNum = new TextField();
    private final TextField tfUsername = new TextField();
    private final PasswordField tfPassword = new PasswordField();
    private ArrayList<Customer> customers = new ArrayList<>();
    private static final int PHONE_NUM_LENGTH = 10;
    private long customerId = 0;
    private String customerName = "";

    //data fields and global variable for Menu Page
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private ListView<Food> foodListView;
    private ImageView imageFood;
    private Label foodNameLabel;
    private Label unitPriceLabel;
    private Button addButton;
    private Button basketButton;

    //global variables for Cart Page
    private Label lblCustomerName;
    private Label lblPhoneNo;
    private TableView<Food> foodTable;
    private TableColumn<Food, String> foodNameColumn;
    private TableColumn<Food, Number> unitPriceColumn;
    private TableColumn<Food, Number> quantityColumn;
    private TableColumn<Food, Number> subtotal;
    private TextField quantityTextField;
    private Button changeButton;
    private Button removeButton;
    private Button backMenuButton;
    private Button orderButton;
    private Label totalPlusTaxLebel;
    
    //global variables for Cart Page
    private Button myButton;
    private Label orderDetailsLabel;

    //data fields for Invoice Page & Menu Page
    private ObservableList<Food> foodData = FXCollections.observableArrayList();
    private ObservableList<Food> foodSelected = FXCollections.observableArrayList();
    private Order orderList;

    //global variables for Welcome Page
    private Label customerLabel;//also in the Cart Page
    private Button newOrderButton;
    
    /**
     * This start method override the start method in the Application class
     * @param stage A stage object(window) that is automatically created
     * @throws Exception 
     * @author XiaoyuLiang
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        setLoginPage();
        
        stage.setScene(scene);
        stage.setTitle("Tap & take");
        stage.setResizable(false);
        stage.show();
    }
    
     /**
     * This method will set the login page of the application
     * @author Hamnah Atif 
     * Last Modified: XiaoyuLiang
     */
    private void setLoginPage() {

        mainPane = new BorderPane();
        mainPane.setId("main-pane");
        mainPane.getStylesheets().add("/tapandtake/Style.css");

        mainPane.setTop(getTitle("Tap & Take"));

        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10, 10, 10, 40));

        //creating file to save user information
        File file = new File("data/users.txt");

        //adding user information into the file using comma as delimiter
        if (file.exists()) {
            try (Scanner fileInput = new Scanner(file)) {
                while (fileInput.hasNextLine()) {
                    String lineIn = fileInput.nextLine();
                    String[] lineInSplit = lineIn.split(",");
                    long phoneNum = Long.parseLong(lineInSplit[0]);
                    String name = lineInSplit[1];
                    String password = lineInSplit[2];
                    Customer cust = new Customer(phoneNum, name, password);
                    customers.add(cust);
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.toString());
            }
        } else {
            System.out.println("File doesn't exist!");
        }

        //creating a GUI layout for login page
        VBox vBox = new VBox(10);
        vBox.setId("fontsize20");
        vBox.setPadding(new Insets(50));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(new Label("Phone Number (Id)"), tfPhoneNum, 
                new Label("Password"), tfPassword);
        
        //setting fixed text length of tfPhoneNum textfield
        tfPhoneNum.lengthProperty().addListener(
                (ObservableValue<? extends Number> observable, 
                        Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                //checking if the new character is greater than PHONE_NUM_LENGTH
                if (tfPhoneNum.getText().length() >= PHONE_NUM_LENGTH) {
                    tfPhoneNum.setText(tfPhoneNum.getText().substring(
                            0, PHONE_NUM_LENGTH));
                }
            }
        });
        
        mainPane.setCenter(vBox);
        
        Button btnLogin = new Button("Login");
        btnLogin.setId("green-button");
        Button btnSignUp = new Button("Sign Up");
        btnSignUp.setId("green-button");
        
        //button to login
        btnLogin.setOnAction(e -> {

            //validating phone number using regular expression
            if (tfPhoneNum.getText().matches("[\\d]{10}")) {
                for (int i = 0; i < customers.size(); i++) {
                    long phoneNum = customers.get(i).getPhoneNum();
                    String password = customers.get(i).getPassword();
                    
                    //checking if user login is successful
                    if (Long.parseLong(tfPhoneNum.getText()) == phoneNum
                            && tfPassword.getText().equals(password)) {
                        customerId = customers.get(i).getPhoneNum();
                        customerName = customers.get(i).getName();
                        System.out.println("Welcome!" + customerName + " You id is: " + customerId);
                        setAccountPage();
                        break;
                    }

                    //checking if user id and password match
                    if (i == customers.size() - 1 && Long.parseLong(
                            tfPhoneNum.getText())
                            != phoneNum && !tfPassword.equals(password)) {
                        JOptionPane.showMessageDialog(
                                null, "Incorrect user id or password");
                        tfPhoneNum.setText("");
                        tfPassword.setText("");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect user id or password");
            }
        });
        
        //button to go to signup page
        btnSignUp.setOnAction(e -> {setSignUpPage();});
        
        VBox buttonPane = new VBox(2);
        buttonPane.getChildren().addAll(btnLogin, btnSignUp);
        mainPane.setBottom(buttonPane);

        scene = new Scene(mainPane, 375, 667);
        stage.setScene(scene);
    }
    
    /**
     * This method will create an account for user in this application
     * @author Hamnah Atif 
     * Last Modified: Hamnah Atif 
     */
    private void setSignUpPage() {
        mainPane = new BorderPane();
        mainPane.setId("main-pane");
        mainPane.getStylesheets().add("/tapandtake/Style.css");

        mainPane.setTop(getTitle("Create an Account"));

        //creating labels and text fields for signup page GUI layout
        VBox vBox = new VBox(10);
        vBox.setId("fontsize20");
        vBox.setPadding(new Insets(50));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(new Label("Phone Number (Id)"), tfPhoneNum, 
                new Label("Customer Name"), tfUsername, 
                new Label("Password"), tfPassword);
        
        //setting fixed text length of tfPhoneNum textfield
        tfPhoneNum.lengthProperty().addListener(
                (ObservableValue<? extends Number> observable, 
                        Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                // Check if the new character is greater than PHONE_NUM_LENGTH
                if (tfPhoneNum.getText().length() >= PHONE_NUM_LENGTH) {
                    tfPhoneNum.setText(tfPhoneNum.getText().substring(
                            0, PHONE_NUM_LENGTH));
                }
            }
        });
        mainPane.setCenter(vBox);
        
        //creating buttons to sign up page
        Button btnCreate = new Button("Create");
        btnCreate.setId("green-button");
        Button btnBack = new Button("Back");
        btnBack.setId("green-button");
        
        //creating action for Create button
        btnCreate.setOnAction(e -> {
            if (tfPhoneNum.getText().matches("[\\d]{10}")) {
                for (int i = 0; i < customers.size(); i++) {
                    long phoneNum = customers.get(i).getPhoneNum();

                    //checking for phoneNum whether it exist in users file or not
                    if (Long.parseLong(tfPhoneNum.getText()) == phoneNum) {
                        JOptionPane.showMessageDialog(null, "Phone Number already exist");
                        tfPhoneNum.setText("");
                        tfPassword.setText("");
                        tfUsername.setText("");
                        break;
                    }

                    //writing and saving user's input in the file
                    if (i == customers.size() - 1 && Long.parseLong(
                            tfPhoneNum.getText()) != phoneNum) {
                        Customer cust = new Customer(Long.parseLong(
                                tfPhoneNum.getText()),
                                tfUsername.getText(), tfPassword.getText());
                        customers.add(cust);
                        try (PrintWriter pw = new PrintWriter(new FileWriter(
                                "data/users.txt", true))) {
                            pw.print(tfPhoneNum.getText() + "," + tfUsername.getText()
                                    + "," + tfPassword.getText() + "\n");
                        } catch (IOException ex) {
                            System.out.println(ex.toString());
                        }
                        setLoginPage();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Phone number is incorrect!");
            }
        });

        //creating action for back button
        btnBack.setOnAction(e -> {setLoginPage();});
        
        VBox buttonPane = new VBox(2);
        buttonPane.getChildren().addAll(btnCreate, btnBack);
        mainPane.setBottom(buttonPane);
        
        scene = new Scene(mainPane, 375, 667);
        stage.setScene(scene);
    }
    
    /**
     * This method will be the welcome page of the application after user successfully login
     * @author Hamnah Atif & Liang Xiaoyu
     * Last Modified: Hamnah Atif
     */
    private void setAccountPage() {
        mainPane = new BorderPane();
        mainPane.setId("main-pane");
        mainPane.getStylesheets().add("/tapandtake/Style.css");

        mainPane.setTop(getTitle("My account"));

        //display message with user's name and id
        customerLabel = new Label("Welcome " + customerName + "!\nID:" + customerId);
        customerLabel.setTextAlignment(TextAlignment.CENTER);
        customerLabel.setId("pink-label");

        //creating New Order button to direct user to the Menu Page
        newOrderButton = new Button("New order");
        newOrderButton.setOnAction(e -> setMenuPage());
        newOrderButton.setId("pink-button2");
        
        Button exitButton = new Button("Log Out");
        exitButton.setId("pink-button2");
        exitButton.setOnAction(e -> {System.exit(0);});
        
        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(customerLabel, newOrderButton, exitButton);
        vBox.setAlignment(Pos.CENTER);

        mainPane.setCenter(vBox);

        scene = new Scene(mainPane, 375, 667);
        stage.setScene(scene);
    }
    
    /**
     * This method will set the Arraylist of menu on the screen
     * @author XiaoyuLiang
     * Last Modified: XiaoyuLiang
     */
    private void setMenuPage() {

        foodData.clear();
        loadFood();

        //creating GUI layout for menu page
        mainPane = new BorderPane();
        mainPane.setId("main-pane");
        mainPane.getStylesheets().add("/tapandtake/Style.css");

        mainPane.setTop(getTitle("Menu"));
        mainPane.setLeft(getListView());
        mainPane.setCenter(getItemPane());
        mainPane.setBottom(getBasketButton());

        scene = new Scene(mainPane, 375, 667);
        stage.setScene(scene);
    }

    /**
     * This method will add the food on the Cart Page
     * @return Button "Basket" button show on the menu page
     * @author XiaoyuLiang
     */
    private Button getBasketButton() {
        basketButton = new Button("Basket");
        basketButton.setId("green-button");
        basketButton.setOnAction((ActionEvent e) -> {
            if (foodSelected.isEmpty()) {
                System.out.println("No food added");
            } else {
                orderList = new Order(foodSelected);
                setCartPage();
            }
        });
        return basketButton;
    }

    /**
     * This method will set the items and pictures of the food arraylist on the screen
     * @return VBox show on the menu page
     * @author XiaoyuLiang
     */
    private VBox getItemPane() {
        
        //setting food images on menu page
        imageFood = new ImageView(new Image("file:images/" + 
                foodData.get(0).getFoodName() + ".png"));
        imageFood.setFitWidth(215);
        imageFood.setFitHeight(110);
        
        //setting prices of food on menu page
        foodNameLabel = new Label(foodData.get(0).getFoodName());
        foodNameLabel.setId("fontsize20");
        unitPriceLabel = new Label("C$" + Double.toString(
                foodData.get(0).getUnitPrice()));
        unitPriceLabel.setId("pink-label");
        
        //creating button to add food into basket
        addButton = new Button("+");
        addButton.setId("add-button");
        addButton.setOnAction(e -> handleAddFood(foodData.get(0)));

        VBox itemPane = new VBox(10);
        itemPane.setAlignment(Pos.CENTER);
        itemPane.getChildren().addAll(
                imageFood, foodNameLabel, unitPriceLabel, addButton);
        return itemPane;
    }

    /**
     * This method will set the food listview
     * @return ListView foodListView show on the menu page
     * @author XiaoyuLiang
     */
    private ListView getListView() {

        foodListView = new ListView<>();

        foodListView.setItems(foodData);
        foodListView.getSelectionModel().selectFirst();
        foodListView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends Food> ov, Food oldValue, Food newValue) -> {
            if (newValue != null) {
                imageFood.setImage(new Image("file:images/" + newValue.getFoodName() + ".png"));
                foodNameLabel.setText(newValue.getFoodName());
                unitPriceLabel.setText("C$" + Double.toString(newValue.getUnitPrice()));
                addButton.setOnAction(e -> handleAddFood(newValue));
            }
        });
        return foodListView;
    }
    
    /**
     * This method will set the order details on invoice page
     * @author XiaoyuLiang
     */
    private void setCartPage() {
        mainPane = new BorderPane();
        mainPane.setId("main-pane");
        mainPane.getStylesheets().add("/tapandtake/Style.css");

        mainPane.setTop(getTitle("My Basket"));
        mainPane.setCenter(getCartPane());
        mainPane.setBottom(getButtonsPane());

        scene = new Scene(mainPane, 375, 667);
        stage.setScene(scene);
    }
    
    /**
     * This method will set the buttons on Cart Page
     * @return VBox pane show on the menu page
     * @author XiaoyuLiang
     */
    private VBox getButtonsPane() {
        backMenuButton = new Button("Back");
        backMenuButton.setId("green-button");
        backMenuButton.setOnAction(e -> {
            setMenuPage();
        });
        orderButton = new Button("Order");
        orderButton.setId("green-button");
        orderButton.setOnAction(e -> handleOrder());
        VBox pane = new VBox(2);
        pane.getChildren().addAll(backMenuButton, orderButton);
        return pane;
    }
    
    /**
     * This method will get the information for Cart Page
     * @return VBox pane show on the menu page
     * @author XiaoyuLiang 
     * Last Modified: Hamnah Atif
     */
    private VBox getCartPane() {

        lblCustomerName = new Label("Customer Name: " + customerName);
        lblPhoneNo = new Label("Phone No." + customerId);
        VBox customerPane = new VBox(10);
        customerPane.getChildren().addAll(lblCustomerName, lblPhoneNo);

        foodTable = new TableView<>();
        foodTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        foodTable.setItems(foodSelected);
        foodTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
            if (newValue != null) {
                quantityTextField.setEditable(true);
                quantityTextField.setText(Integer.toString(newValue.getQuantity()));
                changeButton.setOnAction(e -> handleChangeQuantity(newValue));
                removeButton.setOnAction(e -> handleRemoveFood(newValue));
            }
        });

        foodNameColumn = new TableColumn<>("Food Name");
        foodNameColumn.setMinWidth(70);
        foodNameColumn.setCellValueFactory(cellData -> cellData.getValue().foodNameProperty());
        unitPriceColumn = new TableColumn<>("Unit Price");
        unitPriceColumn.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty());
        quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        subtotal = new TableColumn<>("Subtotal");
        subtotal.setCellValueFactory(cellData -> cellData.getValue().subotalProperty());
        foodTable.getColumns().addAll(foodNameColumn, unitPriceColumn, quantityColumn, subtotal);

        quantityTextField = new TextField();
        quantityTextField.setEditable(false);
        quantityTextField.setPrefWidth(100);
        quantityTextField.setPromptText("Quantity");
        changeButton = new Button("Change");
        changeButton.setId("pink-button");
        removeButton = new Button("Remove");
        removeButton.setId("pink-button");
        totalPlusTaxLebel = new Label();
        totalPlusTaxLebel.setText("Total: C$" + orderList.getStringTotal() + 
                " + Tax -> C$" + orderList.getStringTotalPlusTax());
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(quantityTextField, changeButton, removeButton);

        VBox cartPane = new VBox(10);
        cartPane.setId("padding");
        cartPane.getChildren().addAll(customerPane, foodTable, hBox, totalPlusTaxLebel);

        return cartPane;
    }
    
    /**
     * This method will be the Invoice page of the application after user placed
     * an order
     * @author XiaoyuLiang
     */
    private void setInvoicePage() {
        mainPane = new BorderPane();
        mainPane.setId("main-pane");
        mainPane.getStylesheets().add("/tapandtake/Style.css");

        mainPane.setTop(getTitle("Order Information"));
        mainPane.setCenter(setOrderDetailPane());

        myButton = new Button("My account");
        myButton.setId("green-button");
        myButton.setOnAction(e -> {
            setAccountPage();
            foodSelected.clear();
        });
        Button exitButton = new Button("Log Out");
        exitButton.setId("green-button");
        exitButton.setOnAction(e -> {System.exit(0);});
        VBox buttonsPane = new VBox(2);
        buttonsPane.getChildren().addAll(myButton, exitButton);
        
        mainPane.setBottom(buttonsPane);
        scene = new Scene(mainPane, 375, 667);
        stage.setScene(scene);
    }
    
    /**
     * This method will set the order details on invoice page
     * @return GridPane gridPane show on Order Information page
     * @author XiaoyuLiang
     */
    private GridPane setOrderDetailPane() {
        //Food ordered details
        customerLabel = new Label("Customer Name: " + customerName + 
                "\nCustomer ID: " + customerId);
        orderDetailsLabel = new Label("Order Number: " + 
                DateUtil.formatOrderNumber(orderList.getDate()) + 
                "\nOrder Time: " + DateUtil.formatDate(orderList.getDate()));
        Label orderTotalLabel = new Label("Total Price: C$" + 
                orderList.getStringTotalPlusTax());
        
        GridPane gridPane = new GridPane();
        gridPane.setId("padding");
        gridPane.setVgap(10);
        gridPane.addRow(0, customerLabel);
        GridPane.setColumnSpan(customerLabel, 3);
        gridPane.addRow(1, orderDetailsLabel);
        GridPane.setColumnSpan(orderDetailsLabel, 3);
        gridPane.addRow(2, orderTotalLabel);
        GridPane.setColumnSpan(orderTotalLabel, 3);

        for(int i = 0; i < foodSelected.size(); i++){
            gridPane.add(new Label(String.format(
                    "%-20s",foodSelected.get(i).getFoodName())), 0, i + 3);
            gridPane.add(new Label(String.format(
                    "%6.2f",foodSelected.get(i).getUnitPrice())), 1, i + 3);
            gridPane.add(new Label(String.format(
                    "%8d",foodSelected.get(i).getQuantity())), 2, i + 3);
            gridPane.add(new Label(String.format(
                    "%12.2f",foodSelected.get(i).getSubotal())), 3, i + 3);
        }
        return gridPane;
    }
    
    /**
     *This method will create a Label to show the theme of each page
     * @param string top word of each pane
     * @return Label 
     * @author XiaoyuLiang
     */
    private Label getTitle(String string) {
        Label titleLabel = new Label(string);
        titleLabel.setId("title-label");
        return titleLabel;
    }

    /**
     * This method will load the food
     * @author XiaoyuLiang
     */
    private void loadFood() {
        File file = new File("data/Food.txt");
        if (file.exists()) {
            try (Scanner fileInput = new Scanner(file)) {
                while (fileInput.hasNextLine()) {
                    String lineIn = fileInput.nextLine();
                    String[] fields = lineIn.split(",");
                    Food food = new Food(fields[0], Double.parseDouble(fields[1]));
                    foodData.add(food);
                }
            } catch (Exception ex) {
                System.out.println(ex.toString());
                System.out.println("loadFood wrong");
            }
        } else {
            System.out.println("File does not exists");
        }
    }
    
    /**
     * This method will add and handle the food
     * @param food 
     * @author XiaoyuLiang
     */
    private void handleAddFood(Food food) {
        Boolean isSelected = false;

        if (foodSelected.isEmpty()) {
            foodSelected.add(food);
            alert.setHeaderText("First food " + food.getFoodName());
            alert.showAndWait();
            System.out.println("First food " + food.getFoodName());
        } else {
            for (int i = 0; i < foodSelected.size(); i++) {
                Food foodInCart = foodSelected.get(i);
                if (foodInCart.getFoodName().equals(food.getFoodName())) {
                    isSelected = true;
                }
            }
            if (isSelected) {
                alert.setHeaderText(food.getFoodName() + " already exist");
                alert.showAndWait();
                System.out.println(food.getFoodName() + " already exist");
            } else {
                alert.setHeaderText(food.getFoodName() + " added successful");
                alert.showAndWait();
                System.out.println(food.getFoodName() + " added successful");
                foodSelected.add(food);
            }
        }
    }

    /**
     * This method will handle the user input number for qty on Cart Page
     * @param Food
     * @author XiaoyuLiang
     */
    private void handleChangeQuantity(Food food) {
        if (foodSelected.isEmpty()) {
            System.out.println("Change: the cart is empty cannot be change");
        } else {
            try {
                if (1000 > Integer.parseInt(quantityTextField.getText()) && 
                        Integer.parseInt(quantityTextField.getText()) > 0) {
                    food.setQuantity(Integer.parseInt(quantityTextField.getText()));
                    foodTable.refresh();
                    totalPlusTaxLebel.setText("Total: C$" + orderList.getStringTotal() + 
                            " + Tax -> C$" + orderList.getStringTotalPlusTax());
                    System.out.println(food.getFoodName() + " changed to " + food.getQuantity());
                } else {
                    System.out.println("Limited 1000");
                }
            } catch (NumberFormatException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    /**
     * This method will remove the selected food on Cart Page
     * @param Food
     * @author XiaoyuLiang
     */
    private void handleRemoveFood(Food food) {
        if (foodSelected.isEmpty()) {
            quantityTextField.setEditable(false);
            changeButton.setDisable(true);
            System.out.println("Empty cart cannot be remove");
        } else {
            foodTable.getItems().remove(foodTable.getSelectionModel().getSelectedIndex());
            quantityTextField.clear();
            totalPlusTaxLebel.setText("Total: C$" + orderList.getStringTotalPlusTax());
            System.out.println(food.getFoodName() + " remove!");
        }
    }
    
    /**
     * This method will handle the food order on Cart Page
     * @author XiaoyuLiang
     */
    private void handleOrder() {
        if (foodSelected.isEmpty()) {
            System.out.println("No food in cart");
        } else {
            orderList.setDate(new Date());
            setInvoicePage();
            //setPage();

            File file = new File("data/" + DateUtil.formatOrderNumber(orderList.getDate()) + ".txt");
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("Customer Name: " + customerName);
                writer.println("Customer ID: " + customerId);
                writer.println("Order Number: " + DateUtil.formatOrderNumber(orderList.getDate()));
                writer.println("Order Time: " + DateUtil.formatDate(orderList.getDate()));
                for (int i = 0; i < foodSelected.size(); i++) {
                    writer.println(foodSelected.get(i).toFood());
                }
                writer.println("Total Price: C$" + orderList.getStringTotalPlusTax());
            } catch (FileNotFoundException ex) {
                System.out.println(ex.toString());
            }
            System.out.println("Place an order!");
            // for test
            System.out.println(DateUtil.formatDate(orderList.getDate()));
            System.out.println(DateUtil.formatOrderNumber(orderList.getDate()));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
