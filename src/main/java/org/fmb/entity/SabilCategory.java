/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "sabilcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SabilCategory.findAll", query = "SELECT s FROM SabilCategory s"),
    @NamedQuery(name = "SabilCategory.findBySabilCategoryNo", query = "SELECT s FROM SabilCategory s WHERE s.sabilCategoryNo = :sabilCategoryNo"),
    @NamedQuery(name = "SabilCategory.findByParentNo", query = "SELECT s FROM SabilCategory s WHERE s.parentNo = :parentNo"),
    @NamedQuery(name = "SabilCategory.findByCategoryname", query = "SELECT s FROM SabilCategory s WHERE s.categoryname = :categoryname"),
    @NamedQuery(name = "SabilCategory.findByAmount", query = "SELECT s FROM SabilCategory s WHERE s.amount = :amount")})
public class SabilCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sabilCategoryNo")
    private Long sabilCategoryNo;
    @Column(name = "parentNo")
    private BigInteger parentNo;
    @Size(max = 45)
    @Column(name = "categoryname")
    private String categoryname;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount = 0.00;
    //@OneToMany(mappedBy = "sabilCategoryNo")
    //private List<Sabil> sabilList;

    @Transient
    private DecimalFormat format = new DecimalFormat("#,##0.000");
    
    public SabilCategory() {
    }

    public SabilCategory(Long sabilCategoryNo) {
        this.sabilCategoryNo = sabilCategoryNo;
    }

    public Long getSabilCategoryNo() {
        return sabilCategoryNo;
    }

    public void setSabilCategoryNo(Long sabilCategoryNo) {
        this.sabilCategoryNo = sabilCategoryNo;
    }

    public BigInteger getParentNo() {
        return parentNo;
    }

    public void setParentNo(BigInteger parentNo) {
        this.parentNo = parentNo;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Transient
    public double getAmountYearly(){
        return getAmount()*12;
    }
    
    /*@XmlTransient
    public List<Sabil> getSabilList() {
        return sabilList;
    }

    public void setSabilList(List<Sabil> sabilList) {
        this.sabilList = sabilList;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sabilCategoryNo != null ? sabilCategoryNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SabilCategory)) {
            return false;
        }
        SabilCategory other = (SabilCategory) object;
        if ((this.sabilCategoryNo == null && other.sabilCategoryNo != null) || (this.sabilCategoryNo != null && !this.sabilCategoryNo.equals(other.sabilCategoryNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(getAmount()==null){
            return getCategoryname();
        }
        return getCategoryname() + " [BD " + format.format(getAmount()) + "/month]";
    }
    
}
