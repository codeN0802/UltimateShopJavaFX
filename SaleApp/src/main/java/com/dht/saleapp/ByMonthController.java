/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.dht.pojo.Thang;
import com.dht.service.JdbcUtils;
import com.dht.service.ProductService;
import com.dht.service.StaticService;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class ByMonthController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PieChart chartSoLuongHangBanDuoc;
    @FXML
    private PieChart ChartDoanhThu;
    @FXML
    private ComboBox<Thang> cbThang;
    @FXML
    private Text txtTongDoanhThu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.cbThang.setItems(FXCollections.observableList(thang()));

        txtTongDoanhThu.setText("0 VNĐ");
    }

//    public List<PieChart.Data> getListDataDoanhThuSanPham(int month) {
//        List<PieChart.Data> list = new ArrayList<>();
//        try {
//            Connection conn = JdbcUtils.getConn();
//            StaticService ss = new StaticService(conn);
//            ProductService hhs = new ProductService(conn);
//            for (int id : ss.getDistinctIDHangHoaByMonth(month)) {
//                list.add(new PieChart.Data(hhs.getProById(id).getName(), Double.valueOf(ss.staticDoanhThuSanPhamByMonth(month, id).toString())));
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ByMonthController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
    public List<PieChart.Data> getDataSoLuong(int month) {
        List<PieChart.Data> list = new ArrayList<>();
        try {
            Connection conn = JdbcUtils.getConn();
            StaticService ss = new StaticService(conn);
            ProductService hhs = new ProductService(conn);
            for (int id : ss.getDistinctIDHangHoaByMonth(month)) {
                list.add(new PieChart.Data(hhs.getProById(id).getName(), ss.staticByMonth(month, id)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ByMonthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void TongDoanhThu(int month) {
        try {
            Connection conn = JdbcUtils.getConn();
            StaticService ss = new StaticService(conn);
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            txtTongDoanhThu.setText(format.format(ss.staticTongDoanhThuByMonth(month)));
            txtTongDoanhThu.setStyle("-fx-font-size:18px; -fx-font-weight: bold");

        } catch (SQLException ex) {
            Logger.getLogger(ByMonthController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Thang> thang() {
        List<Thang> listM = new ArrayList<>();
        Thang m1 = new Thang();
        m1.setName("Thang 1");
        m1.setValue(1);
        Thang m2 = new Thang();
        m2.setName("Thang 2");
        m2.setValue(2);
        Thang m3 = new Thang();
        m3.setName("Thang 3");
        m3.setValue(3);
        Thang m4 = new Thang();
        m4.setName("Thang 4");
        m4.setValue(4);
        Thang m5 = new Thang();
        m5.setName("Thang 5");
        m5.setValue(5);
        Thang m6 = new Thang();
        m6.setName("Thang 6");
        m6.setValue(6);
        Thang m7 = new Thang();
        m7.setName("Thang 7");
        m7.setValue(7);
        Thang m8 = new Thang();
        m8.setName("Thang 8");
        m8.setValue(8);
        Thang m9 = new Thang();
        m9.setName("Thang 9");
        m9.setValue(9);
        Thang m10 = new Thang();
        m10.setName("Thang 10");
        m10.setValue(10);
        Thang m11 = new Thang();
        m11.setName("Thang 11");
        m11.setValue(11);
        Thang m12 = new Thang();
        m12.setName("Thang 12");
        m12.setValue(12);
        listM.add(m1);
        listM.add(m2);
        listM.add(m3);
        listM.add(m4);
        listM.add(m5);
        listM.add(m6);
        listM.add(m7);
        listM.add(m8);
        listM.add(m9);
        listM.add(m10);
        listM.add(m11);
        listM.add(m12);
        return listM;
    }

    @FXML
    private void changeThang(ActionEvent event) {
        System.out.println(getDataSoLuong(this.cbThang.getSelectionModel().getSelectedItem().getValue()));
        int month = this.cbThang.getSelectionModel().getSelectedItem().getValue();

        if (getDataSoLuong(month).size() <= 0) {
            chartSoLuongHangBanDuoc.setTitle("Không có dữ liệu số lượng sản phấm bán ra trong tháng " + month);
            ObservableList<PieChart.Data> listSL = FXCollections.observableArrayList(getDataSoLuong(month));
            chartSoLuongHangBanDuoc.setData(listSL);
            
            txtTongDoanhThu.setText("0 VNĐ");
//            ChartDoanhThu.setTitle("Không có dữ liệu doanh thu tháng  " + month);
//            ObservableList<PieChart.Data> ol = FXCollections.observableArrayList(getListDataDoanhThuSanPham(month));
//           ChartDoanhThu.setData(ol);
//            try {
//                TongDoanhThu(month);
//            } catch (NullPointerException e) {
//                txtTongDoanhThu.setText("0 VNĐ");
//            }

        } else {
            chartSoLuongHangBanDuoc.setTitle("Thống Kê Số Lượng Sản Phẩm Bán Ra Tháng " + month);
            ObservableList<PieChart.Data> ol2 = FXCollections.observableArrayList(getDataSoLuong(month));
            chartSoLuongHangBanDuoc.setData(ol2);
            for (PieChart.Data data : chartSoLuongHangBanDuoc.getData()) {
                data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue());
                
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        Utils.getBox(data.getName(), Alert.AlertType.INFORMATION).show();
                    }

                });
            }
//            ChartDoanhThu.setTitle("Thống Kê Doanh Thu Tháng " + month);
//            ObservableList<PieChart.Data> ol = FXCollections.observableArrayList(getListDataDoanhThuSanPham(month));
//            ChartDoanhThu.setData(ol);
//            for (PieChart.Data data : ChartDoanhThu.getData()) {
//                data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue() + "VNĐ");
//                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        Utils.getBox(data.getName(), Alert.AlertType.INFORMATION).show();
//                    }
//                });
//            }
            TongDoanhThu(month);
        }
    }

}
