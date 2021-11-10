package com.uriegas;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.*;
import com.uriegas.Model.*;
import javafx.collections.transformation.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.*;
import javafx.application.Application;
import javafx.beans.property.*;
/**
 * Controller for the main window
 */
public class CashierController implements Initializable {
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
    @FXML private Button devolution;
    @FXML private Label totalprice;
    DoubleProperty totalpriceProperty = new SimpleDoubleProperty(0.00);
    private FilteredList<Searchable> filteredList;

    /**
     * Get the model
     */
    public void initModel(DataModel model) {    
        if(this.model != null)
            throw new NullPointerException("Model is already initialized, can only initialize it once");
        else
            this.model = model;
        // ==> Data binding
        this.filteredList = new FilteredList<>(this.model.getSearchables());
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
            new Application() {
            @Override
            public void start(Stage stage) {
            }
            }.getHostServices().showDocument("https://github.com/uriegas/");
            event.consume();
        });
        Callback<TableColumn<Searchable, Void>, TableCell<Searchable, Void>> cellFactory = new Callback<TableColumn<Searchable, Void>, TableCell<Searchable, Void>>() {
            @Override
            public TableCell<Searchable, Void> call(TableColumn<Searchable, Void> param) {
                return new TableCell<Searchable, Void>() {
                    final Button btn = new Button("Agregar");
                    //Add style to the button
                    {
                        btn.getStyleClass().add("button-add");
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

        commitsale.setOnAction(event -> {
            try{
                this.model.makeSale();
                //Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sale");
                alert.setHeaderText("Sale successful");
                alert.setContentText("The sale was successful");
                alert.showAndWait();
            }catch(Exception e){//Show alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.getClass().getName());
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
        clear_btn.setOnAction(event -> {
            searchbar.clear();
        });
        devolution.setOnAction(event ->{
            try{
                this.model.makeDevolution();
                //Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Devolution");
                alert.setHeaderText("Devolution successful");
                alert.setContentText("The devolution was successful");
                alert.showAndWait();
            }catch(Exception e){//Show alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.getClass().getName());
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
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
