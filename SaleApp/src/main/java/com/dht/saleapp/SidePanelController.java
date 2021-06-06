/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */

public class SidePanelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
   

    @FXML
    private Label lblname;
    private static String username = "";

    @FXML
    private JFXButton btnLogout;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        username =  Login1Controller.loggerUsername;
        lblname.setText("Hi, " + username);
    }  @FXML
     private void switchToLogin1() throws IOException {
         
        App.setRoot("login1");}   
    
    
}
