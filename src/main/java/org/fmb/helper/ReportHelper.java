/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.helper;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Burhani152
 */
public class ReportHelper implements Serializable{
    
    private Date date;
    private Integer hijriYear;
    private Integer year;

    private Double debit;
    private Double credit;
    private Double balance;
    
    private String description;
    
    public ReportHelper(){
        
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the hijriYear
     */
    public Integer getHijriYear() {
        return hijriYear;
    }

    /**
     * @param hijriYear the hijriYear to set
     */
    public void setHijriYear(Integer hijriYear) {
        this.hijriYear = hijriYear;
    }

    /**
     * @return the year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return the debit
     */
    public Double getDebit() {
        return debit;
    }

    /**
     * @param debit the debit to set
     */
    public void setDebit(Double debit) {
        this.debit = debit;
    }

    /**
     * @return the credit
     */
    public Double getCredit() {
        return credit;
    }

    /**
     * @param credit the credit to set
     */
    public void setCredit(Double credit) {
        this.credit = credit;
    }

    /**
     * @return the balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(Double balance) {
        this.balance = balance;
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
}
