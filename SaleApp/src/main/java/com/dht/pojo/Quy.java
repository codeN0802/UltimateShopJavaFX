/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.pojo;

/**
 *
 * @author HUU NGHI
 */
public class Quy {

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the monthTo
     */
    public int getMonthTo() {
        return monthTo;
    }

    /**
     * @param monthTo the monthTo to set
     */
    public void setMonthTo(int monthTo) {
        this.monthTo = monthTo;
    }

    /**
     * @return the monthFrom
     */
    public int getMonthFrom() {
        return monthFrom;
    }

    /**
     * @param monthFrom the monthFrom to set
     */
    public void setMonthFrom(int monthFrom) {
        this.monthFrom = monthFrom;
    }
    private String name;
    private int monthTo;
    private int monthFrom;
     @Override
    public String toString() {
        return name;
    }
}
