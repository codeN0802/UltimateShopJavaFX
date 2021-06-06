/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.dht.pojo.User;
import com.dht.service.JdbcUtils;
import com.dht.service.UserService;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class Register1Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField txtHo;
    @FXML private TextField txtTen;
    @FXML private TextField txtEmail;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private TextField txtXacNhan;
    @FXML private JFXButton btnRegister;
    @FXML private Text txtRoom;
     @FXML
    private Label lblerror;
    
    
   
     @FXML
     private void switchToLogin1() throws IOException {
         
        App.setRoot("login1");}
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(con == null){
            lblerror.setTextFill(Color.TOMATO);
            lblerror.setText("Server Error : Check");
        } else {
             lblerror.setTextFill(Color.GREEN);
             lblerror.setText("Server is up : Good to go");
        }
        
    }    
     Connection con = null;
     public Register1Controller() throws SQLException{
         con = JdbcUtils.getConn();
     }
    public void handleButtonAction(ActionEvent event){
        if(txtHo.getText().isEmpty() || txtTen.getText().isEmpty()|| txtEmail.getText().isEmpty()
                || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || txtXacNhan.getText().isEmpty() 
                || !txtPassword.getText().strip().equals(txtXacNhan.getText().strip())){
                setLblError(Color.TOMATO, "invalid!!!");
        }else{
            try {
                Connection conn = JdbcUtils.getConn();
                UserService s = new UserService(conn);
                User u = new User();

                u.setFirst_name(txtHo.getText());
                u.setLast_name(txtTen.getText());
                u.setEmail(txtEmail.getText());
                u.setUsername(txtUsername.getText());
                u.setPassword(txtPassword.getText());
                u.setRoom(txtRoom.getText());

                if (s.addUser(u) == true) {
                    Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                     setLblError(Color.GREEN, "Success!!!");
                   
                     txtHo.setText("");
                     txtTen.setText("");
                     txtEmail.setText("");
                     txtUsername.setText("");
                     txtPassword.setText("");
                     txtXacNhan.setText("");

                } else
                    Utils.getBox("FAILED", Alert.AlertType.INFORMATION).show();
            } catch (SQLException ex) {
                Logger.getLogger(Register1Controller.class.getName()).log(Level.SEVERE, null, ex);
                Utils.getBox("Email hoặc username đã được đăng ký", Alert.AlertType.INFORMATION).show();
            }
        }
        
        
    }
    
     private void setLblError(Color color, String text) {
        lblerror.setTextFill(color);
        lblerror.setText(text);
        
        System.out.println(text);
    }
}
