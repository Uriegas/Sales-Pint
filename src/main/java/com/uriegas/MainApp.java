package com.uriegas;

import javafx.application.*;
import com.uriegas.Model.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.*;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin.fxml"));
        Parent root = loader.load();

        // ==> Load model
        AdminController controller = loader.getController();
        controller.initModel(new DataModel());
        // <== Load model

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("Caja registradora");
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/styles/logo.png")));
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("You are about to exit the application.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK)
                    System.exit(0);
                else
                    e.consume();
            });
        });
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
