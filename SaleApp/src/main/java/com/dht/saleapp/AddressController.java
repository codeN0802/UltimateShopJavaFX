/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class AddressController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private void switchToIndex() throws IOException {
        App.setRoot("index");

    }
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
