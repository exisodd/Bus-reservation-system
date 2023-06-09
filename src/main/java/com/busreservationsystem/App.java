package com.busreservationsystem;

import com.busreservationsystem.system.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author Ethan Tran
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/LoginForm.fxml"));
            scene = new Scene(fxmlLoader.load(), 1024, 576);
            stage.setTitle("Bus Reservation System");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> onClose());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        String filePath = String.format("views/%s.fxml", fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(filePath));
        scene.setRoot(fxmlLoader.load());
    }

    public static void onClose() {
        Database.writeJsons();
    }

    public static void main(String[] args) {
        new Database("clients", "admins", "bookings", "buses");
        Database.loadJsons();
        launch();
    }
}
