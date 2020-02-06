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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "businesslink")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BusinessLink.findAll", query = "SELECT b FROM BusinessLink b"),
    @NamedQuery(name = "BusinessLink.findByBusinessLinkNo", query = "SELECT b FROM BusinessLink b WHERE b.businessLinkNo = :businessLinkNo")})
public class BusinessLink implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "businessLinkNo")
    private Long businessLinkNo;
    @JoinColumn(name = "itsNo", referencedColumnName = "itsNo")
    @ManyToOne
    private ItsMaster itsNo;
    @JoinColumn(name = "businessNo", referencedColumnName = "businessNo")
    @ManyToOne
    private Business businessNo;

    public BusinessLink() {
    }

    public BusinessLink(Long businessLinkNo) {
        this.businessLinkNo = businessLinkNo;
    }

    public Long getBusinessLinkNo() {
        return businessLinkNo;
    }

    public void setBusinessLinkNo(Long businessLinkNo) {
        this.businessLinkNo = businessLinkNo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (businessLinkNo != null ? businessLinkNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BusinessLink)) {
            return false;
        }
        BusinessLink other = (BusinessLink) object;
        if ((this.businessLinkNo == null && other.businessLinkNo != null) || (this.businessLinkNo != null && !this.businessLinkNo.equals(other.businessLinkNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.jms.entity.BusinessLink[ businessLinkNo=" + businessLinkNo + " ]";
    }
    
}
