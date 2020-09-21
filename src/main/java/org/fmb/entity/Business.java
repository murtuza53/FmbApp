/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "business")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Business.findAll", query = "SELECT b FROM Business b"),
    @NamedQuery(name = "Business.findByBusinessNo", query = "SELECT b FROM Business b WHERE b.businessNo = :businessNo"),
    @NamedQuery(name = "Business.findByCrNo", query = "SELECT b FROM Business b WHERE b.crNo = :crNo"),
    @NamedQuery(name = "Business.findByBusinessName", query = "SELECT b FROM Business b WHERE b.businessName = :businessName"),
    @NamedQuery(name = "Business.findByBusinessType", query = "SELECT b FROM Business b WHERE b.businessType = :businessType"),
    @NamedQuery(name = "Business.findByAddress", query = "SELECT b FROM Business b WHERE b.address = :address"),
    @NamedQuery(name = "Business.findByPoBox", query = "SELECT b FROM Business b WHERE b.poBox = :poBox"),
    @NamedQuery(name = "Business.findByOfficeNumber1", query = "SELECT b FROM Business b WHERE b.officeNumber1 = :officeNumber1"),
    @NamedQuery(name = "Business.findByOfficeNumber2", query = "SELECT b FROM Business b WHERE b.officeNumber2 = :officeNumber2"),
    @NamedQuery(name = "Business.findByFaxNumber1", query = "SELECT b FROM Business b WHERE b.faxNumber1 = :faxNumber1"),
    @NamedQuery(name = "Business.findByFaxNumber2", query = "SELECT b FROM Business b WHERE b.faxNumber2 = :faxNumber2"),
    @NamedQuery(name = "Business.findByEmail1", query = "SELECT b FROM Business b WHERE b.email1 = :email1"),
    @NamedQuery(name = "Business.findByEmail2", query = "SELECT b FROM Business b WHERE b.email2 = :email2")})
public class Business implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "businessNo")
    private Long businessNo;
    @Size(max = 45)
    @Column(name = "crNo")
    private String crNo;
    @Size(max = 255)
    @Column(name = "businessName")
    private String businessName;
    @Size(max = 255)
    @Column(name = "businessType")
    private String businessType;
    @Size(max = 255)
    @Column(name = "address")
    private String address;
    @Size(max = 255)
    @Column(name = "poBox")
    private String poBox;
    @Size(max = 45)
    @Column(name = "officeNumber1")
    private String officeNumber1;
    @Size(max = 45)
    @Column(name = "officeNumber2")
    private String officeNumber2;
    @Size(max = 45)
    @Column(name = "faxNumber1")
    private String faxNumber1;
    @Size(max = 45)
    @Column(name = "faxNumber2")
    private String faxNumber2;
    @Size(max = 255)
    @Column(name = "email1")
    private String email1;
    @Size(max = 255)
    @Column(name = "email2")
    private String email2;
    //@OneToMany(mappedBy = "businessNo")
    //private List<Sabil> sabilList;
    //@OneToMany(mappedBy = "businessNo")
    //private List<PaymentInvoice> paymentInvoiceList;
    @OneToMany(mappedBy = "businessNo")
    private List<BusinessLink> businessLinkList;

    public Business() {
    }

    public Business(Long businessNo) {
        this.businessNo = businessNo;
    }

    public Long getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(Long businessNo) {
        this.businessNo = businessNo;
    }

    public String getCrNo() {
        return crNo;
    }

    public void setCrNo(String crNo) {
        this.crNo = crNo;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public String getOfficeNumber1() {
        return officeNumber1;
    }

    public void setOfficeNumber1(String officeNumber1) {
        this.officeNumber1 = officeNumber1;
    }

    public String getOfficeNumber2() {
        return officeNumber2;
    }

    public void setOfficeNumber2(String officeNumber2) {
        this.officeNumber2 = officeNumber2;
    }

    public String getFaxNumber1() {
        return faxNumber1;
    }

    public void setFaxNumber1(String faxNumber1) {
        this.faxNumber1 = faxNumber1;
    }

    public String getFaxNumber2() {
        return faxNumber2;
    }

    public void setFaxNumber2(String faxNumber2) {
        this.faxNumber2 = faxNumber2;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    /*@XmlTransient
    public List<Sabil> getSabilList() {
        return sabilList;
    }

    public void setSabilList(List<Sabil> sabilList) {
        this.sabilList = sabilList;
    }

    @XmlTransient
    public List<PaymentInvoice> getPaymentInvoiceList() {
        return paymentInvoiceList;
    }

    public void setPaymentInvoiceList(List<PaymentInvoice> paymentInvoiceList) {
        this.paymentInvoiceList = paymentInvoiceList;
    }*/

    @XmlTransient
    public List<BusinessLink> getBusinessLinkList() {
        return businessLinkList;
    }

    public void setBusinessLinkList(List<BusinessLink> businessLinkList) {
        this.businessLinkList = businessLinkList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (businessNo != null ? businessNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Business)) {
            return false;
        }
        Business other = (Business) object;
        if ((this.businessNo == null && other.businessNo != null) || (this.businessNo != null && !this.businessNo.equals(other.businessNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getBusinessName() + " [" + businessNo + "]";
    }
    
}
