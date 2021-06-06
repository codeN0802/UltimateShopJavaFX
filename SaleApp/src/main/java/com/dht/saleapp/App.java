package com.dht.saleapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
       
        scene = new Scene(loadFXML("login1"));
        stage.getIcons().add(new Image("file:/C:/Users/HUU%20NGHI/Desktop/it81demo/SaleApp/src/main/java/com/dht/images/icons8_gas_52px_4.png"));
        stage.setTitle("Ultimate");
        stage.setResizable(false);
        //stage.initStyle(StageStyle.);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}