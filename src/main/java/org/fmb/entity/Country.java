/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c"),
    @NamedQuery(name = "Country.findByCountryNo", query = "SELECT c FROM Country c WHERE c.countryNo = :countryNo"),
    @NamedQuery(name = "Country.findByCountryName", query = "SELECT c FROM Country c WHERE c.countryName = :countryName"),
    @NamedQuery(name = "Country.findByCountrySym", query = "SELECT c FROM Country c WHERE c.countrySym = :countrySym"),
    @NamedQuery(name = "Country.findByCurrencyName", query = "SELECT c FROM Country c WHERE c.currencyName = :currencyName"),
    @NamedQuery(name = "Country.findByCurrencySym", query = "SELECT c FROM Country c WHERE c.currencySym = :currencySym"),
    @NamedQuery(name = "Country.findByDefaultCountry", query = "SELECT c FROM Country c WHERE c.defaultCountry = :defaultCountry")})
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "countryNo")
    private Integer countryNo;
    @Size(max = 255)
    @Column(name = "countryName")
    private String countryName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "countrySym")
    private String countrySym;
    @Size(max = 45)
    @Column(name = "currencyName")
    private String currencyName;
    @Size(max = 45)
    @Column(name = "currencySym")
    private String currencySym;
    @Column(name = "defaultCountry")
    private Boolean defaultCountry;

    public Country() {
    }

    public Country(Integer countryNo) {
        this.countryNo = countryNo;
    }

    public Country(Integer countryNo, String countrySym) {
        this.countryNo = countryNo;
        this.countrySym = countrySym;
    }

    public Integer getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(Integer countryNo) {
        this.countryNo = countryNo;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountrySym() {
        return countrySym;
    }

    public void setCountrySym(String countrySym) {
        this.countrySym = countrySym;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySym() {
        return currencySym;
    }

    public void setCurrencySym(String currencySym) {
        this.currencySym = currencySym;
    }

    public Boolean getDefaultCountry() {
        return defaultCountry;
    }

    public void setDefaultCountry(Boolean defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryNo != null ? countryNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.countryNo == null && other.countryNo != null) || (this.countryNo != null && !this.countryNo.equals(other.countryNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jms.entity.Country[ countryNo=" + countryNo + " ]";
    }
    
}
