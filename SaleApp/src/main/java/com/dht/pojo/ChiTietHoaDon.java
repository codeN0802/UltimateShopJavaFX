/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.pojo;

import java.math.BigDecimal;

/**
 *
 * @author HUU NGHI
 */
public class ChiTietHoaDon {

    /**
     * @return the numx
     */
    public int getNumx() {
        return numx;
    }

    /**
     * @param numx the numx to set
     */
    public void setNumx(int numx) {
        this.numx = numx;
    }

    /**
     * @return the product_id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the order_id
     */
    public int getOrder_id() {
        return order_id;
    }

    /**
     * @param order_id the order_id to set
     */
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    /**
     * @return the user_id
     */
   

    /**
     * @return the unit_price
     */
    public BigDecimal getUnit_price() {
        return unit_price;
    }

    /**
     * @param unit_price the unit_price to set
     */
    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

  
    private int id;
    private int order_id;
    private int product_id;
    private BigDecimal unit_price;
    private int numx;
}
