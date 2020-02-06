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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murtuza
 */
@Entity
@Table(name = "tablekeys")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tablekeys.findAll", query = "SELECT t FROM Tablekeys t"),
    @NamedQuery(name = "Tablekeys.findByTableName", query = "SELECT t FROM Tablekeys t WHERE t.tableName = :tableName"),
    @NamedQuery(name = "Tablekeys.findByNextValue", query = "SELECT t FROM Tablekeys t WHERE t.nextValue = :nextValue")})
public class Tablekeys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tableName")
    private String tableName;
    @Size(max = 255)
    @Column(name = "nextValue")
    private String nextValue;

    public Tablekeys() {
    }

    public Tablekeys(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNextValue() {
        return nextValue;
    }

    public void setNextValue(String nextValue) {
        this.nextValue = nextValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tableName != null ? tableName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tablekeys)) {
            return false;
        }
        Tablekeys other = (Tablekeys) object;
        if ((this.tableName == null && other.tableName != null) || (this.tableName != null && !this.tableName.equals(other.tableName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ktee.entity.Tablekeys[ tableName=" + tableName + " ]";
    }
    
}
