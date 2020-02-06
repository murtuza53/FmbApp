/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "paymentdetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentDetails.findAll", query = "SELECT p FROM PaymentDetails p"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsNo", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsNo = :paymentDetailsNo"),
    @NamedQuery(name = "PaymentDetails.findByAccountDocumentNo", query = "SELECT p FROM PaymentDetails p WHERE p.accountDocument.accountDocumentNo = :accountDocumentNo"),
    @NamedQuery(name = "PaymentDetails.findByPaymentType", query = "SELECT p FROM PaymentDetails p WHERE p.paymentType = :paymentType"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDate", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDate = :paymentDate"),
    @NamedQuery(name = "PaymentDetails.findByChequeName", query = "SELECT p FROM PaymentDetails p WHERE p.chequeName = :chequeName"),
    @NamedQuery(name = "PaymentDetails.findByBank", query = "SELECT p FROM PaymentDetails p WHERE p.bank = :bank")})
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "paymentDetailsNo")
    private Long paymentDetailsNo = 0l;
    @Size(max = 45)
    @Column(name = "paymentType")
    private String paymentType;
    @Column(name = "paymentDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;
    @Size(max = 45)
    @Column(name = "chequeName")
    private String chequeName;
    @Size(max = 45)
    @Column(name = "bank")
    private String bank;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "paymentDetailsNo")
    private AccountDocument accountDocument;

    public PaymentDetails() {
    }

    public PaymentDetails(Long paymentDetailsNo) {
        this.paymentDetailsNo = paymentDetailsNo;
    }

    public Long getPaymentDetailsNo() {
        return paymentDetailsNo;
    }

    public void setPaymentDetailsNo(Long paymentDetailsNo) {
        this.paymentDetailsNo = paymentDetailsNo;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getChequeName() {
        return chequeName;
    }

    public void setChequeName(String chequeName) {
        this.chequeName = chequeName;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @XmlTransient
    public AccountDocument getAccountDocument() {
        return accountDocument;
    }

    public void setAccountDocument(AccountDocument accountDocument) {
        this.accountDocument = accountDocument;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentDetailsNo != null ? paymentDetailsNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentDetails)) {
            return false;
        }
        PaymentDetails other = (PaymentDetails) object;
        if ((this.paymentDetailsNo == null && other.paymentDetailsNo != null) || (this.paymentDetailsNo != null && !this.paymentDetailsNo.equals(other.paymentDetailsNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jms.entity.PaymentDetails[ paymentDetailsNo=" + paymentDetailsNo + " ]";
    }
    
}
