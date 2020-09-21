/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

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

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "area")
@XmlRootElement
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "areaid")
    private Integer areaid;
    @Size(max = 145)
    @Column(name = "areaname")
    private String areaname;
    @Basic(optional = false)
    @NotNull
    @Size(max = 45)
    @Column(name = "block")
    private String block;
    @Size(max = 45)
    @Column(name = "description")
    private String description;

    public Area() {
    }

    public Area(String areaname) {
        this.areaname = areaname;
    }

    /**
     * @return the areaid
     */
    public Integer getAreaid() {
        return areaid;
    }

    /**
     * @param areaid the areaid to set
     */
    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    /**
     * @return the areaname
     */
    public String getAreaname() {
        return areaname;
    }

    /**
     * @param areaname the areaname to set
     */
    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    /**
     * @return the block
     */
    public String getBlock() {
        return block;
    }

    /**
     * @param block the block to set
     */
    public void setBlock(String block) {
        this.block = block;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return areaname + '[' + areaid + "]";
    }

    
}
