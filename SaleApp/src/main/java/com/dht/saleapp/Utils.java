/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import javafx.scene.control.Alert;

/**
 *
 * @author HUU NGHI
 */
public class Utils {
     public static Alert getBox(String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        
        alert.setTitle("ULTIMATE");
        alert.setHeaderText("Ultimate's Message");
        alert.setContentText(content);
        //alert.showAndWait();
        return alert;
    }
}
