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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "menuallocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MenuAllocation.findAll", query = "SELECT m FROM MenuAllocation m"),
    @NamedQuery(name = "MenuAllocation.findByMenuallocationNo", query = "SELECT m FROM MenuAllocation m WHERE m.menuallocationNo = :menuallocationNo"),
    @NamedQuery(name = "MenuAllocation.findByDescription", query = "SELECT m FROM MenuAllocation m WHERE m.description = :description"),
    @NamedQuery(name = "MenuAllocation.findByRating", query = "SELECT m FROM MenuAllocation m WHERE m.rating = :rating")})
public class MenuAllocation implements Serializable, Comparable<MenuAllocation> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "menuallocationNo")
    private Integer menuallocationNo;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private double rating;
    @JoinColumn(name = "calendarNo", referencedColumnName = "calendarNo")
    @ManyToOne(optional = false)
    private ThaaliCalendar calendarNo;
    @JoinColumn(name = "menuNo", referencedColumnName = "menuNo")
    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    private Menu menuNo;
    @Column(name = "orderTimeStamp")
    private long orderTimeStamp;

    public MenuAllocation() {
    }

    public MenuAllocation(Integer menuallocationNo) {
        this.menuallocationNo = menuallocationNo;
    }

    public Integer getMenuallocationNo() {
        return menuallocationNo;
    }

    public void setMenuallocationNo(Integer menuallocationNo) {
        this.menuallocationNo = menuallocationNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ThaaliCalendar getCalendarNo() {
        return calendarNo;
    }

    public void setCalendarNo(ThaaliCalendar calendarNo) {
        this.calendarNo = calendarNo;
    }

    public Menu getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(Menu menuNo) {
        this.menuNo = menuNo;
    }

    @Override
    public int hashCode() {
        return menuallocationNo;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuAllocation)) {
            return false;
        }
        MenuAllocation other = (MenuAllocation) object;
        return menuallocationNo==other.getMenuallocationNo();
    }

    @Override
    public String toString() {
        return "org.famb.entity.MenuAllocation[ menuallocationNo=" + menuallocationNo + " ]";
    }

    /**
     * @return the orderTimeStamp
     */
    public long getOrderTimeStamp() {
        return orderTimeStamp;
    }

    /**
     * @param orderTimeStamp the orderTimeStamp to set
     */
    public void setOrderTimeStamp(long orderTimeStamp) {
        this.orderTimeStamp = orderTimeStamp;
    }
    
    @Override
    public int compareTo(MenuAllocation o) {
        return new Long(orderTimeStamp).compareTo(o.getOrderTimeStamp());
    }
}
