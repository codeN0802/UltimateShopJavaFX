/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.service;

import com.dht.pojo.Product;
import com.dht.saleapp.ProductController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class ProductService {

    private Connection conn;

    public ProductService(Connection conn) {

        this.conn = conn;
    }

    public List<Product> getPros() throws SQLException {
        Statement stm = this.conn.createStatement();
        ResultSet r = stm.executeQuery("SELECT * FROM product");

        List<Product> re = new ArrayList<>();
        while (r.next()) {
            Product p = new Product();
            p.setId(r.getInt("id"));
            p.setName(r.getString("name"));
            //p.setDescription(r.getString("description"));
            p.setPrice(r.getBigDecimal("price"));
            p.setCategory_id(r.getInt("category_id"));
            p.setImage(r.getString("image"));
            p.setSoLuong(r.getInt("soLuong"));

            re.add(p);
        }
        return re;
    }

    public List<Product> getProducts(String kw) throws SQLException {
        if (kw == null) {
            throw new SQLDataException();
        }

        String sql = "SELECT * FROM product WHERE name like concat('%', ?, '%') ORDER BY id DESC";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kw);

        ResultSet rs = stm.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            //p.setDescription(rs.getString("description"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setCategory_id(rs.getInt("category_id"));
            p.setImage(rs.getString("image"));
            p.setSoLuong(rs.getInt("soLuong"));

            products.add(p);
        }
        return products;
    }

    public boolean addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO product(name, price, category_id, image, soLuong) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, p.getName());
        stm.setBigDecimal(2, p.getPrice());
        stm.setInt(3, p.getCategory_id());
        stm.setString(4, p.getImage());
        stm.setInt(5, p.getSoLuong());

        int row = stm.executeUpdate();

        return row > 0;
    }

    public boolean deleleProduct(int productId) throws SQLException {
        String sql = "DELETE FROM product WHERE id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, productId);

        int row = stm.executeUpdate();

        return row > 0;
    }

    public boolean updateProduct(Product hh) throws SQLException {

        String sql = "UPDATE  product SET name = ?, price = ?, category_id = ?, image = ?, soLuong = ? WHERE id = ? ";

        PreparedStatement stm = this.conn.prepareStatement(sql);

        stm.setString(1, hh.getName());
        stm.setBigDecimal(2, hh.getPrice());
        stm.setInt(3, hh.getCategory_id());
        stm.setString(4, hh.getImage());
        stm.setInt(5, hh.getSoLuong());
        stm.setInt(6, hh.getId());
        int row = stm.executeUpdate();

        return row > 0;
    }

    public Product getProById(int id) throws SQLException {
        String sql = "SELECT * FROM product WHERE id=?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, id);

        ResultSet rs = stm.executeQuery();
        Product p = null;
        while (rs.next()) {
            p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            //p.setDescription(rs.getString("description"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setCategory_id(rs.getInt("category_id"));
            p.setImage(rs.getString("image"));
            p.setSoLuong(rs.getInt("soLuong"));

        }

        return p;
    }

    public List<Product> getListHangHoaConTrongKho(String kw) throws SQLException {
        if (kw == null) {
            throw new SQLDataException();
        }
        String sql = "SELECT * FROM product WHERE name like concat('%', ?, '%')";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("soLuong") > 0) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                //p.setDescription(rs.getString("description"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setCategory_id(rs.getInt("category_id"));
                p.setImage(rs.getString("image"));
                p.setSoLuong(rs.getInt("soLuong"));

                products.add(p);
            }

        }
        return products;
    }
    public boolean updateSoLuongHangHoa(int idHangHoa, int SoLuong) throws SQLException {
        if (idHangHoa <= 0 || SoLuong < 0) {
            throw new SQLDataException();
        }
        String sql = "UPDATE product SET soLuong = ? WHERE (id =?)";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, SoLuong);
        stm.setInt(2, idHangHoa);
        int row = stm.executeUpdate();
        return row > 0;
    }
}
