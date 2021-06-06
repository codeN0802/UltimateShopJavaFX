/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.saleapp;

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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HUU NGHI
 */
public class ByYearController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PieChart chartSoLuongHangBanDuoc;
    @FXML
    private Text txtTongDoanhThu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int year = java.time.LocalDate.now().getYear();
        chartSoLuongHangBanDuoc.setTitle("Thống Kê Số Lượng Sản Phẩm Bán Ra Năm " + year);
        ObservableList<PieChart.Data> listSL = FXCollections.observableArrayList(getDataSoLuong(year));
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
         try {
            TongDoanhThu(year);
        } catch (NullPointerException e) {
            txtTongDoanhThu.setText("0 VNĐ");
        }
    }

    public void TongDoanhThu(int year) {
        try {
            Connection conn = JdbcUtils.getConn();
            StaticService ss = new StaticService(conn);Locale locale = new Locale("vi", "VN");
             NumberFormat format = NumberFormat.getCurrencyInstance(locale);
             txtTongDoanhThu.setText(format.format(ss.staticTongDoanhThuByYear(year)) );
              txtTongDoanhThu.setStyle("-fx-font-size:18px; -fx-font-weight: bold");
            //txtTongDoanhThu.setText(ss.staticTongDoanhThuByYear(year).toString() + " VNĐ");

        } catch (SQLException ex) {
            Logger.getLogger(ByMonthController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     public List<PieChart.Data> getDataSoLuong(int year) {
        List<PieChart.Data> list = new ArrayList<>();
        try {
            Connection conn = JdbcUtils.getConn();
            StaticService ss = new StaticService(conn);
            ProductService hhs = new ProductService(conn);
            for (int id : ss.getDistinctIDHangHoaByYear(year)) {
                list.add(new PieChart.Data(hhs.getProById(id).getName(), ss.staticByYear(year, id)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ByMonthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
