package com.uriegas;

import java.io.*;
import java.net.URL;
import java.util.*;
import com.uriegas.Model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.*;

public class LoginController implements Initializable {
    @FXML private TextField username;
    @FXML private PasswordField password;
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
            // ==> Not valid validation, should request accounts from DB
            if(username.getText().equals("admin") && password.getText().equals("admin")) {
                System.out.println( DataModel.DEBUG + "Login as Admin");
                // Change to Admin view
                Stage switchscene = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try{
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(this.getClass().getResource("/fxml/Admin.fxml"));
                    Parent root = loader.load();
                    AdminController controller = loader.getController();
                    controller.initModel(new DataModel());
                    Scene scene = new Scene(root);
                    switchscene.setScene(scene);
                }catch(IOException ex){System.out.println(DataModel.ERROR + ex.getMessage());}
            } else if (username.getText().length() > 0 && password.getText().length() > 0) {
                System.out.println( DataModel.DEBUG + "Login as Cobrador");
                // Change to Casher view
                Stage switchscene = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try{
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(this.getClass().getResource("/fxml/Cashier.fxml"));
                    Parent root = loader.load();
                    CashierController controller = loader.getController();
                    controller.initModel(new DataModel());
                    Scene scene = new Scene(root);
                    switchscene.setScene(scene);
                }catch(IOException ex){System.out.println(DataModel.ERROR + ex.getMessage());}
            } else {
                System.out.println( DataModel.DEBUG + "Login Failed");
            }
            // <== Not valid validation, should request accounts from DB
    }
}
