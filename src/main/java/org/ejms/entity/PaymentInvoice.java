/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity;

import com.hijri.HijriDate;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "paymentinvoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentInvoice.findAll", query = "SELECT p FROM PaymentInvoice p"),
    @NamedQuery(name = "PaymentInvoice.findByPiNo", query = "SELECT p FROM PaymentInvoice p WHERE p.piNo = :piNo"),
    @NamedQuery(name = "PaymentInvoice.findByHijrimonth", query = "SELECT p FROM PaymentInvoice p WHERE p.hijriMonth = :hijrimonth"),
    @NamedQuery(name = "PaymentInvoice.findByHijriyear", query = "SELECT p FROM PaymentInvoice p WHERE p.hijriYear = :hijriyear"),
    @NamedQuery(name = "PaymentInvoice.findByAmount", query = "SELECT p FROM PaymentInvoice p WHERE p.amount = :amount"),
    @NamedQuery(name = "PaymentInvoice.findByDescription", query = "SELECT p FROM PaymentInvoice p WHERE p.description = :description"),
    @NamedQuery(name = "PaymentInvoice.findByInvoiceType", query = "SELECT p FROM PaymentInvoice p WHERE p.invoiceType = :invoiceType"),
    @NamedQuery(name = "PaymentInvoice.findByAccountNo", query = "SELECT p FROM PaymentInvoice p WHERE p.accountNo = :accountNo"),
    @NamedQuery(name = "PaymentInvoice.findByAccountDocumentNo", query = "SELECT p FROM PaymentInvoice p WHERE p.accountDocumentNo.accountDocumentNo = :accountDocumentNo"),
    @NamedQuery(name = "PaymentInvoice.findByInvoiceDate", query = "SELECT p FROM PaymentInvoice p WHERE p.invoiceDate = :invoiceDate"),
    @NamedQuery(name = "PaymentInvoice.findBySabilNo", query = "SELECT p FROM PaymentInvoice p WHERE p.sabilNo = :sabilNo"),
    @NamedQuery(name = "PaymentInvoice.findByBusinessNo", query = "SELECT b FROM PaymentInvoice b WHERE b.businessNo.businessNo = :businessNo ORDER BY b.hijriYear, b.hijriMonth"),
    @NamedQuery(name = "PaymentInvoice.findByItsNo", query = "SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo ORDER BY b.hijriYear, b.hijriMonth"),
    @NamedQuery(name = "PaymentInvoice.findByItsNoByType", query = "SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo AND b.invoiceType = :invoiceType ORDER BY b.hijriYear, b.hijriMonth"),
    @NamedQuery(name = "PaymentInvoice.findByBusinessNoByYear", query = "SELECT b FROM PaymentInvoice b WHERE b.businessNo.businessNo = :businessNo AND b.hijriYear = :hijriYear ORDER BY b.hijriYear, b.hijriMonth"),
    @NamedQuery(name = "PaymentInvoice.findByItsNoByYear", query = "SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo AND b.hijriYear = :hijriYear ORDER BY b.hijriYear, b.hijriMonth"),
    @NamedQuery(name = "PaymentInvoice.findByBusinessNoOutstanding", query = "SELECT b FROM PaymentInvoice b WHERE b.businessNo.businessNo = :businessNo AND b.accountDocumentNo IS NULL ORDER BY b.hijriYear, b.hijriMonth"),
    @NamedQuery(name = "PaymentInvoice.findByItsNoByOutstanding", query = "SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo AND b.accountDocumentNo IS NULL ORDER BY b.hijriYear, b.hijriMonth"),
    @NamedQuery(name = "PaymentInvoice.findByBusinessNoPaid", query = "SELECT b FROM PaymentInvoice b WHERE b.businessNo.businessNo = :businessNo AND b.accountDocumentNo IS NOT NULL ORDER BY b.hijriYear, b.hijriMonth"),
    @NamedQuery(name = "PaymentInvoice.findByItsNoByPaid", query = "SELECT b FROM PaymentInvoice b WHERE b.itsNo.itsNo = :itsNo AND b.accountDocumentNo IS NOT NULL ORDER BY b.hijriYear, b.hijriMonth")})
