/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "sabil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sabil.findAll", query = "SELECT s FROM Sabil s"),
    @NamedQuery(name = "Sabil.findBySabilNo", query = "SELECT s FROM Sabil s WHERE s.sabilNo = :sabilNo"),
    @NamedQuery(name = "Sabil.findByOldSabilNo", query = "SELECT s FROM Sabil s WHERE s.oldSabilNo = :oldSabilNo"),
    @NamedQuery(name = "Sabil.findByItsNo", query = "SELECT s FROM Sabil s WHERE s.itsNo.itsNo=?1"),
    @NamedQuery(name = "Sabil.findByItsNoBySearchCriteria", query = "SELECT OBJECT(s) FROM Sabil s WHERE s.itsNo.itsNo LIKE ?1 "
            + "OR s.itsNo.cpr LIKE ?1 OR s.itsNo.firstName LIKE ?1 OR s.itsNo.secondName LIKE ?1 "
            + "OR s.itsNo.surname LIKE ?1"),
    @NamedQuery(name = "Sabil.findByItsNoBySabilCategoryNo", query = "SELECT s FROM Sabil s WHERE (s.itsNo.itsNo LIKE ?1 "
            + "OR s.itsNo.cpr LIKE ?1 OR s.itsNo.firstName LIKE ?1 OR s.itsNo.secondName LIKE ?1 "
            + "OR s.itsNo.surname LIKE ?1) AND s.sabilCategoryNo.sabilCategoryNo = ?2"),
    @NamedQuery(name = "Sabil.findByBusinessNo", query = "SELECT s FROM Sabil s WHERE s.businessNo.businessNo=?1"),
    @NamedQuery(name = "Sabil.findByBusinessNoBySearchCriteria", query = "SELECT s FROM Sabil s WHERE TRIM(FROM s.businessNo.businessNo) LIKE ?1 "
            + "OR s.businessNo.crNo LIKE ?1 OR s.businessNo.businessName LIKE ?1"),
    @NamedQuery(name = "Sabil.findByBusinessNoBySabilCategoryNo", query = "SELECT s FROM Sabil s WHERE (TRIM(FROM s.businessNo.businessNo) LIKE ?1 "
            + "OR s.businessNo.crNo LIKE ?1 OR s.businessNo.businessName LIKE ?1) AND s.sabilCategoryNo.sabilCategoryNo = ?2")
})
public class Sabil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sabilNo")
    private Long sabilNo;
    @Size(max = 45)
    @Column(name = "oldSabilNo")
    private String oldSabilNo;
    @JoinColumn(name = "sabilCategoryNo", referencedColumnName = "sabilCategoryNo")
    @ManyToOne
    private SabilCategory sabilCategoryNo;
    @JoinColumn(name = "itsNo", referencedColumnName = "itsNo")
    @ManyToOne
    private ItsMaster itsNo;
    @JoinColumn(name = "businessNo", referencedColumnName = "businessNo")
    @ManyToOne
    private Business businessNo;
    //@OneToMany(mappedBy = "sabilNo")
    //private List<Ledger> ledgerList;
    @Column(name = "creationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();
    @Transient
    private double totalInvoiced;
    @Transient
    private double totalPaid;
    @Transient
    private double totalBalance;

    public Sabil() {
    }

    public Sabil(Long sabilNo) {
        this.sabilNo = sabilNo;
    }

    public Long getSabilNo() {
        return sabilNo;
    }

    public void setSabilNo(Long sabilNo) {
        this.sabilNo = sabilNo;
    }

    public String getOldSabilNo() {
        return oldSabilNo;
    }

    public void setOldSabilNo(String oldSabilNo) {
        this.oldSabilNo = oldSabilNo;
    }

    public SabilCategory getSabilCategoryNo() {
        return sabilCategoryNo;
    }

    public void setSabilCategoryNo(SabilCategory sabilCategoryNo) {
        this.sabilCategoryNo = sabilCategoryNo;
    }

    public ItsMaster getItsNo() {
        return itsNo;
    }

    public void setItsNo(ItsMaster itsNo) {
        this.itsNo = itsNo;
    }

    public Business getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(Business businessNo) {
        this.businessNo = businessNo;
    }

    /*@XmlTransient
    public List<Ledger> getLedgerList() {
        return ledgerList;
    }

    public void setLedgerList(List<Ledger> ledgerList) {
        this.ledgerList = ledgerList;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sabilNo != null ? sabilNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sabil)) {
            return false;
        }
        Sabil other = (Sabil) object;
        if ((this.sabilNo == null && other.sabilNo != null) || (this.sabilNo != null && !this.sabilNo.equals(other.sabilNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jms.entity.Sabil[ sabilNo=" + sabilNo + " ]";
    }

    public Integer getItsNumber() {
        if (getItsNo() == null) {
            return null;
        }
        return getItsNo().getItsNo();
    }

    public Long getBusinessNumber() {
        if (getBusinessNo() == null) {
            return null;
        }
        return getBusinessNo().getBusinessNo();
    }

    /**
     * @return the totalInvoiced
     */
    public double getTotalInvoiced() {
        return totalInvoiced;
    }

    /**
     * @param totalInvoiced the totalInvoiced to set
     */
    public void setTotalInvoiced(double totalInvoiced) {
        this.totalInvoiced = totalInvoiced;
    }

    /**
     * @return the totalPaid
     */
    public double getTotalPaid() {
        return totalPaid;
    }

    /**
     * @param totalPaid the totalPaid to set
     */
    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    /**
     * @return the totalBalance
     */
    public double getTotalBalance() {
        return totalBalance;
    }

    /**
     * @param totalBalance the totalBalance to set
     */
    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
