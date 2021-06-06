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
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import com.dht.service.ProductService;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class ProductController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane productListPane;

    @FXML
    private TableView<Product> tbl;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Integer> colProductType;

    @FXML
    private TableColumn<Product, Integer> colProductPrice;

    @FXML
    private AnchorPane productPane;

    @FXML
    private Circle imgCustomerPhoto;

    @FXML
    private Label productName;

    @FXML
    private Label productType;

    @FXML
    private Label producPrice;

    @FXML
    private JFXButton btnPrev;
    @FXML
    private JFXButton btnListAll;
    @FXML private JFXButton btnGoBack;
    @FXML private Label lblindex;
    
   
    

    @FXML
    private JFXButton btnNext;
    public static ObservableList<Product> itemList = FXCollections.observableArrayList();
    public static ArrayList<String> itemNames = new ArrayList<>();
   
      private static boolean addFlag = false;
    private static int recordIndex = 0;
    private static int recordSize = 0;
    private Product onView = null;
    private static String imgPath = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //setView();
       

        
      
        try {
            reloadRecords();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setView() {

        productListPane.setVisible(false);
        //productPane.setVisible(false);
        recordIndex = 0; //Resetting index value
        recordSize = itemList.size();

        btnNext.setOnAction(event -> {
            onView = itemList.get(++recordIndex);
            recordNavigator();
            lblindex.setText("Showing " + (recordIndex + 1) +" of " + (recordSize)+ " results");
            lblindex.setTextFill(Color.GREEN);
            if (recordIndex == recordSize - 1) {
                btnNext.setDisable(true);
            }
            btnPrev.setDisable(false);
        });

        btnPrev.setOnAction(event -> {
            onView = itemList.get(--recordIndex);
            recordNavigator();
             lblindex.setText("Showing " + (recordIndex + 1) +" of " + (recordSize)+ " results");
              lblindex.setTextFill(Color.GREEN);
            btnNext.setDisable(false);
            if (recordIndex == 0) {
                btnPrev.setDisable(true);
            }
        });

        btnNext.setDisable(true); //For purpose ;)

        if (recordSize > 0) {
            onView = itemList.get(recordIndex); //Setting value for current record

            recordNavigator(); 
            lblindex.setText("Showing " + (recordIndex + 1) +" of " + (recordSize)+ " results");
             lblindex.setTextFill(Color.GREEN);

            if (recordSize > 1) {
                btnNext.setDisable(false); //Next entry will be enabled if there is more than one entry
            }
        }

        btnPrev.setDisable(true);

    }

    private void reloadRecords() throws SQLException {
        itemList.clear();
        Connection connection = JdbcUtils.getConn();
        try {
            PreparedStatement getItemList = connection.prepareStatement("SELECT * FROM product  ");
            ResultSet itemResultSet = getItemList.executeQuery();

            while (itemResultSet.next()) {
                Product P = new Product();

                P.setName(itemResultSet.getString("name"));
                P.setPrice(itemResultSet.getBigDecimal("price"));
                P.setImage(itemResultSet.getString("image"));
                P.setCategory_id(itemResultSet.getInt("category_id"));

                itemNames.add(itemResultSet.getString("name"));

                itemList.add(P);

            }

            setView();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   

    

    private void recordNavigator() {

        try {
            Connection conn = JdbcUtils.getConn();
            CategoryService ctes = new CategoryService(conn);
            Category cte = ctes.getCateById(onView.getCategory_id());
            if (cte != null) {
                productType.setText(cte.getName());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        productName.setText(onView.getName());
        //productType.setText(Integer.toString(onView.getCategory_id()));
       
         Locale locale = new Locale("vi", "VN");
         NumberFormat format = NumberFormat.getCurrencyInstance(locale);
         producPrice.setText(format.format(onView.getPrice()));
    

        //producPrice.setText(onView.getPrice().toString());
        if (onView.getImage() == null) {
            ImagePattern img = new ImagePattern(new Image("file:/C:/Users/HUU NGHI/Desktop/it81demo/SaleApp/src/main/java/com/dht/images/icons8_home_52px.png"));
            imgCustomerPhoto.setFill(img);
        } else {
            try {
                imgPath = onView.getImage();

                File tmpPath = new File(imgPath.replace("file:", ""));

                if (tmpPath.exists()) {
                    ImagePattern img = new ImagePattern(new Image(imgPath));
                    imgCustomerPhoto.setFill(img);
                } else {
                    imgPath = null;
                    ImagePattern img = new ImagePattern(new Image("file:/C:/Users/HUU NGHI/Desktop/it81demo/SaleApp/src/main/java/com/dht/images/icons8_home_52px.png"));
                    imgCustomerPhoto.setFill(img);
                }

            } catch (Exception e) {
                //Fallback photo in case image not found
                ImagePattern img = new ImagePattern(new Image("file:/C:/Users/HUU NGHI/Desktop/it81demo/SaleApp/src/main/java/com/dht/images/icons8_home_52px.png"));
                imgCustomerPhoto.setFill(img);
            }
        }

    }

    @FXML
    void listAllItems(ActionEvent event) {
        btnGoBack.setOnAction(e -> {
            productListPane.setVisible(false);  //Setting item list pane visible
            productPane.setVisible(true); //Setting item pane visible
        });
        tbl.setItems(itemList);
       
        listView();
    }

    private void listView() {
       productPane.setVisible(false); //Setting default item pane not visible
        productListPane.setVisible(true); //Setting item list visible

        
        colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
       
        colProductType.setCellValueFactory(new PropertyValueFactory<>("category_id"));
       
       colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    }


