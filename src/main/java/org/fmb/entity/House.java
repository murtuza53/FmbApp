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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Burhani152
 */
@Entity
@Table(name = "house")
@XmlRootElement
public class House implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "houseid")
    private Integer houseid;
    @Size(max = 45)
    @Column(name = "housecode")
    private String housecode;
    @Size(max = 45)
    @Column(name = "housename")
    private String housename;
    @Size(max = 45)
    @Column(name = "housetype")
    private String housetype;       //F-Family, C-Club
    @Size(max = 45)
    @Column(name = "sector")
    private String sector;
    @Size(max = 45)
    @Column(name = "flatno")
    private String flatno;
    @Size(max = 45)
    @Column(name = "buildingno")
    private String buildingno;
    @Size(max = 45)
    @Column(name = "roadno")
    private String roadno;
    @Size(max = 45)
    @Column(name = "blockno")
    private String blockno;
    @Size(max = 45)
    @Column(name = "areaname")
    private String areaname;
    @Size(max = 45)
    @Column(name = "telno")
    private String telno;
    @Size(max = 255)
    @Column(name = "gpslocation")
    private String gpslocation;
    @Size(max = 255)
    @Column(name = "gpslink")
    private String gpslink;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    //@OneToMany(mappedBy = "itsNo")
    //private List<ItsMaster> itsList;

    @Transient 
    private StringBuffer address;
    
    @Transient
    private String style;
    
    public House() {

    }

    public House(String housename) {
        this.housename = housename;
    }

    public House(Integer houseid, String housename, String housetype) {
        this.houseid = houseid;
        this.housename = housename;
        this.housetype = housetype;
    }
    
    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the houseid
     */
    public Integer getHouseid() {
        return houseid;
    }

    /**
     * @param houseid the houseid to set
     */
    public void setHouseid(Integer houseid) {
        this.houseid = houseid;
    }

    /**
     * @return the housecode
     */
    public String getHousecode() {
        return housecode;
    }

    /**
     * @param housecode the housecode to set
     */
    public void setHousecode(String housecode) {
        this.housecode = housecode;
    }

    /**
     * @return the housename
     */
    public String getHousename() {
        return housename;
    }

    /**
     * @param housename the housename to set
     */
    public void setHousename(String housename) {
        this.housename = housename;
    }

    /**
     * @return the housetype
     */
    public String getHousetype() {
        return housetype;
    }

    /**
     * @param housetype the housetype to set
     */
    public void setHousetype(String housetype) {
        this.housetype = housetype;
    }

    /**
     * @return the sector
     */
    public String getSector() {
        return sector;
    }

    /**
     * @param sector the sector to set
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * @return the flatno
     */
    public String getFlatno() {
        return flatno;
    }

    /**
     * @param flatno the flatno to set
     */
    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }

    /**
     * @return the buildingno
     */
    public String getBuildingno() {
        return buildingno;
    }

    /**
     * @param buildingno the buildingno to set
     */
    public void setBuildingno(String buildingno) {
        this.buildingno = buildingno;
    }

    /**
     * @return the roadno
     */
    public String getRoadno() {
        return roadno;
    }

    /**
     * @param roadno the roadno to set
     */
    public void setRoadno(String roadno) {
        this.roadno = roadno;
    }

    /**
     * @return the blockno
     */
    public String getBlockno() {
        return blockno;
    }

    /**
     * @param blockno the blockno to set
     */
    public void setBlockno(String blockno) {
        this.blockno = blockno;
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
     * @return the telno
     */
    public String getTelno() {
        return telno;
    }

    /**
     * @param telno the telno to set
     */
    public void setTelno(String telno) {
        this.telno = telno;
    }

    /**
     * @return the gpslocation
     */
    public String getGpslocation() {
        return gpslocation;
    }

    /**
     * @param gpslocation the gpslocation to set
     */
    public void setGpslocation(String gpslocation) {
        this.gpslocation = gpslocation;
    }

    /**
     * @return the gpslink
     */
    public String getGpslink() {
        return gpslink;
    }

    /**
     * @param gpslink the gpslink to set
     */
    public void setGpslink(String gpslink) {
        this.gpslink = gpslink;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public void setAddress(String add){
        this.address = new StringBuffer(add);
    }
    
    public String getAddress(){
        //if(houseid>10000){   //it is ITS No
        //    return "";
        //}
        
        if(address==null){
            address = new StringBuffer();
            address.append("Flat/Vila: ");
            if(flatno!=null){
                address.append(flatno);
            }
            address.append(", ");
            
            address.append("Building: ");
            if(buildingno!=null){
                address.append(buildingno);
            }
            address.append(", ");
            
            address.append("Road: ");
            if(roadno!=null){
                address.append(roadno);
            }
            address.append(", ");
            
            address.append("Block: ");
            if(roadno!=null){
                address.append(blockno);
            }
            address.append(", ");
            
            address.append("Area: ");
            if(roadno!=null){
                address.append(areaname);
            }
        }
        return address.toString();
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
    public boolean isItsNo(){
        if(houseid>10000000){
            return true;
        }
        return false;
    }
}
