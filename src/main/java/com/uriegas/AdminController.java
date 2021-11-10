package com.uriegas;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.*;
import com.uriegas.Model.*;
import javafx.collections.transformation.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
/**
 * Controller for the main window
 */
public class AdminController implements Initializable {
    DataModel model;

    // ==> Searchbars
    @FXML private TextField searchProduct;
    @FXML private TextField searchEmployee;
    @FXML private Button clearSearchProduct;
    @FXML private Button clearSearchEmployee;
    // <== Searchbars

    // ==> CRUD buttons
    @FXML private Button addProduct;
    @FXML private Button addEmployee;
    @FXML private Button updateProduct;
    @FXML private Button updateEmployee;
    @FXML private Button deleteProduct;
    @FXML private Button deleteEmployee;
    // <== CRUD buttons

    // ==> TableColumns
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productId;
    @FXML private TableColumn<Product, String> productName;
    @FXML private TableColumn<Product, String> productDescription;
    @FXML private TableColumn<Product, Double> productPrice;
    @FXML private TableColumn<Product, Integer> productQuantity;

    @FXML private TableView<Searchable> employeeTable;
    @FXML private TableColumn<Searchable, Integer> employeeId;
    @FXML private TableColumn<Searchable, String> employeeName;
    @FXML private TableColumn<Searchable, String> employeePassword;
    // <== TableColumns

    private FilteredList<Product> filteredProductList;
    private FilteredList<Searchable> filteredEmployeeList;

