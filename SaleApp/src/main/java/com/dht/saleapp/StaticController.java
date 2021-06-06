/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class StaticController implements Initializable {

    /**
     * Initializes the controller class.
     */
        @FXML
    private Button btnThongKeTheoThang;
    @FXML
    private Button btnThongKeTheoNam;
    @FXML
    private Button btnThongKeTheoQuy;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnThongKeTheoThang.setOnMouseClicked(e -> {
            try {
//                StackPane secondaryLayout = new StackPane();
                Parent root = FXMLLoader.load(getClass().getResource("ByMonth.fxml"));
                Scene secondScene = new Scene(root);
                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Thống Kê Theo Tháng");
                newWindow.setScene(secondScene);

                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);

                newWindow.show();
            } catch (IOException ex) {
                Logger.getLogger(StaticController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnThongKeTheoQuy.setOnMouseClicked(e -> {
            try {
//                StackPane secondaryLayout = new StackPane();
                Parent root = FXMLLoader.load(getClass().getResource("ByQuy.fxml"));
                Scene secondScene = new Scene(root);
                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Thống Kê Theo Quý");
                newWindow.setScene(secondScene);

                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);

                newWindow.show();
            } catch (IOException ex) {
                Logger.getLogger(StaticController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnThongKeTheoNam.setOnMouseClicked(e -> {
            try {
//                StackPane secondaryLayout = new StackPane();
                Parent root = FXMLLoader.load(getClass().getResource("ByYear.fxml"));
                Scene secondScene = new Scene(root);
                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Thống Kê Theo Năm");
                newWindow.setScene(secondScene);

                // Specifies the modality for new window.
                newWindow.initModality(Modality.WINDOW_MODAL);

                newWindow.show();
            } catch (IOException ex) {
                Logger.getLogger(StaticController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
