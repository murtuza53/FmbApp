/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.helper;

import java.io.Serializable;
import org.ejms.entity.ItsMaster;
import org.ejms.entity.Sabil;
import org.ejms.entity.famb.ThaaliAllocation;

/**
 *
 * @author Burhani152
 */
public class FambHelper implements Serializable{
    
    private int itsNo;
    private ItsMaster itsMaster;
    private Sabil sabil;
    private ThaaliAllocation thaaliAllocation;
    
    public FambHelper(){
        
    }
    
    public FambHelper(Sabil s, ThaaliAllocation ta){
        this.itsMaster = ta.getItsNo();
        itsNo = itsMaster.getItsNo();
        this.sabil = s;
        this.thaaliAllocation = ta;
    }

    /**
     * @return the itsMaster
     */
    public ItsMaster getItsMaster() {
        return itsMaster;
    }

    /**
     * @param itsMaster the itsMaster to set
     */
    public void setItsMaster(ItsMaster itsMaster) {
        this.itsMaster = itsMaster;
    }

    /**
     * @return the sabil
     */
    public Sabil getSabil() {
        return sabil;
    }

    /**
     * @param sabil the sabil to set
     */
    public void setSabil(Sabil sabil) {
        this.sabil = sabil;
    }

    /**
     * @return the thaaliAllocation
     */
    public ThaaliAllocation getThaaliAllocation() {
        return thaaliAllocation;
    }

    /**
     * @param thaaliAllocation the thaaliAllocation to set
     */
    public void setThaaliAllocation(ThaaliAllocation thaaliAllocation) {
        this.thaaliAllocation = thaaliAllocation;
    }

    /**
     * @return the itsNo
     */
    public int getItsNo() {
        return itsNo;
    }

    /**
     * @param itsNo the itsNo to set
     */
    public void setItsNo(int itsNo) {
        this.itsNo = itsNo;
    }
    
    public String getSabilNo(){
        if(getSabil()!=null){
            return getSabil().getSabilNo().toString();
        }
        return "NA";
    }
}