    /**
     * Get the model
     * @param model the data model
     */
    public void initModel(DataModel model) {    
        if(this.model != null)
            throw new NullPointerException("Model is already initialized, can only initialize it once");
        else
            this.model = model;
        // ==> Data binding
        try{//Get products and employees from DB
            this.filteredProductList = new FilteredList<>(this.model.getProducts());
            this.filteredEmployeeList = new FilteredList<>(this.model.getEmployees());
        }catch(Exception e){
            System.out.println(DataModel.ERROR + e);
        }
        productTable.setItems(filteredProductList);
        searchProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProductList.setPredicate(createPredicate(newValue));
        });
        employeeTable.setItems(filteredEmployeeList);
        searchEmployee.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEmployeeList.setPredicate(createPredicate(newValue));
        });
        // <== Data binding
    }
    /**
     * Constructor
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ==> Product Table and CRUD buttons
        this.productName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.productDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        this.productId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        this.productPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        this.productQuantity.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());

        addProduct.setOnAction(event -> {
            addProductDialog(new Product());
        });
        updateProduct.setOnAction(event -> {
            updateProductDialog((Product)productTable.getSelectionModel().getSelectedItem());
        });
        deleteProduct.setOnAction(event -> {
            deleteProductDialog((Product)productTable.getSelectionModel().getSelectedItem());
        });
        // <== Product Table and CRUD buttons

        // ==> Employee Table and CRUD buttons
        this.employeeId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        this.employeeName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.employeePassword.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        addEmployee.setOnAction(event -> {
            addEmployeeDialog(new Employee());
        });
        updateEmployee.setOnAction(event -> {
            updateEmployeeDialog((Employee)employeeTable.getSelectionModel().getSelectedItem());
        });
        deleteEmployee.setOnAction(event -> {
            deleteEmployeeDialog((Employee)employeeTable.getSelectionModel().getSelectedItem());
        });
        // <== Employee Table and CRUD buttons
    }
    /**
     * Dialog to delete a product
     * @param p selected product
     */
    private void deleteProductDialog(Product product) {
        if(product != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete product");
            alert.setHeaderText("Delete product");
            alert.setContentText("Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try{
                    model.deleteProduct(product);
                }catch(Exception e){
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Error");
                    alert2.setHeaderText("An error ocurred");
                    alert2.setContentText("Could not delete this product");
                    alert2.showAndWait();
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error ocurred");
            alert.setContentText("You haven't select any product yet");
            alert.showAndWait();
        }
    }
    /**
     * Dialog to add a product
     * @param p an empty product
     */
    private void addProductDialog(Product p) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProductDialog.fxml"));
            Parent root = loader.load();
            ProductDialogController controller = loader.getController();
            controller.setProduct(p);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add/modify product");
            dialog.setDialogPane((DialogPane)root);
            dialog.showAndWait().ifPresent(button -> {
                if(button.equals(ButtonType.OK)){//Save product to database
                    System.out.println("INFO " + p.toString());
                    try{
                        this.model.addProduct(p);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Product added");
                        alert.setContentText("The product will appear in the list the next time you start the app");
                        alert.showAndWait();
                        
                    }catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Couldn't add this product to the DB");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        }catch(Exception e){//Show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Couldn't perform this action.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Dialog to update a product
     * @param p the selected product
     */
    private void updateProductDialog(Product p){
        try{
            final Product copy = p.clone();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProductDialog.fxml"));
            Parent root = loader.load();
            ProductDialogController controller = loader.getController();
            controller.setProduct(p);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Update product");
            dialog.setDialogPane((DialogPane)root);
            dialog.showAndWait().ifPresent(button -> {
                if(button.equals(ButtonType.CANCEL)){
                    p.setName(copy.getName());
                    p.setDescription(copy.getDescription());
                    p.setId(copy.getId());
                    p.setStock(copy.getStock());
                    p.setPrice(copy.getPrice());
                    System.out.println("Cancel");
                }
                else if(button.equals(ButtonType.OK)){
                    System.out.println("INFO " + p.toString());
                    try{
                        this.model.updateProduct(p);
                    }catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Couldn't update this product");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        }catch(Exception e){//Show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Make your that you have selected a product");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void addEmployeeDialog(Employee e){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EmployeeDialog.fxml"));
            Parent root = loader.load();
            EmployeeDialogController controller = loader.getController();
            controller.setEmployee(e);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add/modify employee");
            dialog.setDialogPane((DialogPane)root);
            dialog.showAndWait().ifPresent(button -> {
                if(button.equals(ButtonType.OK)){//Save employee to database
                    System.out.println("INFO " + e.toString());
                    try{
                        this.model.addEmployee(e);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Employee added");
                        alert.setContentText("The employee will appear in the list the next time you start the app");
                        alert.showAndWait();
                        
                    }catch(Exception ex){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Couldn't add this employee to the DB");
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        }catch(Exception exception){//Show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Couldn't perform this action.");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    private void updateEmployeeDialog(Employee e){
        try{
            final Employee copy = e.clone();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EmployeeDialog.fxml"));
            Parent root = loader.load();
            EmployeeDialogController controller = loader.getController();
            controller.setEmployee(e);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Update employee");
            dialog.setDialogPane((DialogPane)root);
            dialog.showAndWait().ifPresent(button -> {
                if(button.equals(ButtonType.CANCEL)){
                    e.setName(copy.getName());
                    e.setId(copy.getId());
                    e.setDescription(copy.getDescription());
                    System.out.println("Cancel");
                }
                else if(button.equals(ButtonType.OK)){
                    System.out.println("INFO " + e.toString());
                    try{
                        this.model.updateEmployee(e);
                    }catch(Exception ex){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Couldn't update this employee");
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        }catch(Exception exception){//Show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Make your that you have selected a employee");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    private void deleteEmployeeDialog(Employee e){
        if(e == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Make your that you have selected a employee");
            alert.setContentText("");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete employee");
            alert.setHeaderText("Are you sure you want to delete this employee?");
            alert.setContentText("This action cannot be undone");
            alert.showAndWait().ifPresent(button -> {
                if(button.equals(ButtonType.OK)){
                    try{
                        this.model.deleteEmployee(e);
                    }catch(Exception ex){
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText("Couldn't delete this employee");
                        alert2.setContentText(ex.getMessage());
                        alert2.showAndWait();
                    }
                }
            });
        }
    }


    private boolean searchFind(Searchable s, String search){
        return String.valueOf(s.getId()).contains(search) || s.getName().toString().toLowerCase().contains(search.toLowerCase()) || s.getDescription().toString().toLowerCase().contains(search.toLowerCase());
    }
    private Predicate<Searchable> createPredicate(String searchText){
        return searchable -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFind(searchable, searchText);
        };
    }
}