public class PaymentInvoice implements Serializable {

    public static final String SABIL = "Sabil";
    public static final String LAGAT = "Lagat";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "piNo")
    private Long piNo = 0l;
    @Column(name = "hijrimonth")
    private Integer hijriMonth = HijriDate.getInstance().getMonth();
    @Column(name = "hijriyear")
    private Integer hijriYear = HijriDate.getInstance().getYear();
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 45)
    @Column(name = "invoiceType")
    private String invoiceType = SABIL;
    @Size(max = 45)
    @Column(name = "accountNo")
    private String accountNo;
    @Column(name = "invoiceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;
    @JoinColumn(name = "sabilNo", referencedColumnName = "sabilNo")
    @ManyToOne
    private Sabil sabilNo;
    @JoinColumn(name = "itsNo", referencedColumnName = "itsNo")
    @ManyToOne
    private ItsMaster itsNo;
    @JoinColumn(name = "businessNo", referencedColumnName = "businessNo")
    @ManyToOne
    private Business businessNo;
    @JoinColumn(name = "accountDocumentNo", referencedColumnName = "accountDocumentNo")
    @ManyToOne
    private AccountDocument accountDocumentNo;

    public PaymentInvoice() {
    }

    public PaymentInvoice(Long piNo) {
        this.piNo = piNo;
    }

    public Long getPiNo() {
        return piNo;
    }

    public void setPiNo(Long piNo) {
        this.piNo = piNo;
    }

    public Integer getHijriMonth() {
        return hijriMonth;
    }

    public String getHijriMonthSpell(){
        if(invoiceType.equalsIgnoreCase("Lagat")){
            return description;
        }
        
        if(hijriMonth!=null){
            return hijriMonth + ". " +SimpleHijriDateUtils.spellMonth(hijriMonth-1);
        }
        return null;
    }
    
    public void setHijriMonth(Integer hijrimonth) {
        this.hijriMonth = hijrimonth;
    }

    public Integer getHijriYear() {
        return hijriYear;
    }

    public void setHijriYear(Integer hijriyear) {
        this.hijriYear = hijriyear;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Sabil getSabilNo() {
        return sabilNo;
    }

    public void setSabilNo(Sabil sabilNo) {
        this.sabilNo = sabilNo;
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

    public String getPaymentRef(){
        if(getAccountDocumentNo()==null){
            return null;
        }
        return getAccountDocumentNo().getAccountDocumentNo();
    }
    
    public AccountDocument getAccountDocumentNo() {
        return accountDocumentNo;
    }

    public void setAccountDocumentNo(AccountDocument accountDocumentNo) {
        this.accountDocumentNo = accountDocumentNo;
    }

    public String getDetails(){
        if(hijriMonth!=null && hijriMonth>0){
            return getInvoiceType() + " " + getHijriMonthSpell() + " " + getHijriYear();
        } else {
            return getDescription();     //getAccountNo();
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (piNo != null ? piNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentInvoice)) {
            return false;
        }
        PaymentInvoice other = (PaymentInvoice) object;
        if ((this.piNo == null && other.piNo != null) || (this.piNo != null && !this.piNo.equals(other.piNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jms.entity.PaymentInvoice[ piNo=" + piNo + " ]";
    }

    public boolean isPaid() {
        return getAccountDocumentNo() != null;
    }

    public String getAccountDescription() {
        if (getAccountDocumentNo() != null) {
            return getAccountDocumentNo().getDescription();
        }
        return null;
    }

    public boolean isSabilInvoice(){
        return invoiceType.equalsIgnoreCase(SABIL);
    }
    
    public boolean isLagatInvoice(){
        return invoiceType.equalsIgnoreCase(LAGAT);
    }
}
