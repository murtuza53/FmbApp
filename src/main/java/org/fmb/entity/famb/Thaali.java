/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity.famb;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.fmb.entity.ItsMaster;
import org.fmb.entity.Sabil;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "thaali")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Thaali.findAll", query = "SELECT t FROM Thaali t"),
    @NamedQuery(name = "Thaali.findByThaaliNo", query = "SELECT t FROM Thaali t WHERE t.thaaliNo = :thaaliNo"),
    @NamedQuery(name = "Thaali.findByDescription", query = "SELECT t FROM Thaali t WHERE t.description = :description"),
    @NamedQuery(name = "Thaali.findByNumberOfTifin", query = "SELECT t FROM Thaali t WHERE t.numberOfTifin = :numberOfTifin"),
    @NamedQuery(name = "Thaali.findByDescription2", query = "SELECT t FROM Thaali t WHERE t.description2 = :description2"),
    @NamedQuery(name = "Thaali.findByDescription3", query = "SELECT t FROM Thaali t WHERE t.description3 = :description3"),
    @NamedQuery(name = "Thaali.findByNumberOfMembers", query = "SELECT t FROM Thaali t WHERE t.numberOfMembers = :numberOfMembers")})
public class Thaali implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "thaaliNo")
    private int thaaliNo;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numberOfTifin")
    private int numberOfTifin;
    @Size(max = 45)
    @Column(name = "description2")
    private String description2;
    @Size(max = 45)
    @Column(name = "description3")
    private String description3;
    @Column(name = "numberOfMembers")
    private int numberOfMembers;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "thaaliNo")
    @Fetch(FetchMode.SUBSELECT)
    private List<ThaaliAllocation> thaaliAllocationList;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "thaaliNo")
    @Fetch(FetchMode.SUBSELECT)
    private List<ThaaliStatus> thaaliStatusList;
    @JoinColumn(name = "sabilNo", referencedColumnName = "sabilNo")
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    private Sabil sabilNo;

    public Thaali() {
    }

    public Thaali(int thaaliNo) {
        this.thaaliNo = thaaliNo;
    }

    public Thaali(int thaaliNo, int numberOfTifin) {
        this.thaaliNo = thaaliNo;
        this.numberOfTifin = numberOfTifin;
    }

    public int getThaaliNo() {
        return thaaliNo;
    }

    public void setThaaliNo(int thaaliNo) {
        this.thaaliNo = thaaliNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfTifin() {
        return numberOfTifin;
    }

    public void setNumberOfTifin(int numberOfTifin) {
        this.numberOfTifin = numberOfTifin;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    public void setNumberOfMembers(int numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    //@XmlTransient
    public List<ThaaliAllocation> getThaaliAllocationList() {
        return thaaliAllocationList;
    }

    public void setThaaliAllocationList(List<ThaaliAllocation> thaaliAllocationList) {
        this.thaaliAllocationList = thaaliAllocationList;
    }

    //@XmlTransient
    public List<ThaaliStatus> getThaaliStatusList() {
        return thaaliStatusList;
    }

    public void setThaaliStatusList(List<ThaaliStatus> thaaliStatusList) {
        this.thaaliStatusList = thaaliStatusList;
    }

    @Override
    public int hashCode() {
        return thaaliNo;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Thaali)) {
            return false;
        }
        Thaali other = (Thaali) object;
        return thaaliNo==other.getThaaliNo();
    }

    @Override
    public String toString() {
        return "ThaaliNo. " + thaaliNo;
    }

    /**
     * @return the sabilNo
     */
    public Sabil getSabilNo() {
        return sabilNo;
    }

    /**
     * @param sabilNo the sabilNo to set
     */
    public void setSabilNo(Sabil sabilNo) {
        this.sabilNo = sabilNo;
    }
    
}
