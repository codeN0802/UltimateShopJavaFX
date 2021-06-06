/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.service;
import com.dht.pojo.Product;
import com.dht.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author HUU NGHI
 */
public class UserService {
    private Connection conn;
    public UserService (Connection conn){
        this.conn = conn;
    }
    
    public boolean addUser(User p) throws SQLException {
        String sql = "INSERT INTO user(first_name, last_name, email, username, password , room) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, p.getFirst_name());
        stm.setString(2, p.getLast_name());
        stm.setString(3, p.getEmail());
        stm.setString(4, p.getUsername());
        stm.setString(5, p.getPassword());
        stm.setString(6, p.getRoom());
        
        
        int row = stm.executeUpdate();
        
        return row > 0;
}
    public List<User> getUser() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM user");
        
        List<User> re = new ArrayList<>();
        while (r.next()) {
            User c = new User();
            c.setId(r.getInt("id"));
            c.setLast_name(r.getString("last_name"));
            c.setFirst_name(r.getString("first_name"));
             c.setEmail(r.getString("email"));
            c.setUsername(r.getString("username"));
            c.setPassword(r.getString("password"));
            c.setRoom(r.getString("room"));
              
           
            
            re.add(c);
        }
        return re;
    }
    public List<User> getUserByName(String kw) throws SQLException {
        if (kw == null)
            throw new SQLDataException();
        
        String sql = "SELECT * FROM user WHERE last_name like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<User> u = new ArrayList<>();
        while (rs.next()) {
             User c = new User();
            c.setId(rs.getInt("id"));
            c.setLast_name(rs.getString("last_name"));
            c.setFirst_name(rs.getString("first_name"));
             c.setEmail(rs.getString("email"));
            c.setUsername(rs.getString("username"));
            c.setPassword(rs.getString("password"));
            c.setRoom(rs.getString("room"));
            u.add(c);
        }
        return u;
    }
    public boolean deleleUser(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        int row = stm.executeUpdate();
        
        return row > 0;
    }
    public List<User> getUserByRoom(String kw) throws SQLException {
        if (kw == null)
            throw new SQLDataException();
        
        String sql = "SELECT * FROM user WHERE room like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kw);
        
        ResultSet rs = stm.executeQuery();
        List<User> u = new ArrayList<>();
        while (rs.next()) {
             User c = new User();
            c.setId(rs.getInt("id"));
            c.setLast_name(rs.getString("last_name"));
            c.setFirst_name(rs.getString("first_name"));
             c.setEmail(rs.getString("email"));
            c.setUsername(rs.getString("username"));
            c.setPassword(rs.getString("password"));
            c.setRoom(rs.getString("room"));
            u.add(c);
        }
        return u;
    }
     public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        User c = null;
        while (rs.next()) {
            c = new User();
            c.setId(rs.getInt("id"));
            c.setLast_name(rs.getString("last_name"));
            c.setFirst_name(rs.getString("first_name"));
             c.setEmail(rs.getString("email"));
            c.setUsername(rs.getString("username"));
            c.setPassword(rs.getString("password"));
            c.setRoom(rs.getString("room"));       
        }
        
        return c;
    }
}
