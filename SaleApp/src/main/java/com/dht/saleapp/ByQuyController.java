/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

import com.dht.pojo.Quy;
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
public class ByQuyController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private PieChart chartSoLuongHangBanDuoc;
      @FXML
    private Text txtTongDoanhThu;
    @FXML
    private ComboBox<Quy> cbQuy;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.cbQuy.setItems(FXCollections.observableList(quy()));
        txtTongDoanhThu.setText("0 VNĐ");
    }    
    public List<Quy> quy() {
        List<Quy> listQ = new ArrayList<>();
        Quy q1 = new Quy();
        q1.setName("Quý 1");
        q1.setMonthTo(1);
        q1.setMonthFrom(3);
        Quy q2 = new Quy();
        q2.setName("Quý 2");
        q2.setMonthTo(4);
        q2.setMonthFrom(6);
        Quy q3 = new Quy();
        q3.setName("Quý 3");
        q3.setMonthTo(7);
        q3.setMonthFrom(9);
        Quy q4 = new Quy();
        q4.setName("Quý 4");
        q4.setMonthTo(10);
        q4.setMonthFrom(12);
        listQ.add(q1);
        listQ.add(q2);
        listQ.add(q3);
        listQ.add(q4);
        return listQ;
    }
    public void TongDoanhThu(Quy quy) {
        try {
            Connection conn = JdbcUtils.getConn();
            StaticService ss = new StaticService(conn);
            Locale locale = new Locale("vi", "VN");
             NumberFormat format = NumberFormat.getCurrencyInstance(locale);
              txtTongDoanhThu.setText(format.format(ss.staticTongDoanhThuByQuy(quy)) );
              txtTongDoanhThu.setStyle("-fx-font-size:18px; -fx-font-weight: bold");
            //txtTongDoanhThu.setText(ss.staticTongDoanhThuByQuy(quy).toString() + " VNĐ");

        } catch (SQLException ex) {
            Logger.getLogger(ByQuyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public List<PieChart.Data> getDataSoLuong(Quy quy) {
        List<PieChart.Data> list = new ArrayList<>();
        try {
            Connection conn = JdbcUtils.getConn();
            StaticService ss = new StaticService(conn);
            ProductService hhs = new ProductService(conn);
            for (int id : ss.getDistinctIDHangHoaByQuy(quy)) {
                list.add(new PieChart.Data(hhs.getProById(id).getName(), ss.staticByQuy(quy, id)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ByQuyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    @FXML
    private void changeQuy(ActionEvent event) {
        Quy quy = this.cbQuy.getSelectionModel().getSelectedItem();
        if (getDataSoLuong(quy).size() <= 0) {
            chartSoLuongHangBanDuoc.setTitle("Không có dữ liệu số lượng sản phấm bán ra trong  " + quy);
            ObservableList<PieChart.Data> listSL = FXCollections.observableArrayList(getDataSoLuong(quy));
            chartSoLuongHangBanDuoc.setData(listSL);
            txtTongDoanhThu.setText("0 VNĐ");
//            ChartDoanhThu.setTitle("Không có dữ liệu doanh thu   " + quy);
//            ObservableList<PieChart.Data> ol = FXCollections.observableArrayList(getListDataDoanhThuSanPham(quy));
//            ChartDoanhThu.setData(ol);
//            try {
//                TongDoanhThu(quy);
//            } catch (NullPointerException e) {
//                txtTongDoanhThu.setText("0 VNĐ");
//            }
        } else {
            chartSoLuongHangBanDuoc.setTitle("Thống Kê Số Lượng Sản Phẩm Bán Ra " + quy);
            ObservableList<PieChart.Data> listSL = FXCollections.observableArrayList(getDataSoLuong(quy));
            chartSoLuongHangBanDuoc.setData(listSL);

            for (PieChart.Data data : chartSoLuongHangBanDuoc.getData()) {
                data.nameProperty().set(data.getName() + " : " + (int) data.getPieValue());
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Utils.getBox(data.getName(), Alert.AlertType.INFORMATION).show();
                    }
                });
            }
//            ChartDoanhThu.setTitle("Thống Kê Doanh Thu  " + quy);
//            ObservableList<PieChart.Data> ol = FXCollections.observableArrayList(getListDataDoanhThuSanPham(quy));
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
            try {
                TongDoanhThu(quy);
            } catch (NullPointerException e) {
                txtTongDoanhThu.setText("0 VNĐ");
            }
        }
    }

}
