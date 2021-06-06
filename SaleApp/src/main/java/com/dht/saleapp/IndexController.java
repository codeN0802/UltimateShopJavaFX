/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 * 
 */


public class IndexController   implements Initializable{

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
     private void switchToPrimary() throws IOException {
        App.setRoot("primary");
  }
     
     @FXML
     private void switchToIndex() throws IOException {
         
        App.setRoot("index");
        Utils.getBox("Home", Alert.AlertType.INFORMATION).show();
       
        
  }
       @FXML
     private void switchToAddress() throws IOException {
        App.setRoot("address");
  }
     @FXML
     private void switchtoLoad_phukien() throws IOException {
        App.setRoot("base");
  }

//    @FXML private void handleButtonAction(MouseEvent event) throws IOException{
//        if(event.getTarget() == btn_icon)
//            App.setRoot("primary");
//    }
//    
////    @Override
////    public void initialize(URL url, ResourceBundle rb) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
////    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To bchange body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SidePanel.fxml"));
            VBox box = loader.load();
            SidePanelController controller = loader.getController();
        
            drawer.setSidePane(box);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
    }

    
    
    
}
