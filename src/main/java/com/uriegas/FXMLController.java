package com.uriegas;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.*;
import com.uriegas.Model.*;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.util.*;
import javafx.util.converter.DoubleStringConverter;
import javafx.beans.property.*;
/**
 * Controller for the main window
 */
public class FXMLController implements Initializable {
    DataModel model;

    @FXML private TextField searchbar;
    @FXML private Button search_btn;
    @FXML private Button clear_btn;
    @FXML private Button settings_btn;
    @FXML private TableView<Searchable> searchableproducts;
    @FXML private TableColumn<Searchable, String> productname;
    @FXML private TableColumn<Searchable, String> productdescription;
    @FXML private TableColumn<Searchable, Integer> productid;
    @FXML private TableColumn<Searchable, Void> carrito;
    @FXML private TableView<Product> sales;
    @FXML private TableColumn<Product, String> salesproductname;
    @FXML private TableColumn<Product, Integer> salesproductquantity;
    @FXML private TableColumn<Product, Double> salesproductprice;
    @FXML private Button commitsale;
    @FXML private Label totalprice;
    @FXML private Button addbtn;
    @FXML private Button updatebtn;
    @FXML private Button deletebtn;
    DoubleProperty totalpriceProperty = new SimpleDoubleProperty(0.00);
    /**
     * Get the model
     */
    public void initModel(DataModel model) {    
        if(this.model != null)
            throw new NullPointerException("Model is already initialized, can only initialize it once");
        else
            this.model = model;
        // ==> Data binding
        FilteredList<Searchable> filteredList = new FilteredList<>(this.model.getSearchables());
        searchableproducts.setItems(filteredList);
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(createPredicate(newValue));
        });
        // totalprice.textProperty().bind(this.model.getCart().forEach(p -> p.getPrice() * p.getStock() ).sum());
        this.sales.setItems(this.model.cartProperty());
        // this.searchableproducts.setItems(this.model.getSearchables());
        // totalprice.textProperty().bindBidirectional(model.sProperty());
        this.totalprice.textProperty().bind(this.model.totalpriceProperty().asString());
        // <== Data binding
        
    }
    /**
     * Constructor
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.salesproductname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.salesproductquantity.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        this.salesproductprice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        // StringConverter<? extends Number> converter = new DoubleStringConverter();

        this.productname.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.productdescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        this.productid.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        settings_btn.setOnAction(event -> {
            //Create dialog to choose between add a product or a offert
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Add/modify product", "Add/modify offer");
            dialog.setHeaderText("Settings");
            dialog.setContentText("Choose what you want to do");
            // dialog.getDialogPane().getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
            dialog.showAndWait().ifPresent(choice -> {
                if(choice.equals("Add/modify product"))//Create dialog to choose between add a product or a offert
                    addProductDialog();
                else if(choice.equals("Add/modify offer"))//Create dialog to choose between add a product or a offert
                    addOfferDialog();
            });
        });
        Callback<TableColumn<Searchable, Void>, TableCell<Searchable, Void>> cellFactory = new Callback<TableColumn<Searchable, Void>, TableCell<Searchable, Void>>() {
            @Override
            public TableCell<Searchable, Void> call(TableColumn<Searchable, Void> param) {
                return new TableCell<Searchable, Void>() {
                    final Button btn = new Button("Agregar");
                    {
                        btn.setOnAction(event -> {
                            // Searchable s = searchableproducts.getSelectionModel().getSelectedItem();
                            Searchable s = (Searchable) getTableView().getItems().get(getIndex());
                            System.out.println("INFO " + s.toString());
                            model.addToCart(s);
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else 
                            setGraphic(btn);
                    }
                };
            }
        };
        this.carrito.setCellFactory(cellFactory);

        addbtn.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProductDialog.fxml"));
            
        });
    }
    /**
     * Dialog to add a product
     */
    private void addProductDialog(){
        System.out.println("Add/modify product");
    }
    private void addOfferDialog(){
        System.out.println("Add/modify offer");
    }
    /**
     * Add the selected product or offer to the carrito
     */
    private void addProductToCart(Searchable s){
        System.out.println("Add product to cart");
        if(s instanceof Product){
            Product p = (Product) s;
            System.out.println("Product: " + p.toString());
            
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
