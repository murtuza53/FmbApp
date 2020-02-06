/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity.famb;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.ejms.entity.ItsMaster;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "thaaliallocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ThaaliAllocation.findAll", query = "SELECT t FROM ThaaliAllocation t WHERE t.active='TRUE' ORDER BY t.thaaliNo.thaaliNo"),
    @NamedQuery(name = "ThaaliAllocation.findAllActive", query = "SELECT t FROM ThaaliAllocation t WHERE t.active='TRUE' ORDER BY t.thaaliNo.thaaliNo"),
    @NamedQuery(name = "ThaaliAllocation.findAllInActive", query = "SELECT t FROM ThaaliAllocation t WHERE t.active='FALSE' ORDER BY t.thaaliNo.thaaliNo"),
    @NamedQuery(name = "ThaaliAllocation.findByAllocationNo", query = "SELECT t FROM ThaaliAllocation t WHERE t.allocationNo = :allocationNo"),
    @NamedQuery(name = "ThaaliAllocation.findByThaaliAllocation", query = "SELECT t FROM ThaaliAllocation t WHERE t.active='TRUE' AND t.thaaliNo.thaaliNo=:thaaliNo"),
    @NamedQuery(name = "ThaaliAllocation.findByItsAllocation", query = "SELECT t FROM ThaaliAllocation t WHERE t.active='TRUE' AND t.itsNo.itsNo=:itsNo"),
    @NamedQuery(name = "ThaaliAllocation.findByPickupPerson", query = "SELECT t FROM ThaaliAllocation t WHERE t.active='TRUE' AND t.pickupBy.pickupNo=:pickupNo"),
    @NamedQuery(name = "ThaaliAllocation.findByRegistrationDate", query = "SELECT t FROM ThaaliAllocation t WHERE t.registrationDate = :registrationDate"),
    @NamedQuery(name = "ThaaliAllocation.findByDescription", query = "SELECT t FROM ThaaliAllocation t WHERE t.description = :description")})
public class ThaaliAllocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "allocationNo")
    private Integer allocationNo;
    @JoinColumn(name = "thaaliNo", referencedColumnName = "thaaliNo")
    @ManyToOne
    private Thaali thaaliNo;
    @Column(name = "registrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Size(max = 70)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "itsNo", referencedColumnName = "itsNo")
    @ManyToOne
    private ItsMaster itsNo;
    @Column(name = "active")
    private Boolean active = true;
    @JoinColumn(name = "pickupNo", referencedColumnName = "pickupNo")
    @ManyToOne
    private ThaaliPickupPerson pickupBy;
    @JoinColumn(name = "accountManagerNo", referencedColumnName = "itsNo")
    @ManyToOne
    private ItsMaster accountManagerNo;
    @Size(max = 70)
    @Column(name = "amEmailAddress")
    private String amEmailAddress;
    @Column(name = "closeDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;
    @Size(max = 45)
    @Column(name = "tifin")
    private String tifin;       //F-Family, S-Single, example FFS-2Family 1Single

    public ThaaliAllocation() {
    }

    public ThaaliAllocation(Integer allocationNo) {
        this.allocationNo = allocationNo;
    }

    public Integer getAllocationNo() {
        return allocationNo;
    }

    public void setAllocationNo(Integer allocationNo) {
        this.allocationNo = allocationNo;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItsMaster getItsNo() {
        return itsNo;
    }

    public void setItsNo(ItsMaster itsNo) {
        this.itsNo = itsNo;
    }

    @Override
    public int hashCode() {
        return allocationNo;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ThaaliAllocation)) {
            return false;
        }
        ThaaliAllocation other = (ThaaliAllocation) object;
        return allocationNo==other.getAllocationNo();
    }

    @Override
    public String toString() {
        return "org.famb.entity.ThaaliAllocation[ allocationNo=" + allocationNo + " ]";
    }

    /**
     * @return the active
     */
    public Boolean isActive() {
        return active;
    }

    public Boolean getActive(){
        return isActive();
    }
    
    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the pickupBy
     */
    public ThaaliPickupPerson getPickupBy() {
        return pickupBy;
    }

    /**
     * @param pickupBy the pickupBy to set
     */
    public void setPickupBy(ThaaliPickupPerson pickupBy) {
        this.pickupBy = pickupBy;
    }
    
    /**
     * @return the accountManagerNo
     */
    public ItsMaster getAccountManagerNo() {
        return accountManagerNo;
    }

    /**
     * @param accountManagerNo the accountManagerNo to set
     */
    public void setAccountManagerNo(ItsMaster accountManagerNo) {
        this.accountManagerNo = accountManagerNo;
    }

    /**
     * @return the amEmailAddress
     */
    public String getAmEmailAddress() {
        return amEmailAddress;
    }

    /**
     * @param amEmailAddress the amEmailAddress to set
     */
    public void setAmEmailAddress(String amEmailAddress) {
        this.amEmailAddress = amEmailAddress;
    }

    /**
     * @return the closeDate
     */
    public Date getCloseDate() {
        return closeDate;
    }

    /**
     * @param closeDate the closeDate to set
     */
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * @return the tifin
     */
    public String getTifin() {
        return tifin;
    }

    /**
     * @param tifin the tifin to set
     */
    public void setTifin(String tifin) {
        this.tifin = tifin;
    }

    /**
     * @return the thaaliNo
     */
    public Thaali getThaaliNo() {
        return thaaliNo;
    }

    /**
     * @param thaaliNo the thaaliNo to set
     */
    public void setThaaliNo(Thaali thaaliNo) {
        this.thaaliNo = thaaliNo;
    }
}
