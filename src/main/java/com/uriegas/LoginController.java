package com.uriegas;

import java.net.URL;
import java.util.*;
import com.uriegas.Model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

public class LoginController implements Initializable {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button button;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                password.requestFocus();
            }
        });
        password.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tryLogin(event);
            }
        });
        button.setOnAction(event -> {
            tryLogin(event);
        });
    }
    public void tryLogin(Event event) {
            System.out.println( DataModel.DEBUG + "Username: " + username.getText() + " Password: " + password.getText());
            if(username.getText().equals("admin") && password.getText().equals("admin")) {
                System.out.println( DataModel.DEBUG + "Login as Admin");
            } else if (username.getText().equals("cobrador") || password.getText().equals("cobrador")) {
                System.out.println( DataModel.DEBUG + "Login as Cobrador");
            } else {
                System.out.println( DataModel.DEBUG + "Login Failed");
            }
            // if (username.getText().equals("admin") && password.getText().equals("admin")) {
            //     button.getScene().getWindow().hide();
            // } else {
            //     Alert alert = new Alert(Alert.AlertType.ERROR);
            //     alert.setTitle("Error");
            // }
    }
}
