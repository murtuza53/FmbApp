/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.helper;

import java.io.Serializable;

/**
 *
 * @author FatemaLaptop
 */
public class SabilReportHelper implements Serializable{
    
    private String id;
    private String name;
    private String description;
    private String lastInvoiced;
    private double sabilYearly;
    
    public SabilReportHelper(){
        
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the lastInvoiced
     */
    public String getLastInvoiced() {
        return lastInvoiced;
    }

    /**
     * @param lastInvoiced the lastInvoiced to set
     */
    public void setLastInvoiced(String lastInvoiced) {
        this.lastInvoiced = lastInvoiced;
    }

    /**
     * @return the sabilYearly
     */
    public double getSabilYearly() {
        return sabilYearly;
    }

    /**
     * @param sabilYearly the sabilYearly to set
     */
    public void setSabilYearly(double sabilYearly) {
        this.sabilYearly = sabilYearly;
    }
}
