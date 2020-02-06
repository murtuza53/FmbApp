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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "menucategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MenuCategory.findAll", query = "SELECT m FROM MenuCategory m"),
    @NamedQuery(name = "MenuCategory.findByMenuCategoryNo", query = "SELECT m FROM MenuCategory m WHERE m.menuCategoryNo = :menuCategoryNo")
})
public class MenuCategory implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "menuCategoryNo")
    private Integer menuCategoryNo;
    @Size(max = 255)
    @Column(name = "categoryName")
    private String categoryName;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "required")
    private String required;
    @Size(max = 255)
    @Column(name = "instructions")
    private String instructions;
    @Column(name = "allowedCount")
    private Integer allowedCount = 1;
    @Column(name = "menuOrder")
    private Integer menuOrder;
    //@OneToMany(mappedBy = "menuNo")
    //private List<ThaaliCalendar> thaaliCalendarList;
    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "menuNo")
    //private List<MenuAllocation> menuAllocationList;

    public MenuCategory() {
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
     * @return the menuCategoryNo
     */
    public Integer getMenuCategoryNo() {
        return menuCategoryNo;
    }

    /**
     * @param menuCategoryNo the menuCategoryNo to set
     */
    public void setMenuCategoryNo(Integer menuCategoryNo) {
        this.menuCategoryNo = menuCategoryNo;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    /**
     * @return the required
     */
    public String getRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(String required) {
        this.required = required;
    }

    /**
     * @return the instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions the instructions to set
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    @Override
    public int hashCode() {
        return getMenuCategoryNo();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuCategory)) {
            return false;
        }
        MenuCategory other = (MenuCategory) object;
        return getMenuCategoryNo()==other.getMenuCategoryNo();
    }

    @Override
    public String toString() {
        return getCategoryName()+ " [" + menuCategoryNo + "]";
    }

    /**
     * @return the allowedCount
     */
    public Integer getAllowedCount() {
        return allowedCount;
    }

    /**
     * @param allowedCount the allowedCount to set
     */
    public void setAllowedCount(Integer allowedCount) {
        this.allowedCount = allowedCount;
    }

    /**
     * @return the menuOrder
     */
    public Integer getMenuOrder() {
        return menuOrder;
    }

    /**
     * @param menuOrder the menuOrder to set
     */
    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }
    
}
