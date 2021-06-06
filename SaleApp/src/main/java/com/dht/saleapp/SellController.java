/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.dht.pojo.ChiTietHoaDon;
import com.dht.pojo.HoaDon;
import com.dht.pojo.Product;
import com.dht.pojo.User;
import com.dht.service.ChiTietHoaDonService;
import com.dht.service.HoaDonService;
import com.dht.service.JdbcUtils;
import com.dht.service.ProductService;
import com.dht.service.UserService;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.CullFace;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class SellController implements Initializable {

    /**
     * @return the hoaDon
     */
    public List<ChiTietHoaDon> getHoaDon() {
        return hoaDon;
    }

    /**
     * @param hoaDon the hoaDon to set
     */
    public void setHoaDon(List<ChiTietHoaDon> hoaDon) {
        this.hoaDon = hoaDon;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnAddBill;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtIDHangHoa;

    @FXML
    private TextField txtSoLuongHangHoa;

    @FXML
    private TextField txtDonGiaHangHoa;

    @FXML
    private ComboBox<User> cbKhachHang;

    @FXML
    private Text txtNotiSearchKH;

    @FXML
    private TableView<ChiTietHoaDon> tbBill;

    @FXML
    private Button btnEditBill;

    @FXML
    private Button btnPrintBill;

    @FXML
    private Button btnDeleteBill;

    @FXML
    private Text txtTongTien;

    @FXML
    private TableView<Product> tbListProduct;

    @FXML
    private Button btnSearchCustomer;

    @FXML
    private TextField kwSearch;

    @FXML
    private Button btnResetLoaiHang;
    private List<ChiTietHoaDon> hoaDon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.hoaDon = new ArrayList<>();
        try {
            Connection conn = JdbcUtils.getConn();
            UserService s = new UserService(conn);
            String kw = "User";

            this.cbKhachHang.setItems(FXCollections.observableList(s.getUserByRoom(kw)));

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtSoLuongHangHoa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                try {
                    if (!newValue.matches("\\d*")) {
                        txtSoLuongHangHoa.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                } catch (NullPointerException e) {
                    Utils.getBox("Nhập sai định  dạng", Alert.AlertType.INFORMATION).show();

                }
            }
        });
        this.tbListProduct.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(e -> {
                Product hh = this.tbListProduct.getSelectionModel().getSelectedItem();
                txtIDHangHoa.setText(Integer.toString(hh.getId()));// String.valueof == Integer.toString
                txtDonGiaHangHoa.setText(hh.getPrice().toString());
                txtSoLuongHangHoa.setText(Integer.toString(1));
            });
            return r;
        });
        this.kwSearch.textProperty().addListener((obj) -> {
            LoadDataHangHoa(this.kwSearch.getText());
        });
        btnResetLoaiHang.setOnMouseClicked(e -> {
            cbKhachHang.setPromptText("Khách hàng");
            //cbKhachHang.setValue(null);

            kwSearch.setText("");
            txtSoLuongHangHoa.setText("");
            txtIDHangHoa.setText("");
            txtDonGiaHangHoa.setText("");
            LoadDataHangHoa("");

            btnDeleteBill.setDisable(true);
            btnEditBill.setDisable(true);

        });

        btnAddBill.setOnMouseClicked(e -> {
            if (checkIsEmpty()) {
                Utils.getBox("Vui nhập đầy đủ thông tin", Alert.AlertType.ERROR).show();
            } else {
                if (CheckSoLuongHangHoa(Integer.parseInt(txtIDHangHoa.getText()), Integer.parseInt(txtSoLuongHangHoa.getText())) == false) {
                    Utils.getBox("Số Lượng Bán Ra Nhiều Hơn Số Lượng Trong Kho", Alert.AlertType.ERROR).show();
                } else {
                    if (this.hoaDon.toArray().length > 0) {
                        if (timKiemChiTietHoaDon(Integer.parseInt(txtIDHangHoa.getText())) != null) {
                            ChiTietHoaDon ct = timKiemChiTietHoaDon(Integer.parseInt(txtIDHangHoa.getText()));
                            if (CheckSoLuongHangHoa(Integer.parseInt(txtIDHangHoa.getText()), ct.getNumx() + Integer.parseInt(txtSoLuongHangHoa.getText())) == false) {
                                Utils.getBox("Số Lượng Bán Ra Nhiều Hơn Số Lượng Trong Kho", Alert.AlertType.ERROR).show();
                            } else {
                                ct.setNumx(ct.getNumx() + Integer.parseInt(txtSoLuongHangHoa.getText()));
                                this.hoaDon.set(this.hoaDon.indexOf(ct), ct);;
                            }
                            
                        } else {

                            ChiTietHoaDon ct = new ChiTietHoaDon();
                            ct.setUnit_price(new BigDecimal(txtDonGiaHangHoa.getText()));
                            ct.setNumx(Integer.parseInt(txtSoLuongHangHoa.getText()));
                            ct.setProduct_id(Integer.parseInt(txtIDHangHoa.getText()));
                            this.hoaDon.add(ct);
                        }
                    } else {
                        ChiTietHoaDon ct = new ChiTietHoaDon();
                        ct.setUnit_price(new BigDecimal(txtDonGiaHangHoa.getText()));
                        ct.setNumx(Integer.parseInt(txtSoLuongHangHoa.getText()));
                        ct.setProduct_id(Integer.parseInt(txtIDHangHoa.getText()));
                        this.hoaDon.add(ct);
                    }

                    LoadDataBill();
                    BigDecimal tong = TinhTongTien();
                    //String Tong = String.format("%..0f",tong);

                    Locale locale = new Locale("vi", "VN");
                    NumberFormat format = NumberFormat.getCurrencyInstance(locale);

                    String Tong = format.format(tong).toString();
                    txtTongTien.setText(Tong);
                    txtTongTien.setStyle("-fx-font-size: 18px;");
                }

            }
        });
        btnPrintBill.setOnMouseClicked(e -> {
            if (this.hoaDon.toArray().length > 0) {
                PrintHoaDon();
            } else {
                Utils.getBox("Hóa Đơn Của Bạn Đang Trống", Alert.AlertType.WARNING).show();
            }
        });

        disableButtonBill(false);
        this.tbBill.setRowFactory(obj -> {
            TableRow r = new TableRow();
            r.setOnMouseClicked(e -> {
                disableButtonBill(true);
                ChiTietHoaDon ct = this.tbBill.getSelectionModel().getSelectedItem();
                try {
                    txtIDHangHoa.setText(Integer.toString(ct.getProduct_id()));
                    txtIDHangHoa.setDisable(true);
                    txtDonGiaHangHoa.setText(ct.getUnit_price().toString());
                    txtSoLuongHangHoa.setText(Integer.toString(ct.getNumx()));
                } catch (NullPointerException event) {

                }

                btnDeleteBill.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Utils.getBox("Bạn có chắc chắn xóa không?", Alert.AlertType.CONFIRMATION)
                                .showAndWait().ifPresent(bt -> {
                                    if (bt == ButtonType.OK) {
                                        hoaDon.remove(ct);
                                        LoadDataBill();
                                        ResetInput();
                                        BigDecimal tong = TinhTongTien();
                                        //String Tong = String.format("%..0f",tong);

                                        Locale locale = new Locale("vi", "VN");
                                        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

                                        String Tong = format.format(tong);
                                        txtTongTien.setText(Tong);
                                        txtTongTien.setStyle("-fx-font-size: 18px;");
//                                        String Tong = TinhTongTien().toString() + " VNĐ";
//                                        txtTongTien.setText(Tong);
//                                        String thanhTien = String.format("%.2f", ThanhTien(TinhTongTien(), 0.1, Integer.parseInt(txtDiemKhachHangSuDung.getText())));
//                                       txtThanhTien.setText(thanhTien);
                                    }
                                });

                    }
                });
                btnEditBill.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        if (checkIsEmpty()) {
                            Utils.getBox("Vui nhập đầy đủ thông tin", Alert.AlertType.ERROR).show();
                        } else {
                             if (CheckSoLuongHangHoa(Integer.parseInt(txtIDHangHoa.getText()), Integer.parseInt(txtSoLuongHangHoa.getText())) == false) {
                                Utils.getBox("Số Lượng Bán Ra Nhiều Hơn Số Lượng Trong Kho", Alert.AlertType.ERROR).show();
                            } else {
                                if (timKiemChiTietHoaDon(Integer.parseInt(txtIDHangHoa.getText())) != null) {
                                    ChiTietHoaDon ct = tbBill.getSelectionModel().getSelectedItem();
                                    ct.setNumx(Integer.parseInt(txtSoLuongHangHoa.getText()));
                                    hoaDon.set(hoaDon.indexOf(ct), ct);
                                }
                                LoadDataBill();
                                BigDecimal tong = TinhTongTien();
                                //String Tong = String.format("%..0f",tong);

                                Locale locale = new Locale("vi", "VN");
                                NumberFormat format = NumberFormat.getCurrencyInstance(locale);

                                String Tong = format.format(tong);
                                txtTongTien.setText(Tong);
                                txtTongTien.setStyle("-fx-font-size: 18px;");}
    //                                String Tong = TinhTongTien().toString() + " VNĐ";
    //                                txtTongTien.setText(Tong);
    //                                String thanhTien = String.format("%.2f", ThanhTien(TinhTongTien(), 0.1, Integer.parseInt(txtDiemKhachHangSuDung.getText())));
    //                                txtThanhTien.setText(thanhTien);

                        }
                    }
                });

            });
            return r;
        });

        LoadTableListProduct();
        LoadDataHangHoa("");
        LoadTableBill();
        txtTongTien.setText("0 VND");
    }

    private void LoadTableListProduct() {
        TableColumn colId = new TableColumn("Mã SP");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Tên SP");
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colNum = new TableColumn("Số lượng tồn: ");
       colNum.setCellValueFactory(new PropertyValueFactory("soLuong"));

        TableColumn colPrice = new TableColumn("Gía SP");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        this.tbListProduct.getColumns().addAll(colId, colName, colNum, colPrice);
    }

    private void LoadDataHangHoa(String kw) {
        try {
            this.tbListProduct.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn);
            this.tbListProduct.setItems(FXCollections.observableList(s.getListHangHoaConTrongKho(kw)));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void disableButtonBill(boolean action) {
        if (action == false) {
            btnEditBill.setDisable(true);
            btnDeleteBill.setDisable(true);
        } else {
            btnEditBill.setDisable(false);
            btnDeleteBill.setDisable(false);
        }
    }

    private void LoadTableBill() {
        TableColumn colIdHH = new TableColumn("Mã Hàng Hóa");
        colIdHH.setCellValueFactory(new PropertyValueFactory("product_id"));
        TableColumn colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("numx"));
        TableColumn colDonGia = new TableColumn("Đơn Giá");
        colDonGia.setCellValueFactory(new PropertyValueFactory("unit_price"));
        this.tbBill.getColumns().addAll(colIdHH, colSoLuong, colDonGia);
    }

    private void LoadDataBill() {
        this.tbBill.refresh();
        this.tbBill.setItems(FXCollections.observableArrayList(this.hoaDon));
    }

    private boolean checkIsEmpty() {
        return this.txtIDHangHoa.getText().isEmpty() || this.txtSoLuongHangHoa.getText().isEmpty() || this.txtDonGiaHangHoa.getText().isEmpty()
                || this.cbKhachHang.getSelectionModel().isEmpty();
    }

    public ChiTietHoaDon timKiemChiTietHoaDon(int x) {
        for (ChiTietHoaDon h : this.hoaDon) {
            if (h.getProduct_id() == x) {
                return h;
            }
        }
        return null;
    }

    private BigDecimal TinhTongTien() {
        BigDecimal tong = new BigDecimal(0);

        for (ChiTietHoaDon ct : this.hoaDon) {
            BigDecimal item = ct.getUnit_price().multiply(new BigDecimal(ct.getNumx()));
            tong = tong.add(item);
        }
        return tong;
    }

    public boolean UpdateSoLuongHangHoa(int id, int soLuong) {
        boolean isUpdateSoLuongHangHoa = true;
        try {
            Connection conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn);
            int soLuongUpdate = s.getProById(id).getSoLuong() - soLuong;
            isUpdateSoLuongHangHoa = s.updateSoLuongHangHoa(id, soLuongUpdate);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SellController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isUpdateSoLuongHangHoa;
    }

    public boolean CheckSoLuongHangHoa(int idHangHoa, int soLuongMuonBan) {
        boolean isCheckSoLuongHangHoa = true;
        try {

            Connection conn = JdbcUtils.getConn();
            ProductService s = new ProductService(conn);
            if (s.getProById(idHangHoa).getSoLuong() < soLuongMuonBan) {
                isCheckSoLuongHangHoa = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(SellController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isCheckSoLuongHangHoa;
    }

    private void ResetInput() {
        txtSoLuongHangHoa.setText("");
        txtIDHangHoa.setText("");
        txtDonGiaHangHoa.setText("");
        //txtIDHangHoa.setDisable(false);
    }

    private void PrintHoaDon() {

        try {
            Connection conn = JdbcUtils.getConn();
            ChiTietHoaDonService cthds = new ChiTietHoaDonService(conn);
            HoaDonService hds = new HoaDonService(conn);
            UserService khs = new UserService(conn);
            HoaDon hd = new HoaDon();
            LocalDateTime dateNow = java.time.LocalDateTime.now();
            Date ngayLap = Date.valueOf(dateNow.toLocalDate());
            hd.setCreated_date(ngayLap);
            hd.setAmount(new BigDecimal(TinhTongTien().toString()));
            hd.setUser_id(cbKhachHang.getSelectionModel().getSelectedItem().getId());
            HoaDon hdNew = hds.CreateHoaDon(hd);
            if (hdNew != null) {
                boolean isAddChiTietHoaDon = true;
                for (ChiTietHoaDon h : this.hoaDon) {
                    h.setOrder_id(hdNew.getId());
                    if (cthds.AddChiTietHoaDon(h)) {
                        isAddChiTietHoaDon = isAddChiTietHoaDon && UpdateSoLuongHangHoa(h.getProduct_id(), h.getNumx());
                    }
                }
                if (isAddChiTietHoaDon) {
                    Utils.getBox("Thânh Công", Alert.AlertType.INFORMATION).show();
                    txtTongTien.setText("0 VNĐ");
                    cbKhachHang.setPromptText("Khách hàng");

                    txtSoLuongHangHoa.setText("");
                    txtIDHangHoa.setText("");
                    txtDonGiaHangHoa.setText("");
                    txtNotiSearchKH.setText("");
                    // txtThanhTien.setText("0");
                    hoaDon.removeAll(hoaDon);

                    // txtDiemKhachHangSuDung.setText("0");
                    //btnSuDungDiem.setDisable(true);
                    LoadDataBill();
                    LoadDataHangHoa("");
                } else {
                    Utils.getBox("Thất Bại", Alert.AlertType.ERROR).show();
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(SellController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
