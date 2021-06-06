/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.service;

import com.dht.pojo.ChiTietHoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HUU NGHI
 */
public class ChiTietHoaDonService {
    private Connection conn;

    public ChiTietHoaDonService(Connection conn) {
        this.conn = conn;
    }
     public List<ChiTietHoaDon> getListChiTietById(int idHoaDon) throws SQLException {
        if (idHoaDon <= 0) {
            throw new SQLDataException();
        }
        String sql = "SELECT * FROM order_detail where order_id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, idHoaDon);
        ResultSet rs = stm.executeQuery();
        List<ChiTietHoaDon> Bill = new ArrayList<>();
        while (rs.next()) {
            ChiTietHoaDon b = new ChiTietHoaDon();
            b.setOrder_id(rs.getInt("order_id"));
            b.setProduct_id(rs.getInt("product_id"));
            b.setNumx(rs.getInt("num"));
            b.setUnit_price(rs.getBigDecimal("unit_price"));
            Bill.add(b);
        }
        return Bill;
    }
      public boolean AddChiTietHoaDon(ChiTietHoaDon b) throws SQLException {
        String sql = "INSERT INTO `order_detail` (`order_id`,"
                + " `num`,"
                + " `product_id`,"
                + " `unit_price`)"
                + " VALUES (?, ?, ?, ?);";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, b.getOrder_id());
        stm.setInt(2, b.getNumx());
        stm.setInt(3, b.getProduct_id());
        stm.setBigDecimal(4, b.getUnit_price());
        int row = stm.executeUpdate();
        return row > 0;
    }
}
