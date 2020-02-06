/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "itsmaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItsMaster.findAll", query = "SELECT i FROM ItsMaster i"),
    @NamedQuery(name = "ItsMaster.findByItsNo", query = "SELECT i FROM ItsMaster i WHERE i.itsNo = :itsNo"),
    @NamedQuery(name = "ItsMaster.findHof", query = "SELECT i FROM ItsMaster i WHERE i.itsNo = i.hofId"),
    @NamedQuery(name = "ItsMaster.findHofItsStatus", query = "SELECT i FROM ItsMaster i WHERE i.itsNo = i.hofId AND i.itsStatus=:itsStatus"),
    @NamedQuery(name = "ItsMaster.findHofSabilStatus", query = "SELECT i FROM ItsMaster i WHERE i.itsNo = i.hofId AND i.sabilStatus=:sabilStatus"),
    @NamedQuery(name = "ItsMaster.findByPrefix", query = "SELECT i FROM ItsMaster i WHERE i.prefix = :prefix"),
    @NamedQuery(name = "ItsMaster.findByFirstName", query = "SELECT i FROM ItsMaster i WHERE i.firstName = :firstName"),
    @NamedQuery(name = "ItsMaster.findByPrefix2", query = "SELECT i FROM ItsMaster i WHERE i.prefix2 = :prefix2"),
    @NamedQuery(name = "ItsMaster.findBySecondName", query = "SELECT i FROM ItsMaster i WHERE i.secondName = :secondName"),
    @NamedQuery(name = "ItsMaster.findBySurname", query = "SELECT i FROM ItsMaster i WHERE i.surname = :surname"),
    @NamedQuery(name = "ItsMaster.findByMobile1", query = "SELECT i FROM ItsMaster i WHERE i.mobile1 = :mobile1"),
    @NamedQuery(name = "ItsMaster.findByMobile2", query = "SELECT i FROM ItsMaster i WHERE i.mobile2 = :mobile2"),
    @NamedQuery(name = "ItsMaster.findByTel1", query = "SELECT i FROM ItsMaster i WHERE i.tel1 = :tel1"),
    @NamedQuery(name = "ItsMaster.findByTel2", query = "SELECT i FROM ItsMaster i WHERE i.tel2 = :tel2"),
    @NamedQuery(name = "ItsMaster.findByFax1", query = "SELECT i FROM ItsMaster i WHERE i.fax1 = :fax1"),
    @NamedQuery(name = "ItsMaster.findByFax2", query = "SELECT i FROM ItsMaster i WHERE i.fax2 = :fax2"),
    @NamedQuery(name = "ItsMaster.findByEmail1", query = "SELECT i FROM ItsMaster i WHERE i.email1 = :email1"),
    @NamedQuery(name = "ItsMaster.findByEmail2", query = "SELECT i FROM ItsMaster i WHERE i.email2 = :email2"),
    @NamedQuery(name = "ItsMaster.findByFlatNo", query = "SELECT i FROM ItsMaster i WHERE i.flatNo = :flatNo"),
    @NamedQuery(name = "ItsMaster.findByBuildingNo", query = "SELECT i FROM ItsMaster i WHERE i.buildingNo = :buildingNo"),
    @NamedQuery(name = "ItsMaster.findByRoadNo", query = "SELECT i FROM ItsMaster i WHERE i.roadNo = :roadNo"),
    @NamedQuery(name = "ItsMaster.findByBlockNo", query = "SELECT i FROM ItsMaster i WHERE i.blockNo = :blockNo"),
    @NamedQuery(name = "ItsMaster.findByAreaName", query = "SELECT i FROM ItsMaster i WHERE i.areaName = :areaName"),
    @NamedQuery(name = "ItsMaster.findByHofId", query = "SELECT i FROM ItsMaster i WHERE i.hofId = :hofId"),
    @NamedQuery(name = "ItsMaster.findByGender", query = "SELECT i FROM ItsMaster i WHERE i.gender = :gender"),
    @NamedQuery(name = "ItsMaster.findByArabicName", query = "SELECT i FROM ItsMaster i WHERE i.arabicName = :arabicName")})
