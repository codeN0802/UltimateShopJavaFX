/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.service;

import com.dht.pojo.Quy;
import java.math.BigDecimal;
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
public class StaticService {
    private Connection conn;

    public StaticService(Connection conn) {
        this.conn = conn;
    }
    public List<Integer> getDistinctIDHangHoaByMonth(int month) throws SQLException {
        if (month <= 0) {
            throw new SQLDataException();
        }
        List<Integer> listIDHangHoa = new ArrayList<>();
        String sql = "SELECT DISTINCT saledb.order_detail.product_id"
                + " AS product_id"
                + " FROM saledb.order_detail,saledb.sale_order "
                + "Where saledb.sale_order.id = saledb.order_detail.order_id"
                + " and month(saledb.sale_order.created_date)=? and year(saledb.sale_order.created_date)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, month);
        stm.setInt(2, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            listIDHangHoa.add(rs.getInt("product_id"));
        }
        return listIDHangHoa;

    }
//    public BigDecimal staticDoanhThuSanPhamByMonth(int month, int idHH) throws SQLException {
//        if (month <= 0 && idHH <= 0) {
//            throw new SQLDataException();
//        }
//        BigDecimal sum = new BigDecimal(0);
//        String sql = "SELECT SUM(saledb.sale_order.amount)"
//                + "  AS TongDoanThu FROM saledb.order_detail,saledb.sale_order"
//                + " where saledb.sale_order.id = saledb.order_detail.order_id"
//                + " and month(saledb.sale_order.created_date)=? and product_id=? and year(saledb.sale_order.created_date)=?;";
//        PreparedStatement stm = this.conn.prepareStatement(sql);
//        int year = java.time.LocalDate.now().getYear();
//        stm.setInt(1, month);
//        stm.setInt(2, idHH);
//        stm.setInt(3, year);
//        ResultSet rs = stm.executeQuery();
//        while (rs.next()) {
//            sum = rs.getBigDecimal("TongDoanThu");
//        }
//        return sum;
//    }
    public BigDecimal staticTongDoanhThuByMonth(int month) throws SQLException {
        if (month <= 0) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(saledb.sale_order.amount)"
                + "  AS TongDoanThu FROM saledb.sale_order"
                + " where month(saledb.sale_order.created_date)=? and year(saledb.sale_order.created_date)=?; ";
               
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, month);
        stm.setInt(2, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }
    public int staticByMonth(int month, int idHH) throws SQLException {
        if (month <= 0 && idHH <= 0) {
            throw new SQLDataException();
        }
        int count = 0;
        String sql = "SELECT SUM(saledb.order_detail.num)  AS tong"
                + " FROM saledb.order_detail,saledb.sale_order"
                + " where saledb.sale_order.id = saledb.order_detail.order_id"
                + " and month(saledb.sale_order.created_date)=? and product_id=? and year(saledb.sale_order.created_date)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, month);
        stm.setInt(2, idHH);
        stm.setInt(3, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            count = rs.getInt("tong");
        }
        return count;
    }
    // THONG KE QUY
    public List<Integer> getDistinctIDHangHoaByQuy(Quy quy) throws SQLException {
        if (quy == null) {
            throw new SQLDataException();
        }
        List<Integer> listIDHangHoa = new ArrayList<>();
        String sql = "SELECT DISTINCT saledb.order_detail.product_id"
                + "  AS product_id FROM saledb.order_detail,saledb.sale_order "
                + "where saledb.sale_order.id = saledb.order_detail.order_id "
                + "and month(saledb.sale_order.created_date) between ? and"
                + " ? and year(saledb.sale_order.created_date)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, quy.getMonthTo());
        stm.setInt(2, quy.getMonthFrom());
        stm.setInt(3, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            listIDHangHoa.add(rs.getInt("product_id"));
        }
        return listIDHangHoa;

    }
    public BigDecimal staticTongDoanhThuByQuy(Quy quy) throws SQLException {
        if (quy == null) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(saledb.sale_order.amount)"
                + "  AS TongDoanThu FROM saledb.sale_order"
                + " where month(saledb.sale_order.created_date) between ? and ? and year(saledb.sale_order.created_date)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, quy.getMonthTo());
        stm.setInt(2, quy.getMonthFrom());
        stm.setInt(3, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }
    public int staticByQuy(Quy quy, int idHH) throws SQLException {
        if (quy == null && idHH <= 0) {
            throw new SQLDataException();
        }
        int count = 0;
        String sql = "SELECT SUM(saledb.order_detail.num)"
                + "  AS tong FROM saledb.order_detail,saledb.sale_order "
                + "where saledb.sale_order.id = saledb.order_detail.order_id "
                + "and month(saledb.sale_order.created_date) between ? and"
                + " ? and product_id=? and year(saledb.sale_order.created_date)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        int year = java.time.LocalDate.now().getYear();
        stm.setInt(1, quy.getMonthTo());
        stm.setInt(2, quy.getMonthFrom());
        stm.setInt(3, idHH);
        stm.setInt(4, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            count = rs.getInt("tong");
        }
        return count;
    }
    // THONG KE NAM
    public List<Integer> getDistinctIDHangHoaByYear(int year) throws SQLException {
        if (year <= 0) {
            throw new SQLDataException();
        }
        List<Integer> listIDHangHoa = new ArrayList<>();
        String sql = "SELECT DISTINCT saledb.order_detail.product_id"
                + " AS product_id"
                + " FROM saledb.order_detail,saledb.sale_order "
                + "Where saledb.sale_order.id = saledb.order_detail.order_id"
                + " and year(saledb.sale_order.created_date)=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            listIDHangHoa.add(rs.getInt("product_id"));
        }
        return listIDHangHoa;

    }
    public BigDecimal staticTongDoanhThuByYear(int year) throws SQLException {
        if (year <= 0) {
            throw new SQLDataException();
        }
        BigDecimal sum = new BigDecimal(0);
        String sql = "SELECT SUM(saledb.sale_order.amount)"
                + "  AS TongDoanThu FROM saledb.sale_order"
                + " where  year(saledb.sale_order.created_date)=?";
               
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, year);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            sum = rs.getBigDecimal("TongDoanThu");
        }
        return sum;
    }
    public int staticByYear(int year, int idHH) throws SQLException {
        if (year <= 0 && idHH <= 0) {
            throw new SQLDataException();
        }
        int count = 0;
        String sql = "SELECT SUM(saledb.order_detail.num) AS tong "
                + "from saledb.order_detail,saledb.sale_order "
                + "where saledb.sale_order.id = saledb.order_detail.order_id "
                + "and  year(saledb.sale_order.created_date)=? "
                + "and product_id=?;";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, year);
        stm.setInt(2, idHH);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            count = rs.getInt("tong");
        }
        return count;
    }

}
