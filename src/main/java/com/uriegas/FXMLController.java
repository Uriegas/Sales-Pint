package com.uriegas;

import java.net.URL;
import java.util.ResourceBundle;
import com.uriegas.Model.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class FXMLController implements Initializable {
    DataModel model;

    @FXML private TextField searchbar;
    @FXML private Button search_btn;
    @FXML private Button clear_btn;
    @FXML private Button settings_btn;
    @FXML private TableView<Searchable> searchableproducts;
    @FXML private TableColumn<Searchable, String> productname;
    @FXML private TableColumn<Searchable, String> productdescription;
    @FXML private TableColumn<Searchable, Double> productprice;
    @FXML private TableView<Product> sales;
    @FXML private TableColumn<Product, String> salesproductname;
    @FXML private TableColumn<Product, Integer> salesproductquantity;
    @FXML private TableColumn<Product, Double> salesproductprice;
    @FXML private Button commitsale;
    @FXML private Label totalprice;

    public void initModel(DataModel model) {
        if(this.model != null)
            throw new NullPointerException("Model is already initialized, can only initialize it once");
        else
            this.model = model;
        // ==> Data binding
        // totalprice.textProperty().bindBidirectional(model.sProperty());
        // <== Data binding
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
