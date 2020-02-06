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
@Table(name = "thaalistatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ThaaliStatus.findAll", query = "SELECT t FROM ThaaliStatus t"),
    @NamedQuery(name = "ThaaliStatus.findByStatusNo", query = "SELECT t FROM ThaaliStatus t WHERE t.statusNo = :statusNo"),
    @NamedQuery(name = "ThaaliStatus.findByThaaliNo", query = "SELECT t FROM ThaaliStatus t WHERE t.thaaliNo.thaaliNo = :thaaliNo ORDER BY t.requestDate DESC"),
    @NamedQuery(name = "ThaaliStatus.findByStopDate", query = "SELECT t FROM ThaaliStatus t WHERE t.stopDate = :stopDate"),
    @NamedQuery(name = "ThaaliStatus.findByStartDate", query = "SELECT t FROM ThaaliStatus t WHERE t.startDate = :startDate"),
    @NamedQuery(name = "ThaaliStatus.findByComments", query = "SELECT t FROM ThaaliStatus t WHERE t.comments = :comments"),
    @NamedQuery(name = "ThaaliStatus.findByDescription", query = "SELECT t FROM ThaaliStatus t WHERE t.description = :description"),
    @NamedQuery(name = "ThaaliStatus.findByDescription2", query = "SELECT t FROM ThaaliStatus t WHERE t.description2 = :description2")})
public class ThaaliStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "statusNo")
    private Integer statusNo;
    @Column(name = "requestDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate = new Date();
    @Column(name = "stopDate")
    @Temporal(TemporalType.DATE)
    private Date stopDate;
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Size(max = 255)
    @Column(name = "comments")
    private String comments;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 25)
    @Column(name = "description2")
    private String description2;
    @JoinColumn(name = "thaaliNo", referencedColumnName = "thaaliNo")
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    private Thaali thaaliNo;
    @Column(name = "thaaliTaking")
    private Integer thaaliTaking;
    @Column(name = "thaaliNotTaking")
    private Integer thaaliNotTaking;
    
    public ThaaliStatus() {
    }

    public ThaaliStatus(Integer statusNo) {
        this.statusNo = statusNo;
    }

    public Integer getStatusNo() {
        return statusNo;
    }

    public void setStatusNo(Integer statusNo) {
        this.statusNo = statusNo;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public Thaali getThaaliNo() {
        return thaaliNo;
    }

    public void setThaaliNo(Thaali thaaliNo) {
        this.thaaliNo = thaaliNo;
    }

    @Override
    public int hashCode() {
        return statusNo;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ThaaliStatus)) {
            return false;
        }
        ThaaliStatus other = (ThaaliStatus) object;
        return statusNo==other.getStatusNo();
    }

    @Override
    public String toString() {
        return "org.famb.entity.ThaaliStatus[ statusNo=" + statusNo + " ]";
    }

    /**
     * @return the requestDate
     */
    public Date getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the thaaliTaking
     */
    public Integer getThaaliTaking() {
        return thaaliTaking;
    }

    /**
     * @param thaaliTaking the thaaliTaking to set
     */
    public void setThaaliTaking(Integer thaaliTaking) {
        this.thaaliTaking = thaaliTaking;
    }

    /**
     * @return the thaaliNotTaking
     */
    public Integer getThaaliNotTaking() {
        return thaaliNotTaking;
    }

    /**
     * @param thaaliNotTaking the thaaliNotTaking to set
     */
    public void setThaaliNotTaking(Integer thaaliNotTaking) {
        this.thaaliNotTaking = thaaliNotTaking;
    }
}
