package com.dht.saleapp;

import com.dht.pojo.Category;
import com.dht.pojo.Product;
import com.dht.service.CategoryService;
import com.dht.service.JdbcUtils;
import com.dht.service.ProductService;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class PrimaryController implements Initializable {

    @FXML
    private ComboBox<Category> cbCates;
    @FXML
    private TableView<Product> tbProducts;
    @FXML
    private TextField txtKeywords;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private Circle imgCustomer;
    @FXML
    private Button btnUpdate;
      @FXML
    private Button btnReset;
       @FXML
    private TextField txtsoLuong;
        @FXML
    private Button btnAdd;

    
    private Product hh = null;

    private static String imgPath = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = JdbcUtils.getConn();
            CategoryService s = new CategoryService(conn);

            this.cbCates.setItems(FXCollections.observableList(s.getCates()));

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

        loadTables();
        loadProductData("");
        this.btnAdd.setDisable(false);
        this.btnUpdate.setDisable(true);

        this.txtKeywords.textProperty().addListener((obj) -> {
            loadProductData(this.txtKeywords.getText());
        });
        selected();
        imgCustomer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Choose Photo");

                File imgFile = fc.showOpenDialog(imgCustomer.getScene().getWindow());

                if(imgFile != null) { //This block will be only executed if there is any file chosen
                    imgPath = imgFile.toURI().toString();

                    if(imgPath.contains(".jpg") || imgPath.contains(".png") || imgPath.contains(".gif") ||imgPath.contains(".jpeg")) {
                        ImagePattern gg = new ImagePattern(new Image(imgPath));
                        imgCustomer.setFill(gg);
                    } else {
                        Utils.getBox("FAILED", Alert.AlertType.INFORMATION).show();
                    }
                }
            }
        });
        txtPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtPrice.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {
                    Utils.getBox("Nhập sai định  dạng", Alert.AlertType.INFORMATION).show();
                            

                }
            }
        });
         txtsoLuong.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtsoLuong.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {
                    Utils.getBox("Nhập sai định  dạng", Alert.AlertType.INFORMATION).show();
                            

                }
            }
        });
        btnReset.setOnMouseClicked(e -> {
            txtKeywords.setText(null);
            loadProductData("");
            txtName.setText("");
            txtPrice.setText("");
            cbCates.setPromptText("Loại hàng");
            txtsoLuong.setText("");
            imgCustomer.setFill(null);
            this.btnAdd.setDisable(false);
            this.btnUpdate.setDisable(true);
        });


    }
    private void reset(){
         txtName.setText("");
            txtPrice.setText("");
            cbCates.setPromptText("Loại hàng");
            txtsoLuong.setText("");
            imgCustomer.setFill(null);
            this.btnAdd.setDisable(false);
            this.btnUpdate.setDisable(true);
    }

    public void selected() {
       
        this.tbProducts.setRowFactory(obj -> {
            TableRow r = new TableRow();

            r.setOnMouseClicked(evt -> {
                 this.btnAdd.setDisable(true);
                 this.btnUpdate.setDisable(false);
                try {
                    Connection conn = JdbcUtils.getConn();
                    CategoryService s = new CategoryService(conn);
                    

                    hh = this.tbProducts.getSelectionModel().getSelectedItem();
                    //hh.getId();
                    txtName.setText(hh.getName());
                    txtPrice.setText(hh.getPrice().toString());
                    txtsoLuong.setText(Integer.toString(hh.getSoLuong()));
                    cbCates.getSelectionModel().select(s.getCateById(hh.getCategory_id()));
                    record();

                    
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            return r;
        });
    }

    public void addHandler(ActionEvent evt) {
        if (checkEmpty() != true ) {
            try {
                Connection conn = JdbcUtils.getConn();
                ProductService s = new ProductService(conn);

                Product p = new Product();
                
                
                p.setPrice(new BigDecimal(txtPrice.getText()));
                p.setCategory_id(this.cbCates.getSelectionModel().getSelectedItem().getId());
                p.setImage(imgPath);
                p.setSoLuong(Integer.parseInt(txtsoLuong.getText()));

                p.setName(txtName.getText());


                if (s.addProduct(p) == true) {
                    Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                    this.loadProductData("");
                    this.btnUpdate.setDisable(true);
                    reset();
                } else {
                    Utils.getBox("FAILED", Alert.AlertType.INFORMATION).show();
                }

            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                Utils.getBox("Đã có sản phẩm cùng tên trong kho", Alert.AlertType.INFORMATION).show();
            }
        } else {
            Utils.getBox("Vui long nhap day du thong tin", Alert.AlertType.INFORMATION).show();
        }
    }

    public void updateHandler(ActionEvent evt) {

        try {
            Connection conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn);
            Product p1 = new Product();
            p1.setId(hh.getId());
            p1.setName(txtName.getText());
            p1.setPrice(new BigDecimal(txtPrice.getText()));
            p1.setCategory_id(cbCates.getSelectionModel().getSelectedItem().getId());
            p1.setImage(imgPath);
            p1.setSoLuong(Integer.parseInt(txtsoLuong.getText()));
            if (s.updateProduct(p1) == true) {
                Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                loadProductData("");
                reset();
                btnAdd.setDisable(false);
            } else {
                Utils.getBox("FAILED", Alert.AlertType.INFORMATION).show();
            }

        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadProductData(String kw) {
        try {
            this.tbProducts.getItems().clear();

            Connection conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn);

            this.tbProducts.setItems(FXCollections.observableList(s.getProducts(kw)));

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTables() {
        TableColumn colId = new TableColumn("Mã SP");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Tên SP");
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colPrice = new TableColumn("Gía SP");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        TableColumn colSo = new TableColumn("Số lượng hàng tồn");
        colSo.setCellValueFactory(new PropertyValueFactory("soLuong"));

        TableColumn colType = new TableColumn("Type");
        colType.setCellValueFactory(new PropertyValueFactory("category_id"));
        
        TableColumn colImage = new TableColumn("Photo");
        colImage.setCellValueFactory(new PropertyValueFactory("image")); 
        //colImage.setPrefWidth(600);

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
                                    Product p = (Product) cell.getTableRow().getItem();

                                    Connection conn = JdbcUtils.getConn();
                                    ProductService s = new ProductService(conn);

                                    if (s.deleleProduct(p.getId())) {
                                        Utils.getBox("SUCCESSFUL", Alert.AlertType.INFORMATION).show();
                                        loadProductData("");
                                    } else {
                                        Utils.getBox("FAILED", Alert.AlertType.ERROR).show();
                                    }

                                    conn.close();
                                } catch (SQLException ex) {

                                    ex.printStackTrace();
                                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

            });

            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });

        this.tbProducts.getColumns().addAll(colId, colName, colType, colPrice,colSo ,colImage ,colAction);
    }
    private void record(){
        if (hh.getImage() == null) {
                        ImagePattern img = new ImagePattern(new Image("file:/C:/Users/HUU NGHI/Desktop/it81demo/SaleApp/src/main/java/com/dht/images/icons8_home_52px.png"));
                        imgCustomer.setFill(img);
                    } else {
                        try {

                            imgPath = hh.getImage();

                            File tmpPath = new File(imgPath.replace("file:", ""));

                            if (tmpPath.exists()) {
                                ImagePattern img = new ImagePattern(new Image(imgPath));
                                imgCustomer.setFill(img);
                            } else {
                                imgPath = null;
                                ImagePattern img = new ImagePattern(new Image("file:/C:/Users/HUU NGHI/Desktop/it81demo/SaleApp/src/main/java/com/dht/images/icons8_home_52px.png"));
                                imgCustomer.setFill(img);
                            }

                        } catch (Exception e) {
                            //Fallback photo in case image not found
                            ImagePattern img = new ImagePattern(new Image("file:/C:/Users/HUU NGHI/Desktop/it81demo/SaleApp/src/main/java/com/dht/images/icons8_home_52px.png"));
                            imgCustomer.setFill(img);
                        }
                    }
    }
    private boolean checkEmpty(){
        return this.txtName.getText().isEmpty() || this.txtPrice.getText().isEmpty()|| this.cbCates.getSelectionModel().isEmpty()||this.txtsoLuong.getText().isEmpty() ;
    }
}