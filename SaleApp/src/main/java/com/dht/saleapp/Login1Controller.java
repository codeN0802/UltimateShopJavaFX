package com.dht.saleapp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.dht.service.JdbcUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

public class Login1Controller implements Initializable {
    @FXML
    private TextField txtusername;
    @FXML
    private TextField txtpassword;
    @FXML
    private JFXButton btnlogin;
    @FXML
    private Label lblerror;
     @FXML private JFXComboBox cbRoles ;    
      public static String loggerUsername = "";
      public static String loggerAdmin = "";
    @FXML
     private void switchToRegister() throws IOException {
         
        App.setRoot("register1");}
     @FXML
     private void switchToIndex() throws IOException {
         
        App.setRoot("index");}
      @FXML
     private void switchToAdmin() throws IOException {
         
        App.setRoot("baseadmin");
     }
     @FXML
    public void handleButtonAction(ActionEvent  event) throws IOException {

        if (event.getSource() == btnlogin) {
            //login here
            if (logIn().equals("USER")) {

                //add you loading or delays - ;-)
//                    Node node = (Node) event.getSource();
//                    Stage stage = (Stage) node.getScene().getWindow();
//                   
//                    stage.close();
//                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/index.fxml")));
//                    stage.setScene(scene);
//                    stage.show();
                switchToIndex();
            }
            if (logIn().equals("ADMIN")) {
                switchToAdmin();

            }
        }
    }
    @FXML void Select(ActionEvent event){
        String s = cbRoles.getSelectionModel().getSelectedItem().toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> list = FXCollections.observableArrayList("Admin", "User");
        cbRoles.setItems(list);
        final  JFXComboBox comboBox = new JFXComboBox(list);
        if (con == null) {
            lblerror.setTextFill(Color.TOMATO);
            lblerror.setText("Server Error : Check");
        } else {
             lblerror.setTextFill(Color.GREEN);
             lblerror.setText("Server is up : Good to go");
        }
    }
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public Login1Controller() throws SQLException {
        con = JdbcUtils.getConn();
    }

    //we gonna use string to check for status
    private String logIn() {
        String status = "Success";
        String username = txtusername.getText().trim();
        String password = txtpassword.getText().trim();
        String room = cbRoles.getSelectionModel().getSelectedItem().toString();
        if(username.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty ");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM user Where username = ? and password = ?  ";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String s1 = resultSet.getString("room");
                    if(room.equalsIgnoreCase("Admin") && s1.equalsIgnoreCase("Admin"))
                        status = "ADMIN";
                        loggerAdmin = resultSet.getString("username");
                    if(room.equalsIgnoreCase("User") && s1.equalsIgnoreCase("User")){
                        status = "USER";
                         loggerUsername = resultSet.getString("username");
                    }
                        
                }else {
                    
                    setLblError(Color.TOMATO, "Enter Correct Username/Password");
                    status = "Error";
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
        
        return status;
    }
    
    private void setLblError(Color color, String text) {
        lblerror.setTextFill(color);
        lblerror.setText(text);
        
        System.out.println(text);}
//     private void userLogger() {
//        //Taking input from the username & password fields
//        String username = txtusername.getText();}
}