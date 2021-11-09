package com.uriegas;

import java.net.URL;
import javafx.fxml.*;
import java.util.*;
import com.uriegas.Model.*;
import javafx.scene.control.*;
import javafx.util.*;
import javafx.util.converter.*;

public class EmployeeDialogController implements Initializable {
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField password;

    public void setEmployee(Employee other) {
        name.textProperty().bindBidirectional(other.nameProperty());
        password.textProperty().bindBidirectional(other.descriptionProperty());
        StringConverter<Number> stringConverter = new NumberStringConverter();
        id.textProperty().bindBidirectional(other.idProperty(), stringConverter);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
