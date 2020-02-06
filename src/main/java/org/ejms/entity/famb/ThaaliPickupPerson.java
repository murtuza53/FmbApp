/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity.famb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "thaalipickupperson")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ThaaliPickupPerson.findAll", query = "SELECT i FROM ThaaliPickupPerson i"),
    @NamedQuery(name = "ThaaliPickupPerson.findByPersonName", 
            query = "SELECT i FROM ThaaliPickupPerson i WHERE i.personName LIKE '%:personname%'")})
public class ThaaliPickupPerson implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pickupNo")
    private Integer pickupNo;
    @Size(max = 75)
    @Column(name = "personname")
    private String personName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "mobile")
    private String mobile;
    @Size(max = 45)
    @Column(name = "tel")
    private String telphone;
    @Size(max = 45)
    @Column(name = "email")
    private String email;

    public ThaaliPickupPerson() {
    }

    public ThaaliPickupPerson(String personName) {
        this.personName = personName;
    }
    
    /**
     * @return the pickupNo
     */
    public Integer getPickupNo() {
        return pickupNo;
    }

    /**
     * @param pickupNo the pickupNo to set
     */
    public void setPickupNo(Integer pickupNo) {
        this.pickupNo = pickupNo;
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @param personName the personName to set
     */
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone the telphone to set
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ThaaliPickupPerson)) {
            return false;
        }
        ThaaliPickupPerson other = (ThaaliPickupPerson) object;
        return pickupNo==other.getPickupNo();
    }

    public String toString(){
        return getPersonName();
    }
}
