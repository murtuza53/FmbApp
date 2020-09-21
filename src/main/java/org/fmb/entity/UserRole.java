/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

/**
 *
 * @author murtuza
 */

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "userrole")
@XmlRootElement
public class UserRole implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "roleid")
    private Integer roleid;
    @Size(max = 45)
    @Column(name = "rolename")
    private String rolename;
    @Column(name="createdon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdon;
    @Column(name="wef")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wef;
    @Column(name="expireson")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireson;
    @ManyToMany(mappedBy="userroles", fetch = FetchType.EAGER)
    private List<Users> users;
    @OneToMany(mappedBy = "accessid", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<AppAccess> appAccessList;
    @Size(max = 245)
    @Column(name = "successurl")
    private String successurl;
    @Column(name = "level")
    private Integer level;

    public UserRole(){
        
    }
    
    /**
     * @return the roleid
     */
    public Integer getRoleid() {
        return roleid;
    }

    /**
     * @param roleid the roleid to set
     */
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    /**
     * @return the rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param rolename the rolename to set
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * @return the createdon
     */
    public Date getCreatedon() {
        return createdon;
    }

    /**
     * @param createdon the createdon to set
     */
    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    /**
     * @return the wef
     */
    public Date getWef() {
        return wef;
    }

    /**
     * @param wef the wef to set
     */
    public void setWef(Date wef) {
        this.wef = wef;
    }

    /**
     * @return the expireson
     */
    public Date getExpireson() {
        return expireson;
    }

    /**
     * @param expireson the expireson to set
     */
    public void setExpireson(Date expireson) {
        this.expireson = expireson;
    }

    /**
     * @return the users
     */
    public List<Users> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<Users> users) {
        this.users = users;
    }

    /**
     * @return the appAccessList
     */
    public List<AppAccess> getAppAccessList() {
        return appAccessList;
    }

    /**
     * @param appAccessList the appAccessList to set
     */
    public void setAppAccessList(List<AppAccess> appAccessList) {
        this.appAccessList = appAccessList;
    }

    /**
     * @return the successurl
     */
    public String getSuccessurl() {
        return successurl;
    }

    /**
     * @param successurl the successurl to set
     */
    public void setSuccessurl(String successurl) {
        this.successurl = successurl;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "UserRole{" + "roleid=" + roleid + ", rolename=" + rolename + ", successurl=" + successurl + ", level=" + level + '}';
    }
    
}