public class ItsMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "itsNo")
    private Integer itsNo;
    @Size(max = 255)
    @Column(name = "prefix")
    private String prefix;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 255)
    @Column(name = "prefix2")
    private String prefix2;
    @Size(max = 255)
    @Column(name = "secondName")
    private String secondName;
    @Size(max = 255)
    @Column(name = "surname")
    private String surname;
    @Size(max = 45)
    @Column(name = "cpr")
    private String cpr;
    @Size(max = 45)
    @Column(name = "mobile1")
    private String mobile1;
    @Size(max = 45)
    @Column(name = "mobile2")
    private String mobile2;
    @Size(max = 45)
    @Column(name = "tel1")
    private String tel1;
    @Size(max = 45)
    @Column(name = "tel2")
    private String tel2;
    @Size(max = 45)
    @Column(name = "fax1")
    private String fax1;
    @Size(max = 45)
    @Column(name = "fax2")
    private String fax2;
    @Size(max = 255)
    @Column(name = "email1")
    private String email1;
    @Size(max = 255)
    @Column(name = "email2")
    private String email2;
    @Size(max = 45)
    @Column(name = "flatNo")
    private String flatNo;
    @Size(max = 45)
    @Column(name = "buildingNo")
    private String buildingNo;
    @Size(max = 45)
    @Column(name = "roadNo")
    private String roadNo;
    @Size(max = 45)
    @Column(name = "blockNo")
    private String blockNo;
    @Size(max = 255)
    @Column(name = "areaName")
    private String areaName;
    @Column(name = "hofId")
    private Integer hofId;
    @Size(max = 1)
    @Column(name = "gender")
    private String gender;
    @Size(max = 255)
    @Column(name = "arabicName")
    private String arabicName;
    @Size(max = 255)
    @Column(name = "fullName")
    private String fullName;
    @Size(max = 255)
    @Column(name = "fatherSurname")
    private String fatherSurname;
    @Size(max = 255)
    @Column(name = "husbandName")
    private String husbandName;
    @Size(max = 45)
    @Column(name = "maritalStatus")
    private String maritalStatus;
    @Size(max = 45)
    @Column(name = "vatan")
    private String vatan;
    @Size(max = 45)
    @Column(name = "nationality")
    private String nationality;
    @Size(max = 45)
    @Column(name = "misaq")
    private String misaq;
    @Size(max = 45)
    @Column(name = "itsStatus")
    private String itsStatus;          //ACTIVE, LEFT, TO_TRANSFER_OUT, DEAD
    @Size(max = 45)
    @Column(name = "sabilStatus")
    private String sabilStatus;     //PAYS_SABIL, NOT_PAYING_SABIL, EXEMPTED_BY_KHIDMAT, EXEMPTED_BY_RAZA, VISITOR

    @JoinColumn(name = "houseid", referencedColumnName = "houseid")
    @ManyToOne
    private House houseid;

    //@OneToMany(mappedBy = "itsNo")
    //private List<Sabil> sabilList;
    //@OneToMany(mappedBy = "itsNo")
    //private List<AccountDocument> accountDocumentList;
    //@OneToMany(mappedBy = "itsNo")
    //private List<PaymentInvoice> paymentInvoiceList;
    //@OneToMany(mappedBy = "itsNo")
    //private List<BusinessLink> businessLinkList;
    public ItsMaster() {
    }

    public ItsMaster(Integer itsNo) {
        this.itsNo = itsNo;
    }

    public ItsMaster(Integer itsNo, String firstName) {
        this.itsNo = itsNo;
        this.firstName = firstName;
    }

    public Integer getItsNo() {
        return itsNo;
    }

    public void setItsNo(Integer itsNo) {
        this.itsNo = itsNo;
    }

    @XmlTransient
    public String getFullName1() {
        StringBuilder name = new StringBuilder("");
        if (prefix != null) {
            name.append(prefix).append(" ");
        }

        if (firstName != null) {
            name.append(firstName).append(" ");
        }

        if (prefix2 != null) {
            name.append(prefix2).append(" ");
        }

        if (secondName != null) {
            name.append(secondName).append(" ");
        }

        if (surname != null) {
            name.append(surname);
        }

        return name.toString().trim();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPrefix2() {
        return prefix2;
    }

    public void setPrefix2(String prefix2) {
        this.prefix2 = prefix2;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getFax1() {
        return fax1;
    }

    public void setFax1(String fax1) {
        this.fax1 = fax1;
    }

    public String getFax2() {
        return fax2;
    }

    public void setFax2(String fax2) {
        this.fax2 = fax2;
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

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getHofId() {
        return hofId;
    }

    public void setHofId(Integer hofId) {
        this.hofId = hofId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    /*@XmlTransient
    public List<Sabil> getSabilList() {
        return sabilList;
    }

    public void setSabilList(List<Sabil> sabilList) {
        this.sabilList = sabilList;
    }

    @XmlTransient
    public List<AccountDocument> getAccountDocumentList() {
        return accountDocumentList;
    }

    public void setAccountDocumentList(List<AccountDocument> accountDocumentList) {
        this.accountDocumentList = accountDocumentList;
    }

    @XmlTransient
    public List<PaymentInvoice> getPaymentInvoiceList() {
        return paymentInvoiceList;
    }

    public void setPaymentInvoiceList(List<PaymentInvoice> paymentInvoiceList) {
        this.paymentInvoiceList = paymentInvoiceList;
    }

    @XmlTransient
    public List<BusinessLink> getBusinessLinkList() {
        return businessLinkList;
    }

    public void setBusinessLinkList(List<BusinessLink> businessLinkList) {
        this.businessLinkList = businessLinkList;
    }*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itsNo != null ? itsNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItsMaster)) {
            return false;
        }
        ItsMaster other = (ItsMaster) object;
        if ((this.itsNo == null && other.itsNo != null) || (this.itsNo != null && !this.itsNo.equals(other.itsNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getFullName() + " [" + itsNo + "]";
    }

    /**
     * @return the cpr
     */
    public String getCpr() {
        return cpr;
    }

    /**
     * @param cpr the cpr to set
     */
    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        if (fullName == null) {
            return getFullName1();
        }
        return fullName;
    }

    /**
     * @return the fatherSurname
     */
    public String getFatherSurname() {
        return fatherSurname;
    }

    /**
     * @param fatherSurname the fatherSurname to set
     */
    public void setFatherSurname(String fatherSurname) {
        this.fatherSurname = fatherSurname;
    }

    /**
     * @return the maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * @param maritalStatus the maritalStatus to set
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * @return the vatan
     */
    public String getVatan() {
        return vatan;
    }

    /**
     * @param vatan the vatan to set
     */
    public void setVatan(String vatan) {
        this.vatan = vatan;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the husbandName
     */
    public String getHusbandName() {
        return husbandName;
    }

    /**
     * @param husbandName the husbandName to set
     */
    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    /**
     * @return the sabilStatus
     */
    public String getSabilStatus() {
        return sabilStatus;
    }

    /**
     * @param sabilStatus the sabilStatus to set
     */
    public void setSabilStatus(String sabilStatus) {
        this.sabilStatus = sabilStatus;
    }

    /**
     * @return the misaq
     */
    public String getMisaq() {
        return misaq;
    }

    /**
     * @param misaq the misaq to set
     */
    public void setMisaq(String misaq) {
        this.misaq = misaq;
    }

    /**
     * @return the itsStatus
     */
    public String getItsStatus() {
        return itsStatus;
    }

    /**
     * @param itsStatus the itsStatus to set
     */
    public void setItsStatus(String itsStatus) {
        this.itsStatus = itsStatus;
    }

    public boolean getHofStatus() {
        if (itsNo == null) {
            return false;
        }
        if (hofId == null) {
            return false;
        }
        return itsNo.intValue() == hofId.intValue();
    }

    /**
     * @return the houseid
     */
    public House getHouseid() {
        return houseid;
    }

    /**
     * @param houseid the houseid to set
     */
    public void setHouseid(House houseid) {
        this.houseid = houseid;
    }
}
