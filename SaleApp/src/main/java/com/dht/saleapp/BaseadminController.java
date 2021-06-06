/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class BaseadminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML Text txtname;
    @FXML
    private AnchorPane paneRight;
    private AnchorPane newRightPane = null;
    private JFXButton temp = null;
    private HashMap<String, String> FXML_URL = new HashMap<>();
    private static boolean anchorFlag = false;
    //private static String admim = "";

    private void autoResizePane() {
        newRightPane.setPrefWidth(paneRight.getWidth());
        newRightPane.setPrefHeight(paneRight.getHeight());
    }
    @FXML
    private void switchToLogin1() throws IOException {
         
        App.setRoot("login1");
     }   

    @FXML
    private void ctrlRightPane(String URL) throws IOException {
        try {
            paneRight.getChildren().clear(); //Removing previous nodes
            newRightPane = FXMLLoader.load(getClass().getResource(URL));

            newRightPane.setPrefHeight(paneRight.getHeight());
            newRightPane.setPrefWidth(paneRight.getWidth());

            paneRight.getChildren().add(newRightPane);

            //Listener to monitor any window size change
            paneRight.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
                // Some components of the scene will be resized automatically
                autoResizePane();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btnNavigators(ActionEvent event) {
        borderSelector(event); //Marking selected navigator button

        JFXButton btn = (JFXButton) event.getSource();

        // Getting navigation button label
        String btnText = btn.getText();

        // Checking which button is clicked from the map
        // and navigating to respective menu
        try {
            ctrlRightPane(FXML_URL.get(btnText));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void borderSelector(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();

        if (temp == null) {
            temp = btn; //Saving current button properties
        } else {
            temp.setStyle(""); //Resetting previous selected button properties
            temp = btn; //Saving current button properties
        }

        btn.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 60 0 0 60; ; -fx-text-fill:#2cb1b1;  -fx-font-weight: bold; -fx-font-size: 18px");
    }
     private void loadFXMLMap() {
        // Adding URLS in the FXML_URL ArrayList field
        // to avoid code redundancy in ctrlRightPane() method
        FXML_URL.put("Product Management", "primary.fxml");
        FXML_URL.put("Customer Management", "customermanagement.fxml");
         FXML_URL.put("Sell", "sell.fxml");
         FXML_URL.put("Order Management", "BillManagement.fxml");
          FXML_URL.put("Statistic", "Static.fxml");
         
         
        
    }
     public void showDialog(String info, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(info);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
        
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadFXMLMap();
         //admim =  Login1Controller.loggerAdmin;
        
        txtname.setText("Chào mừng ADMIN "+Login1Controller.loggerAdmin+" dến với hệ thống");
        //Utils.getBox(username +"WELCOME", Alert.AlertType.INFORMATION).showAndWait();
        txtname.setStyle("-fx-font-size:16px; -fx-font-weight: bold;");
    }

}
