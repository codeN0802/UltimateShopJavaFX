/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.service;

import com.dht.pojo.HoaDon;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
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
public class HoaDonService {
    private Connection conn;

    public HoaDonService(Connection conn) {
        this.conn = conn;
    }
    
    public List<HoaDon> getListHoaDon() throws SQLException {
        String sql = "SELECT * FROM sale_order;";
        PreparedStatement stm = this.conn.prepareStatement(sql);

        ResultSet rs = stm.executeQuery();
        List<HoaDon> Bills = new ArrayList<>();
        while (rs.next()) {
            HoaDon b = new HoaDon();
            b.setId(rs.getInt("id"));
            
            b.setUser_id(rs.getInt("user_id"));
            b.setCreated_date(rs.getDate("created_date"));
            b.setAmount(rs.getBigDecimal("amount"));
           
            
            Bills.add(b);
        }
        return Bills;}
    public HoaDon CreateHoaDon(HoaDon hd) throws SQLException {
         int row = 0;
         String sql = "INSERT INTO `sale_order` (`created_date`,"
                    + " `amount`,"
                    + " `user_id`) VALUES (?,?,?);";
          PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setDate(1, hd.getCreated_date());
            stm.setBigDecimal(2, hd.getAmount());
           
            stm.setInt(3, hd.getUser_id());
            row = stm.executeUpdate();
            HoaDon hdNew = null;
            if (row > 0) {
            String getHoaDonNew = "SELECT * FROM sale_order ORDER BY id DESC LIMIT 1;";
            PreparedStatement stmNew = this.conn.prepareStatement(getHoaDonNew);
            ResultSet rs = stmNew.executeQuery();
            while (rs.next()) {
                hdNew = new HoaDon();
                hdNew.setId(rs.getInt("id"));
                
                hdNew.setUser_id(rs.getInt("user_id"));
                hdNew.setCreated_date(rs.getDate("created_date"));
                hdNew.setAmount(rs.getBigDecimal("amount"));
                
            }
            return hdNew;
        } else {
            return hdNew;
        }
    }
     public boolean UpdateListHoaDon(HoaDon hd) throws SQLException {
        String sql = "UPDATE sale_order SET amount = ?, created_date = ?, user_id = ? WHERE id = ?";
               
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setBigDecimal(1, hd.getAmount());
        stm.setDate(2, hd.getCreated_date());
        stm.setInt(3,hd.getUser_id());
       
        stm.setInt(4, hd.getId());
        int row = stm.executeUpdate();
        return row > 0;
    }
    public List<HoaDon> getListHoaDonByDate(Date kw) throws SQLException {
        if (kw == null) {
            throw new SQLDataException();
        }
        String sql = "SELECT * FROM sale_order where created_date=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setDate(1, kw);
        ResultSet rs = stm.executeQuery();
        List<HoaDon> Bills = new ArrayList<>();
        while (rs.next()) {
            HoaDon b = new HoaDon();
            b.setId(rs.getInt("id"));
           
            b.setUser_id(rs.getInt("user_id"));
            b.setCreated_date(rs.getDate("created_date"));
            b.setAmount(rs.getBigDecimal("amount"));
            
            Bills.add(b);
        }
        return Bills;
    }

    public boolean deleleHoaDon(int IdhoaDon) throws SQLException {
        if (IdhoaDon <= 0) {
            throw new SQLDataException();
        }
        String sql = "DELETE FROM sale_order WHERE id =?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, IdhoaDon);
        int row = stm.executeUpdate();
        return row > 0;
    }
    

    
}
