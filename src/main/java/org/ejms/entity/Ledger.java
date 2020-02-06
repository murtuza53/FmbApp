/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "ledger")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ledger.findAll", query = "SELECT l FROM Ledger l"),
    @NamedQuery(name = "Ledger.findByAccountDocumentNo", query = "SELECT l FROM Ledger l WHERE l.accountDocumentNo.accountDocumentNo = :accountDocumentNo"),
    @NamedQuery(name = "Ledger.findByLedgerNo", query = "SELECT l FROM Ledger l WHERE l.ledgerNo = :ledgerNo"),
    @NamedQuery(name = "Ledger.findByTransDate", query = "SELECT l FROM Ledger l WHERE l.transDate = :transDate"),
    @NamedQuery(name = "Ledger.findByRefNo", query = "SELECT l FROM Ledger l WHERE l.refNo = :refNo"),
    @NamedQuery(name = "Ledger.findByAccountNo", 
            query = "SELECT l FROM Ledger l WHERE l.accountNo.accountNo LIKE :accountNo ORDER BY l.transDate DESC"),
    @NamedQuery(name = "Ledger.findByDebit", query = "SELECT l FROM Ledger l WHERE l.debit = :debit"),
    @NamedQuery(name = "Ledger.findByCredit", query = "SELECT l FROM Ledger l WHERE l.credit = :credit"),
    @NamedQuery(name = "Ledger.findByRate", query = "SELECT l FROM Ledger l WHERE l.rate = :rate"),
    @NamedQuery(name = "Ledger.findByFcDebit", query = "SELECT l FROM Ledger l WHERE l.fcDebit = :fcDebit"),
    @NamedQuery(name = "Ledger.findByFcCredit", query = "SELECT l FROM Ledger l WHERE l.fcCredit = :fcCredit"),
    @NamedQuery(name = "Ledger.findByDescription", query = "SELECT l FROM Ledger l WHERE l.description = :description")})
public class Ledger implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ledgerNo")
    private Long ledgerNo = 0l;
    @Column(name = "transDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;
    @Size(max = 45)
    @Column(name = "refNo")
    private String refNo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "debit")
    private Double debit = 0.0;
    @Column(name = "credit")
    private Double credit = 0.0;
    @Column(name = "rate")
    private Double rate = 1.0;
    @Column(name = "fcDebit")
    private Double fcDebit = 0.0;
    @Column(name = "fcCredit")
    private Double fcCredit = 0.0;
    @Size(max = 2555)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "sabilNo", referencedColumnName = "sabilNo")
    @ManyToOne(fetch=FetchType.LAZY)
    private Sabil sabilNo;
    @JoinColumn(name = "accountDocumentNo", referencedColumnName = "accountDocumentNo")
    @ManyToOne
    private AccountDocument accountDocumentNo;
    @JoinColumn(name = "accountNo", referencedColumnName = "accountNo")
    @ManyToOne
    private Account accountNo;

    public Ledger() {
    }

    public Ledger(Long ledgerNo) {
        this.ledgerNo = ledgerNo;
    }

    public Long getLedgerNo() {
        return ledgerNo;
    }

    public void setLedgerNo(Long ledgerNo) {
        this.ledgerNo = ledgerNo;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        if(debit<0){
            this.debit = 0.0;
        }
        
        this.debit = debit;
        
        if(debit > 0){
            this.credit = 0.0;
        }
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        if(credit<0){
            this.credit = 0.0;
        }
        
        this.credit = credit;
        
        if(credit > 0){
            this.debit = 0.0;
        }
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getFcDebit() {
        return fcDebit;
    }

    public void setFcDebit(Double fcDebit) {
        this.fcDebit = fcDebit;
    }

    public Double getFcCredit() {
        return fcCredit;
    }

    public void setFcCredit(Double fcCredit) {
        this.fcCredit = fcCredit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sabil getSabilNo() {
        return sabilNo;
    }

    public void setSabilNo(Sabil sabilNo) {
        this.sabilNo = sabilNo;
    }

    public AccountDocument getAccountDocumentNo() {
        return accountDocumentNo;
    }

    public void setAccountDocumentNo(AccountDocument accountDocumentNo) {
        this.accountDocumentNo = accountDocumentNo;
    }

    public Account getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Account accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ledgerNo != null ? ledgerNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ledger)) {
            return false;
        }
        Ledger other = (Ledger) object;
        if ((this.ledgerNo == null && other.ledgerNo != null) || (this.ledgerNo != null && !this.ledgerNo.equals(other.ledgerNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jms.entity.Ledger[ ledgerNo=" + ledgerNo + " ]";
    }
    
}
