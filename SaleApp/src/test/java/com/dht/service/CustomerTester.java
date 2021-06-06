/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.service;

import com.dht.pojo.Product;
import com.dht.pojo.User;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
/**
 *
 * @author HUU NGHI
 */
public class CustomerTester {
    private static Connection conn;
    
    @BeforeAll
    public static void setUpClass() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    @Test
    public void testWithKeyWord() {
        try {
            UserService s = new UserService(conn);
            List<User> users = s.getUserByName("iphone");
            
            users.forEach(p -> {
                Assertions.assertTrue(p.getLast_name().toLowerCase().contains("bbb"));
            });
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     @Test
    public void testUnknownWithKeyWord() {
        try {
            UserService s = new UserService(conn);
            List<User> users = s.getUserByName("43*&^&^GYGFUYGFUYGFHGD%$");
            
            Assertions.assertEquals(0, users.size());
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testException() {
        Assertions.assertThrows(SQLDataException.class, () -> {
            new UserService(conn).getUserByName(null);
        });
    }
    
    @Test
    public void testTimeout() {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            new  UserService(conn).getUserByName("");
        });
    }
    @Test
    @DisplayName("Kiem thu chuc nang them sp voi name=null")
    @Tag("critical")
    public void testAddProductNameNull() {
        try {
            User p = new User();
            p.setUsername(null);
            p.setEmail("ou@edu.vn");
            p.setFirst_name("ho");
            p.setLast_name("tenXXX");
            
            p.setPassword("456");

            
            
            UserService s = new UserService(conn);
            Assertions.assertFalse(s.addUser(p));
        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    @Test
    public void testAddProduct() {
        try {
            User p = new User();
            p.setUsername("Alo");
            p.setEmail("ou@edu.vn");
            p.setFirst_name("ho");
            p.setLast_name("tenXXX");
            p.setRoom("Admin");
            p.setPassword("456");

            
            
            UserService s = new UserService(conn);
            Assertions.assertTrue(s.addUser(p));
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvSource({"hox, tenx , ou@edu.vn , ty, tu"})
    public void testAddBatchProduct(String first_name, String last_name, String email, String username, String password) {
        try {
            User p = new User();
            p.setFirst_name(first_name);
            p.setLast_name(last_name);
            p.setEmail(email);
            p.setUsername(username);
            p.setPassword(password);
            
            UserService s = new UserService(conn);
            Assertions.assertTrue(s.addUser(p));
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
