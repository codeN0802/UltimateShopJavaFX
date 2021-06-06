/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.dht.pojo.ChiTietHoaDon;
import com.dht.pojo.HoaDon;
import com.dht.pojo.User;
import com.dht.service.ChiTietHoaDonService;
import com.dht.service.HoaDonService;
import com.dht.service.JdbcUtils;
import com.dht.service.UserService;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class BillManagementController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtIDBill;

    @FXML
    private DatePicker pcNgayLap;

    @FXML
    private TextField txtThanhTien;

    @FXML
    private Text txtIDKhachHang;

    @FXML
    private Text txtTenKhachHang;

    @FXML
    private Text txtSDTKhachHang;

    @FXML
    private Text txtDiaChiKhachHang;

    @FXML
    private TableView<ChiTietHoaDon> tbChiTietBill;

    @FXML
    private TableView<HoaDon> tbListBill;

    @FXML
    private Button btnPrintBill;

    @FXML
    private DatePicker pcSearch;

    @FXML
    private Button btnSuaListHoaDon;

    @FXML
    private Button btnXoaListHoaDon;
    @FXML
    private Text txttongtien;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoadTables();
        LoadBill();
        LoadDataBills();
        this.pcSearch.getEditor().textProperty().addListener((obj) -> {
            try {
                Connection conn = JdbcUtils.getConn();
                HoaDonService s = new HoaDonService(conn);
                if (this.pcSearch.getEditor().getText().isEmpty() != true) {
                    this.tbListBill.setItems(FXCollections.observableList(s.getListHoaDonByDate(Date.valueOf(this.pcSearch.getValue()))));
                } else {
                    LoadDataBills();
                }

            } catch (SQLException ex) {
                Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        pcNgayLap.getEditor().setDisable(true);

        this.txtIDBill.setDisable(true);
        this.txtIDKhachHang.setDisable(true);
        this.tbListBill.setRowFactory(obj -> {
            TableRow r = new TableRow();
            SetDisableButtonListBill(false);
            r.setOnMouseClicked(e -> {
                SetDisableButtonListBill(true);
                CheckIsEmpty();
                RestInputKhacHang();
                HoaDon hd = this.tbListBill.getSelectionModel().getSelectedItem();
                try {
                    Connection conn = JdbcUtils.getConn();
                    UserService khs = new UserService(conn);
                    User kh = khs.getUserById(hd.getUser_id());
                    if (kh != null) {
                        txtIDKhachHang.setText(Integer.toString(kh.getId()));
                        txtTenKhachHang.setText(kh.getFirst_name() + " " + kh.getLast_name());
                        //txtSDTKhachHang.setText(kh.getSDT());
                        txtDiaChiKhachHang.setText(kh.getEmail());

                    }

                    conn.close();

                } catch (SQLException ex) {
                    Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
                }
                txtIDBill.setText(Integer.toString(hd.getId()));

                pcNgayLap.setValue(convertToLocalDateViaSqlDate(hd.getCreated_date()));
                txtThanhTien.setText(hd.getAmount().toString());
                Locale locale = new Locale("vi", "VN");
                NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                String Tong = format.format(hd.getAmount());
                txttongtien.setText(Tong);
                LoadDataChiTietHoaDon(Integer.parseInt(txtIDBill.getText()));

                btnSuaListHoaDon.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {

                            if (CheckIsEmpty() == true) {
                                Utils.getBox("Vui Long Nhap Thong Tin Day Du", Alert.AlertType.ERROR).show();
                            } else {
                                Connection conn = JdbcUtils.getConn();
                                HoaDonService s = new HoaDonService(conn);
                                HoaDon hdUpdate = new HoaDon();
                                hdUpdate.setId(hd.getId());
                                hdUpdate.setCreated_date(Date.valueOf(pcNgayLap.getValue()));
                                hdUpdate.setUser_id(hd.getUser_id());

                                hdUpdate.setAmount(new BigDecimal(txtThanhTien.getText()));

                                if (s.UpdateListHoaDon(hdUpdate)) {
                                    Utils.getBox("Thành Công", Alert.AlertType.INFORMATION).show();
                                    conn.close();
                                    LoadDataBills();
                                    Locale locale = new Locale("vi", "VN");
                                    NumberFormat format = NumberFormat.getCurrencyInstance(locale);
                                    txttongtien.setText(format.format(hdUpdate.getAmount()));
                                } else {
                                    Utils.getBox("Thất Bại", Alert.AlertType.INFORMATION).show();
                                    conn.close();
                                }
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                btnXoaListHoaDon.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Utils.getBox("Ban chac chan xoa khong?", Alert.AlertType.CONFIRMATION)
                                .showAndWait().ifPresent(bt -> {
                                    if (bt == ButtonType.OK) {
                                        try {
                                            Connection conn = JdbcUtils.getConn();
                                            HoaDonService s = new HoaDonService(conn);

                                            if (s.deleleHoaDon(hd.getId())) {
                                                Utils.getBox("Thành Công", Alert.AlertType.INFORMATION).show();
                                                SetDisableButtonListBill(true);
                                                //ResetInput();
                                                RestInputKhacHang();
                                                pcSearch.setValue(null);
                                                LoadDataBills();
                                                tbChiTietBill.getItems().clear();
                                            } else {
                                                Utils.getBox("Thất Bại", Alert.AlertType.ERROR).show();
                                            }

                                            conn.close();
                                        } catch (SQLException ex) {

                                            ex.printStackTrace();
                                            Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });

                    }

                });
            });
            return r;
        });

    }

    private void LoadTables() {
        TableColumn colIdBill = new TableColumn("ID Hóa Đơn");
        colIdBill.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn colIdNhanVien = new TableColumn("ID Người Mua");
        colIdNhanVien.setCellValueFactory(new PropertyValueFactory("user_id"));
        TableColumn colNgayLap = new TableColumn("Ngày Lập");
        colNgayLap.setCellValueFactory(new PropertyValueFactory("created_date"));
        TableColumn colThanhTIen = new TableColumn("Thành Tiền");
        colThanhTIen.setCellValueFactory(new PropertyValueFactory("amount"));

        this.tbListBill.getColumns().addAll(colIdBill, colIdNhanVien, colNgayLap, colThanhTIen);
    }

    private void LoadBill() {
        TableColumn colIdHH = new TableColumn("Mã Hàng Hóa");
        colIdHH.setCellValueFactory(new PropertyValueFactory("product_id"));
        TableColumn colSoLuong = new TableColumn("Số Lượng");
        colSoLuong.setCellValueFactory(new PropertyValueFactory("numx"));
        TableColumn colDonGia = new TableColumn("Đơn Giá");
        colDonGia.setCellValueFactory(new PropertyValueFactory("unit_price"));
        this.tbChiTietBill.getColumns().addAll(colIdHH, colSoLuong, colDonGia);
    }

    private void LoadDataBills() {
        try {
            this.tbListBill.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            HoaDonService s = new HoaDonService(conn);
            this.tbListBill.setItems(FXCollections.observableList(s.getListHoaDon()));
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void LoadDataChiTietHoaDon(int id) {
        try {
            this.tbChiTietBill.getItems().clear();
            Connection conn = JdbcUtils.getConn();
            ChiTietHoaDonService s = new ChiTietHoaDonService(conn);
            this.tbChiTietBill.setItems(FXCollections.observableList(s.getListChiTietById(id)));
        } catch (SQLException ex) {
            Logger.getLogger(BillManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SetDisableButtonListBill(boolean isAction) {
        if (isAction == true) {
            btnSuaListHoaDon.setDisable(false);
            btnXoaListHoaDon.setDisable(false);
        } else {
            btnSuaListHoaDon.setDisable(true);
            btnXoaListHoaDon.setDisable(true);
        }
    }

    private boolean CheckIsEmpty() {
        return txtThanhTien.getText().isEmpty();

    }
//    private void ResetInput() {
//       txtIDBill.setText("");
//        txtIDNhanVIen.setText("");
//        txtThanhTien.setText("");
//        txtVAT.setText("");
//        pcNgayLap.setValue(null);
//    }

    private void RestInputKhacHang() {
        txtIDKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtSDTKhachHang.setText("");
        txtDiaChiKhachHang.setText("");
        txtIDBill.setText("");
        txtThanhTien.setText("");
        pcNgayLap.setValue(null);
        txttongtien.setText("");
        //pcSearch.setValue(null);
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
