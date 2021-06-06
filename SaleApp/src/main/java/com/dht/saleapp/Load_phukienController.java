/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.dht.pojo.Category;
import com.dht.pojo.Product;
import com.dht.service.CategoryService;
import com.dht.service.JdbcUtils;
import com.dht.service.ProductService;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class Load_phukienController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnAo;

    @FXML
    private JFXButton btnQuan;

    @FXML
    private JFXButton btnPhuKien;

    @FXML
    private JFXButton btnSneaker;
     @FXML
    private Circle imgCustomerPhoto;
    @FXML private TableView<Product>tbl;
    private JFXButton temp = null;
   
    private static String imgPath = null;
    public static ObservableList<Product>productList=FXCollections.observableArrayList();
    private Product onView = null;
     private static int recordIndex = 1;
    @FXML
     private void switchToIndex() throws IOException {
         
        App.setRoot("index");
        Utils.getBox("Home", Alert.AlertType.INFORMATION).show();
       
        
  }
     
    @FXML
    void btnNavigators(ActionEvent event) {
        borderSelector(event); //Marking selected navigator button

        JFXButton btn = (JFXButton)event.getSource();

        // Getting navigation button label
        String btnText = btn.getText();

        // Checking which button is clicked from the map
        // and navigating to respective menu
        
    }
     private void borderSelector(ActionEvent event) {
        JFXButton btn = (JFXButton)event.getSource();

        if(temp == null) {
            temp = btn; //Saving current button properties
        } else {
            temp.setStyle(""); //Resetting previous selected button properties
            temp = btn; //Saving current button properties
        }

        btn.setStyle("-fx-background-color: #F7B5B0; -fx-background-radius: 60 0 0 60");
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        loadProductData("");
//        try {
//            loadTables();
//        } catch (SQLException ex) {
//            Logger.getLogger(Load_phukienController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//       
//        this.tbl.setRowFactory(obj -> {
//            TableRow r = new TableRow();
//           
//            r.setOnMouseClicked(evt -> {
//                Product p = this.tbl.getSelectionModel().getSelectedItem();
//                onView = productList.get(++recordIndex);
//                imgPath = P.getImage();
//                File tmpPath = new File(imgPath.replace("file:", ""));
//                if(tmpPath.exists()) { 
//                    ImagePattern img = new ImagePattern(new Image(imgPath));
//                    imgCustomerPhoto.setFill(img);}
//                
//              
//
//            });
//            return r;
//        });
//        
//    } 
//   
//     public void loadProductData(String kw) {
//        try {
//            this.tbl.getItems().clear();
//            
//            Connection conn = JdbcUtils.getConn();
//            ProductService s = new ProductService(conn);
//            
//            this.tbl.setItems(FXCollections.observableList(s.getProducts(kw)));
//            
//            conn.close();
//        } catch (SQLException ex) { 
//            ex.printStackTrace();
//            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    private void loadTables() throws SQLException {
//        
//       Connection conn = JdbcUtils.getConn();
//       CategoryService s = new CategoryService(conn);
//        
//        TableColumn colName = new TableColumn("Tên SP");
//        colName.setCellValueFactory(new PropertyValueFactory("name"));
//        
//        TableColumn colPrice = new TableColumn("Gía SP");
//        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
//        TableColumn colCategoryId = new TableColumn("Mã loại");
//        colCategoryId.setCellValueFactory(new PropertyValueFactory("category_id"));
//        
////       
//        
//        
//        
//         this.tbl.getColumns().addAll( colName, colPrice,colCategoryId);
//    }
//    
//        }
    }
}
         
     

