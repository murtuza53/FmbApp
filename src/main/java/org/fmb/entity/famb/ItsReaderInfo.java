/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity.famb;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FatemaLaptop
 */
@Entity
@Table(name = "itsreaderinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItsReaderInfo.findByHfid", query = "SELECT i FROM ItsReaderInfo i WHERE i.hfid = :hfid"),
    @NamedQuery(name = "ItsReaderInfo.findByBackBarcode", query = "SELECT i FROM ItsReaderInfo i WHERE i.barcode = :barcode")
})
public class ItsReaderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "itsNo")
    private Integer itsNo;
    @Size(max = 45)
    @Column(name = "hfid")
    private String hfid;
    @Size(max = 45)
    @Column(name = "barcode")
    private String barcode;

    public ItsReaderInfo() {

    }

    /**
     * @return the itsNo
     */
    public Integer getItsNo() {
        return itsNo;
    }

    /**
     * @param itsNo the itsNo to set
     */
    public void setItsNo(Integer itsNo) {
        this.itsNo = itsNo;
    }

    /**
     * @return the hfid
     */
    public String getHfid() {
        return hfid;
    }

    /**
     * @param hfid the hfid to set
     */
    public void setHfid(String hfid) {
        this.hfid = hfid;
    }

    /**
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode the barcode to set
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return hfid;
    }
}
