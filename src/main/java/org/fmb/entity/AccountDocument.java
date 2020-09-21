/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "accountdocument")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountDocument.findAll", query = "SELECT a FROM AccountDocument a"),
    @NamedQuery(name = "AccountDocument.findByAccountDocumentNo", query = "SELECT a FROM AccountDocument a WHERE a.accountDocumentNo = :accountDocumentNo"),
    @NamedQuery(name = "AccountDocument.findByManualNo", query = "SELECT a FROM AccountDocument a WHERE a.manualNo = :manualNo"),
    @NamedQuery(name = "AccountDocument.findByDocDate", query = "SELECT a FROM AccountDocument a WHERE a.docDate = :docDate"),
    @NamedQuery(name = "AccountDocument.findByRefNo", query = "SELECT a FROM AccountDocument a WHERE a.refNo = :refNo"),
    @NamedQuery(name = "AccountDocument.findByDescription", query = "SELECT a FROM AccountDocument a WHERE a.description = :description"),
    @NamedQuery(name = "AccountDocument.findByBusinessNo", query = "SELECT a FROM AccountDocument a WHERE a.businessNo = :businessNo"),
    @NamedQuery(name="findAccountDocumentBySearchCriteria",
        query="SELECT OBJECT(o) FROM AccountDocument o WHERE (o.accountDocumentNo LIKE :criteria OR " +
        "o.refNo LIKE :criteria) AND (o.docDate>=:fromDate AND o.docDate<=:toDate AND o.accountDocumentNo LIKE :docType) "
        + "ORDER BY o.docDate DESC, o.accountDocumentNo DESC")})
public class AccountDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "accountDocumentNo")
    private String accountDocumentNo;
    @Size(max = 45)
    @Column(name = "manualNo")
    private String manualNo;
    @Column(name = "docDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docDate;
    @Size(max = 45)
    @Column(name = "refNo")
    private String refNo;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "businessNo", referencedColumnName = "businessNo")
    @ManyToOne
    private Business businessNo;
    @JoinColumn(name = "paymentDetailsNo", referencedColumnName = "paymentDetailsNo")
    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private PaymentDetails paymentDetailsNo;
    @JoinColumn(name = "userNo", referencedColumnName = "userNo")
    @ManyToOne
    private Users userNo;
    @JoinColumn(name = "itsNo", referencedColumnName = "itsNo")
    @ManyToOne
    private ItsMaster itsNo;
    @OneToMany(mappedBy = "accountDocumentNo", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<PaymentInvoice> paymentInvoiceList;
    @OneToMany(mappedBy = "accountDocumentNo", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Ledger> ledgerList;

    public static final String ACCOUNT_VOUCHER = "AV";
    public static final String JOURNAL_VOUCHER = "JV";
    public static final String PAYMENT_VOUCHER = "PV";
    public static final String SABIL_RECEIPT_VOUCHER = "SR";
    public static final String RECEIPT = "RCT";
    public static final String SALARY_SLIP = "SS";

    public AccountDocument() {
    }

    public AccountDocument(String accountDocumentNo) {
        this.accountDocumentNo = accountDocumentNo;
    }

    public String getAccountDocumentNo() {
        return accountDocumentNo;
    }

    public void setAccountDocumentNo(String accountDocumentNo) {
        this.accountDocumentNo = accountDocumentNo;
    }

    public String getManualNo() {
        return manualNo;
    }

    public void setManualNo(String manualNo) {
        this.manualNo = manualNo;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Business getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(Business businessNo) {
        this.businessNo = businessNo;
    }

    public PaymentDetails getPaymentDetailsNo() {
        return paymentDetailsNo;
    }

    public void setPaymentDetailsNo(PaymentDetails paymentDetailsNo) {
        this.paymentDetailsNo = paymentDetailsNo;
    }

    public Users getUserNo() {
        return userNo;
    }

    public void setUserNo(Users userNo) {
        this.userNo = userNo;
    }

    public ItsMaster getItsNo() {
        return itsNo;
    }

    public void setItsNo(ItsMaster itsNo) {
        this.itsNo = itsNo;
    }

    @XmlTransient
    public List<PaymentInvoice> getPaymentInvoiceList() {
        return paymentInvoiceList;
    }

    public void setPaymentInvoiceList(List<PaymentInvoice> paymentInvoiceList) {
        this.paymentInvoiceList = paymentInvoiceList;
    }

    @XmlTransient
    public List<Ledger> getLedgerList() {
        return ledgerList;
    }

    public void setLedgerList(List<Ledger> ledgerList) {
        this.ledgerList = ledgerList;
    }
    
    @XmlTransient
    public Double getTotalCredit(){
        if(getLedgerList()==null){
            return 0.0;
        }
        
        double total = 0;
        
        for(Ledger l: getLedgerList()){
            total = total + l.getCredit();
        }
        
        return total;
    }

    @XmlTransient
    public Double getTotalDebit(){
        if(getLedgerList()==null){
            return 0.0;
        }
        
        double total = 0;
        
        for(Ledger l: getLedgerList()){
            total = total + l.getDebit();
        }
        
        return total;
    }    
    
    @XmlTransient
    public Double getTotalFcCredit(){
        if(getLedgerList()==null){
            return 0.0;
        }
        
        double total = 0;
        
        for(Ledger l: getLedgerList()){
            total = total + l.getFcCredit();
        }
        
        return total;
    }

    @XmlTransient
    public Double getTotalFcDebit(){
        if(getLedgerList()==null){
            return 0.0;
        }
        
        double total = 0;
        
        for(Ledger l: getLedgerList()){
            total = total + l.getFcDebit();
        }
        
        return total;
    }
    
    @XmlTransient
    public double getTotalInvoiceAmount(){
        double total = 0;
        for(PaymentInvoice in: getPaymentInvoiceList()){
            total = total + in.getAmount();
        }
        return total;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountDocumentNo != null ? accountDocumentNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountDocument)) {
            return false;
        }
        AccountDocument other = (AccountDocument) object;
        if ((this.accountDocumentNo == null && other.accountDocumentNo != null) || (this.accountDocumentNo != null && !this.accountDocumentNo.equals(other.accountDocumentNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jms.entity.AccountDocument[ accountDocumentNo=" + accountDocumentNo + " ]";
    }
    
}
