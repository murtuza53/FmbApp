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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author murtuza
 */
@Entity
@Table(name = "menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findByMenuNo", query = "SELECT m FROM Menu m WHERE m.menuNo = :menuNo"),
    @NamedQuery(name = "Menu.findByMenuName", query = "SELECT m FROM Menu m WHERE m.menuName = :menuName"),
    @NamedQuery(name = "Menu.findByDescription", query = "SELECT m FROM Menu m WHERE m.description = :description"),
    @NamedQuery(name = "Menu.findByRating", query = "SELECT m FROM Menu m WHERE m.rating = :rating"),
    @NamedQuery(name = "Menu.findByFeedback", query = "SELECT m FROM Menu m WHERE m.feedback = :feedback"),
    @NamedQuery(name = "Menu.findBySuggestions", query = "SELECT m FROM Menu m WHERE m.suggestions = :suggestions"),
    @NamedQuery(name = "Menu.findByDescription2", query = "SELECT m FROM Menu m WHERE m.description2 = :description2"),
    @NamedQuery(name = "Menu.findByDescription3", query = "SELECT m FROM Menu m WHERE m.description3 = :description3"),
    @NamedQuery(name = "Menu.findByRotiMenu", query = "SELECT m FROM Menu m WHERE m.rotiMenu = :rotiMenu"),
    @NamedQuery(name = "Menu.findByLambMenu", query = "SELECT m FROM Menu m WHERE m.lambMenu = :lambMenu"),
    @NamedQuery(name = "Menu.findByChickenMenu", query = "SELECT m FROM Menu m WHERE m.chickenMenu = :chickenMenu"),
    @NamedQuery(name = "Menu.findByNoThaali", query = "SELECT m FROM Menu m WHERE m.noThaali = :noThaali"),
    @NamedQuery(name = "Menu.findByVegMenu", query = "SELECT m FROM Menu m WHERE m.vegMenu = :vegMenu"),
    @NamedQuery(name = "Menu.findByMenuType", query = "SELECT m FROM Menu m WHERE m.menuType = :menuType")})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "menuNo")
    private Integer menuNo;
    @Size(max = 255)
    @Column(name = "menuName")
    private String menuName;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Double rating;
    @Size(max = 255)
    @Column(name = "feedback")
    private String feedback;
    @Size(max = 255)
    @Column(name = "suggestions")
    private String suggestions;
    @Size(max = 255)
    @Column(name = "description2")
    private String description2;
    @Size(max = 255)
    @Column(name = "description3")
    private String description3;
    @Column(name = "rotiMenu")
    private Boolean rotiMenu;
    @Column(name = "lambMenu")
    private Boolean lambMenu;
    @Column(name = "chickenMenu")
    private Boolean chickenMenu;
    @Column(name = "noThaali")
    private Boolean noThaali;
    @Column(name = "vegMenu")
    private Boolean vegMenu;
    @Size(max = 45)
    @Column(name = "menuType")
    private String menuType;
    //@OneToMany(mappedBy = "menuNo")
    //private List<ThaaliCalendar> thaaliCalendarList;
    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "menuNo")
    //private List<MenuAllocation> menuAllocationList;
    @JoinColumn(name = "menuCategoryNo", referencedColumnName = "menuCategoryNo")
    @ManyToOne
    private MenuCategory menuCategoryNo;

    @Transient
    public static final String MENU = "MENU";
    @Transient
    public static final String URS = "URS";
    @Transient
    public static final String EVENT = "EVENT";
    @Transient
    public static final String[] MENU_TYPES = new String[]{MENU, URS, EVENT};

    public Menu() {
    }

    public Menu(Integer menuNo) {
        this.menuNo = menuNo;
    }

    public Integer getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(Integer menuNo) {
        this.menuNo = menuNo;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public Boolean getRotiMenu() {
        return rotiMenu;
    }

    public void setRotiMenu(Boolean rotiMenu) {
        this.rotiMenu = rotiMenu;
    }

    public Boolean getLambMenu() {
        return lambMenu;
    }

    public void setLambMenu(Boolean lambMenu) {
        this.lambMenu = lambMenu;
    }

    public Boolean getChickenMenu() {
        return chickenMenu;
    }

    public void setChickenMenu(Boolean chickenMenu) {
        this.chickenMenu = chickenMenu;
    }

    public Boolean getNoThaali() {
        return noThaali;
    }

    public void setNoThaali(Boolean noThaali) {
        this.noThaali = noThaali;
    }

    public Boolean getVegMenu() {
        return vegMenu;
    }

    public void setVegMenu(Boolean vegMenu) {
        this.vegMenu = vegMenu;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /*@XmlTransient
    public List<ThaaliCalendar> getThaaliCalendarList() {
        return thaaliCalendarList;
    }

    public void setThaaliCalendarList(List<ThaaliCalendar> thaaliCalendarList) {
        this.thaaliCalendarList = thaaliCalendarList;
    }*/

    //@XmlTransient
    /*public List<MenuAllocation> getMenuAllocationList() {
        return menuAllocationList;
    }

    public void setMenuAllocationList(List<MenuAllocation> menuAllocationList) {
        this.menuAllocationList = menuAllocationList;
    }*/

    @Override
    public int hashCode() {
        return menuNo;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        return menuNo==other.getMenuNo();
    }

    @Override
    public String toString() {
        return getMenuName() + " [" + menuNo + "]";
    }

    /**
     * @return the menuCategoryNo
     */
    public MenuCategory getMenuCategoryNo() {
        return menuCategoryNo;
    }

    /**
     * @param menuCategoryNo the menuCategoryNo to set
     */
    public void setMenuCategoryNo(MenuCategory menuCategoryNo) {
        this.menuCategoryNo = menuCategoryNo;
    }
    
}
