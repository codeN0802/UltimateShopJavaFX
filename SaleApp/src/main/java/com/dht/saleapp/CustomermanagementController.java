/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.dht.pojo.User;
import com.dht.service.JdbcUtils;
import com.dht.service.ProductService;
import com.dht.service.UserService;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class CustomermanagementController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private TableView<User> tbUser;

    @FXML
    private TextField txtKeywords;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;
    private User u;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadUserData("");
        loadTables();
        this.txtKeywords.textProperty().addListener((obj) -> {
            loadUserData(this.txtKeywords.getText());
        });
        selected();
       
    }
     public void loadUserData(String kw) {
        try {
            this.tbUser.getItems().clear();

            Connection conn = JdbcUtils.getConn();
            UserService s = new UserService(conn);

            this.tbUser.setItems(FXCollections.observableList(s.getUserByName(kw)));

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     private void loadTables() {
        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colHo = new TableColumn("Họ");
        colHo.setCellValueFactory(new PropertyValueFactory("first_name"));

        TableColumn colTen = new TableColumn("Tên");
        colTen.setCellValueFactory(new PropertyValueFactory("last_name"));

        TableColumn colEmail = new TableColumn("Email");
       colEmail.setCellValueFactory(new PropertyValueFactory("email"));
       
        TableColumn colRoom = new TableColumn("Room");
       colRoom.setCellValueFactory(new PropertyValueFactory("room"));
        
      
        

        TableColumn colAction = new TableColumn();
        colAction.setCellFactory((obj) -> {
            Button btn = new Button("Xóa");
            btn.setStyle("-fx-background-color: #Da5749");
            

            btn.setOnAction(evt -> {
                Utils.getBox("Ban chac chan xoa khong?", Alert.AlertType.CONFIRMATION)
                        .showAndWait().ifPresent(bt -> {
                            if (bt == ButtonType.OK) {
                                try {
                                    TableCell cell = (TableCell) ((Button) evt.getSource()).getParent();
                                    User p = (User) cell.getTableRow().getItem();

                                    Connection conn = JdbcUtils.getConn();
                                    UserService s = new UserService(conn);

                                    if (s.deleleUser(p.getId())) {
                                        Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                        loadUserData("");
                                    } else {
                                        Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
                                    }

                                    conn.close();
                                } catch (SQLException ex) {

                                    ex.printStackTrace();
                                    Logger.getLogger(CustomermanagementController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

            });

            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

        this.tbUser.getColumns().addAll(colId, colHo,  colTen, colEmail ,colRoom,colAction);
    }

    private void selected() {
        this.tbUser.setRowFactory(obj ->{
            TableRow r = new TableRow();
            r.setOnMouseClicked(event ->{
                u= this.tbUser.getSelectionModel().getSelectedItem();
                txtName.setText(u.getFirst_name() +" " +u.getLast_name());
                txtEmail.setText(u.getEmail());
            });
            
            
            return r;
            
        });
    }
     
    
    
}
