package com.uriegas;

import java.net.URL;
import java.text.*;
import javafx.fxml.*;
import java.util.ResourceBundle;
import com.uriegas.Model.Product;
import javafx.scene.control.*;
import javafx.util.*;
import javafx.util.converter.*;

public class ProductDialogController implements Initializable {
    @FXML private TextField name;
    @FXML private TextField description;
    @FXML private TextField price;
    private Product product;

    private void setProduct(Product other) {
        this.product = other;

        name.textProperty().bindBidirectional(other.nameProperty());
        description.textProperty().bindBidirectional(other.descriptionProperty());
        StringConverter<Number> stringConverter = new NumberStringConverter();
        price.textProperty().bindBidirectional(other.priceProperty(), stringConverter);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ==> Parse only 2 decimal numbers
        DecimalFormat format = new DecimalFormat( "#.0" );
        price.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty())
                return c;
            ParsePosition parsePosition = new ParsePosition( 0 );
            Object object = format.parse( c.getControlNewText(), parsePosition );
            if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
                return null;
            else
                return c;
        }));
        // <== Parse only 2 decimal numbers
    }
}
