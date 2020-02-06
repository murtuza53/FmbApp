/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity;

/**
 *
 * @author murtuza
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "appaccess")
@XmlRootElement
public class AppAccess implements Serializable {


    private static long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "accessid")
    private Integer accessid;
    @Size(max = 245)
    @Column(name = "module")
    private String module;
    @Size(max = 245)
    @Column(name = "menu")
    private String menu;
    @Size(max = 245)
    @Column(name = "webpage")
    private String webpage;
    @Size(max = 245)
    @Column(name = "resources")
    private String resources;
    @Column(name="createdon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdon;
    @Column(name="wef")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wef;
    @Column(name="expireson")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireson;
    @Column(name="read")
    private Boolean read;
    @Column(name="write")
    private Boolean write;
    @Column(name="view")
    private Boolean view;
    @Column(name="delete")
    private Boolean delete;

    public AppAccess(){
        
    }

    /**
     * @return the accessid
     */
    public Integer getAccessid() {
        return accessid;
    }

    /**
     * @param accessid the accessid to set
     */
    public void setAccessid(Integer accessid) {
        this.accessid = accessid;
    }

    /**
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return the menu
     */
    public String getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(String menu) {
        this.menu = menu;
    }

    /**
     * @return the webpage
     */
    public String getWebpage() {
        return webpage;
    }

    /**
     * @param webpage the webpage to set
     */
    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    /**
     * @return the resources
     */
    public String getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(String resources) {
        this.resources = resources;
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
     * @return the read
     */
    public Boolean getRead() {
        return read;
    }

    /**
     * @param read the read to set
     */
    public void setRead(Boolean read) {
        this.read = read;
    }

    /**
     * @return the write
     */
    public Boolean getWrite() {
        return write;
    }

    /**
     * @param write the write to set
     */
    public void setWrite(Boolean write) {
        this.write = write;
    }

    /**
     * @return the view
     */
    public Boolean getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(Boolean view) {
        this.view = view;
    }

    /**
     * @return the delete
     */
    public Boolean getDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
  
}
