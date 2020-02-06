/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity.famb;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author murtuza *
 */
@Entity
@Table(name = "thaalicalendar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ThaaliCalendar.findAll", query = "SELECT t FROM ThaaliCalendar t")
    ,
    @NamedQuery(name = "ThaaliCalendar.findByCalendarNo", query = "SELECT t FROM ThaaliCalendar t WHERE t.calendarNo = :calendarNo")
    ,
    @NamedQuery(name = "ThaaliCalendar.findByThaalidate", query = "SELECT t FROM ThaaliCalendar t WHERE t.thaalidate = :thaalidate")
    ,
    @NamedQuery(name = "ThaaliCalendar.findByRating", query = "SELECT t FROM ThaaliCalendar t WHERE t.rating = :rating")
    ,
    @NamedQuery(name = "ThaaliCalendar.findByFeedback", query = "SELECT t FROM ThaaliCalendar t WHERE t.feedback = :feedback")
    ,
    @NamedQuery(name = "ThaaliCalendar.findBySuggestions", query = "SELECT t FROM ThaaliCalendar t WHERE t.suggestions = :suggestions")
    ,
    @NamedQuery(name = "ThaaliCalendar.findByDescription", query = "SELECT t FROM ThaaliCalendar t WHERE t.description = :description")
    ,
    @NamedQuery(name = "ThaaliCalendar.findByDescription2", query = "SELECT t FROM ThaaliCalendar t WHERE t.description2 = :description2")
    ,
    @NamedQuery(name = "ThaaliCalendar.findByDescription3", query = "SELECT t FROM ThaaliCalendar t WHERE t.description3 = :description3")
    ,
    @NamedQuery(name = "ThaaliCalendar.findByRotiMenu", query = "SELECT t FROM ThaaliCalendar t WHERE t.rotiMenu = :rotiMenu")})
public class ThaaliCalendar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "calendarNo")
    private Integer calendarNo;
    @Column(name = "thaalidate")
    @Temporal(TemporalType.DATE)
    private Date thaalidate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private double rating;
    @Size(max = 255)
    @Column(name = "feedback")
    private String feedback;
    @Size(max = 255)
    @Column(name = "suggestions")
    private String suggestions;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 255)
    @Column(name = "description2")
    private String description2;        //booking status - BOOKED or NOT_BOOKED
    @Size(max = 255)
    @Column(name = "description3")
    private String description3;
    @Column(name = "rotiMenu")
    private boolean rotiMenu;
    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "calendarNo")
    //private List<ThaaliCalendarFeedback> thaaliCalendarFeedbackList;
//    //@JoinColumn(name = "menuNo", referencedColumnName = "menuNo")
//    //@ManyToOne
//    //private Menu menuNo;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "calendarNo")
    private List<MenuAllocation> menuAllocationList;

    //@OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    //@JoinColumn(name = "rsvpId")
    //private Rsvp rsvp;
    @Transient
    private String uri;
    @Transient
    private Integer feedbackCount;
    @Transient
    private String formattedGregDate;
    @Transient
    private String formattedHijriDate;
    @Transient
    private String status;
    @Transient
    private Integer booking;
    @Transient
    private Integer tiffinCount;
    @Transient 
    private Integer thaaliNo;
    @Transient
    private List<String> bookingImages = new LinkedList<String>();
    @Transient
    private Integer ratingByIts;
    @Transient
    private boolean menuOrdered = false;
    
    public ThaaliCalendar() {
    }

    public ThaaliCalendar(Integer calendarNo) {
        this.calendarNo = calendarNo;
    }

    public Integer getCalendarNo() {
        return calendarNo;
    }

    public void setCalendarNo(Integer calendarNo) {
        this.calendarNo = calendarNo;
    }

    public Date getThaalidate() {
        return thaalidate;
    }

    public void setThaalidate(Date thaalidate) {
        this.thaalidate = thaalidate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean getRotiMenu() {
        return rotiMenu;
    }

    public void setRotiMenu(boolean rotiMenu) {
        this.rotiMenu = rotiMenu;
    }

    //@XmlTransient
    /*public List<ThaaliCalendarFeedback> getThaaliCalendarFeedbackList() {
        return thaaliCalendarFeedbackList;
    }

    public void setThaaliCalendarFeedbackList(List<ThaaliCalendarFeedback> thaaliCalendarFeedbackList) {
        this.thaaliCalendarFeedbackList = thaaliCalendarFeedbackList;
    }*/

 /*public Menu getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(Menu menuNo) {
        this.menuNo = menuNo;
    }*/
    public List<MenuAllocation> getMenuAllocationList() {
        if(menuAllocationList!=null && menuOrdered==false){
            Collections.sort(menuAllocationList);
            menuOrdered = true;
        }
        return menuAllocationList;
    }

    public void setMenuAllocationList(List<MenuAllocation> menuAllocationList) {
        this.menuAllocationList = menuAllocationList;
    }

    @Override
    public int hashCode() {
        return calendarNo;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ThaaliCalendar)) {
            return false;
        }
        ThaaliCalendar other = (ThaaliCalendar) object;

        return this.calendarNo == other.getCalendarNo();
    }

    @Override
    public String toString() {
        return "org.famb.entity.ThaaliCalendar[ calendarNo=" + calendarNo + " ]";
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the feedbackCount
     */
    public Integer getFeedbackCount() {
        return feedbackCount;
    }

    /**
     * @param feedbackCount the feedbackCount to set
     */
    public void setFeedbackCount(Integer feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public String getMenuNames() {
        StringBuilder builder = new StringBuilder();
        if (getMenuAllocationList() != null) {
            for (int i = 0; i < getMenuAllocationList().size(); i++) {
                MenuAllocation ma = getMenuAllocationList().get(i);
                if (i == 0) {
                    builder.append(ma.getMenuNo().getMenuName());
                } else {
                    builder.append(", " + ma.getMenuNo().getMenuName());
                }
            }
        } else {
            builder.append("No Thaali");
        }
        return builder.toString();
    }

    public String getMenuNamesForCalendar() {
        StringBuilder builder = new StringBuilder();
        if (getMenuType() != null) {
            builder.append(getMenuType() + ":\n");
        }
        if (isMenuType()) {
            if (getMenuAllocationList() != null) {
                for (int i = 0; i < getMenuAllocationList().size(); i++) {
                    MenuAllocation ma = getMenuAllocationList().get(i);
                    if (i == 0) {
                        builder.append(ma.getMenuNo().getMenuName());
                    } else {
                        builder.append("\n" + ma.getMenuNo().getMenuName());
                    }
                }
                if (getRotiMenu()) {
                    builder.append("\nRoti");
                }
            }
        } else if (getMenuAllocationList() != null) {
            for (int i = 0; i < getMenuAllocationList().size(); i++) {
                MenuAllocation ma = getMenuAllocationList().get(i);
                if (i == 0) {
                    builder.append(ma.getMenuNo().getMenuName());
                } else {
                    builder.append("\n" + ma.getMenuNo().getMenuName());
                }
                if (ma.getMenuNo().getNoThaali()) {
                    builder.append("\n NO THAALI");
                }
            }
        } else {
            builder.append("NO THAALI");
        }
        return builder.toString();
    }

    public String getDescriptions() {
        StringBuilder builder = new StringBuilder();
        if (getMenuAllocationList() != null) {
            for (int i = 0; i < getMenuAllocationList().size(); i++) {
                MenuAllocation ma = getMenuAllocationList().get(i);
                if (i == 0) {
                    if (ma.getMenuNo().getDescription() != null) {
                        builder.append(ma.getMenuNo().getDescription());
                    }
                } else if (ma.getMenuNo().getDescription() != null) {
                    builder.append("\n" + ma.getMenuNo().getDescription());
                }
            }
        }
        return builder.toString();
    }

    public boolean isChickenMenu() {
        if (getMenuAllocationList() == null) {
            return false;
        }
        for (MenuAllocation ma : getMenuAllocationList()) {
            if (ma.getMenuNo().getChickenMenu()) {
                return true;
            }
        }
        return false;
    }

    public boolean isLambMenu() {
        if (getMenuAllocationList() == null) {
            return false;
        }
        for (MenuAllocation ma : getMenuAllocationList()) {
            if (ma.getMenuNo().getLambMenu()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVegMenu() {
        if (getMenuAllocationList() == null) {
            return false;
        }
        for (MenuAllocation ma : getMenuAllocationList()) {
            if (ma.getMenuNo().getVegMenu()) {
                return true;
            }
        }
        return false;
    }

    public boolean isNoThaali() {
        if (getMenuAllocationList() == null) {
            return false;
        }
        for (MenuAllocation ma : getMenuAllocationList()) {
            if (ma.getMenuNo().getNoThaali()) {
                return true;
            }
        }
        if (getDescription().equalsIgnoreCase("Thaali Not Updated")) {
            return true;
        }
        return false;
    }

    public int getSingleMenuId() {
        if (getMenuAllocationList() == null) {
            return 0;
        }

        if (getMenuAllocationList().size() == 0) {
            return 0;
        }

        return getMenuAllocationList().get(0).getMenuNo().getMenuNo();
    }

    public int[] getAllMenuId() {
        if (getMenuAllocationList() == null) {
            return null;
        }

        if (getMenuAllocationList().size() == 0) {
            return null;
        }

        int[] ids = new int[getMenuAllocationList().size()];
        for (int i = 0; i < getMenuAllocationList().size(); i++) {
            ids[i] = getMenuAllocationList().get(i).getMenuNo().getMenuNo();
        }
        return ids;
    }

    public boolean isMenuType() {
        if (getMenuAllocationList() == null) {
            return false;
        }

        for (MenuAllocation ma : getMenuAllocationList()) {
            return ma.getMenuNo().getMenuType().equalsIgnoreCase(Menu.MENU);
        }
        return false;
    }

    public boolean isUrsType() {
        if (getMenuAllocationList() == null) {
            return false;
        }

        for (MenuAllocation ma : getMenuAllocationList()) {
            return ma.getMenuNo().getMenuType().equalsIgnoreCase(Menu.URS);
        }
        return false;
    }

    public boolean isEventType() {
        if (getMenuAllocationList() == null) {
            return false;
        }

        for (MenuAllocation ma : getMenuAllocationList()) {
            return ma.getMenuNo().getMenuType().equalsIgnoreCase(Menu.EVENT);
        }
        return false;
    }

    public String getMenuType() {
        if (getMenuAllocationList() == null) {
            return null;
        }

        for (MenuAllocation ma : getMenuAllocationList()) {
            return ma.getMenuNo().getMenuType();
        }
        return null;
    }

    /**
     * @return the formattedGregDate
     */
    public String getFormattedGregDate() {
        return formattedGregDate;
    }

    /**
     * @param formattedGregDate the formattedGregDate to set
     */
    public void setFormattedGregDate(String formattedGregDate) {
        this.formattedGregDate = formattedGregDate;
    }

    /**
     * @return the formattedHijriDate
     */
    public String getFormattedHijriDate() {
        return formattedHijriDate;
    }

    /**
     * @param formattedHijriDate the formattedHijriDate to set
     */
    public void setFormattedHijriDate(String formattedHijriDate) {
        this.formattedHijriDate = formattedHijriDate;
    }

    /**
     * @return the rsvp
     */
    //public Rsvp getRsvp() {
    //    return rsvp;
    //}

    /**
     * @param rsvp the rsvp to set
     */
    //public void setRsvp(Rsvp rsvp) {
    //    this.rsvp = rsvp;
    //}

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the booking
     */
    public Integer getBooking() {
        return booking;
    }

    /**
     * @param booking the booking to set
     */
    public void setBooking(Integer booking) {
        this.booking = booking;
    }

    /**
     * @return the tiffinCount
     */
    public Integer getTiffinCount() {
        return tiffinCount;
    }

    /**
     * @param tiffinCount the tiffinCount to set
     */
    public void setTiffinCount(Integer tiffinCount) {
        this.tiffinCount = tiffinCount;
    }

    /**
     * @return the thaaliNo
     */
    public Integer getThaaliNo() {
        return thaaliNo;
    }

    /**
     * @param thaaliNo the thaaliNo to set
     */
    public void setThaaliNo(Integer thaaliNo) {
        this.thaaliNo = thaaliNo;
    }

    /**
     * @return the bookingImages
     */
    public List<String> getBookingImages() {
        return bookingImages;
    }

    /**
     * @param bookingImages the bookingImages to set
     */
    public void setBookingImages(List<String> bookingImages) {
        this.bookingImages = bookingImages;
    }

    public void updateTiffinBooking(int count){
        booking  = count;
        getBookingImages().clear();
        for (int i = 0; i < tiffinCount; i++) {
            if (i < booking) {
                getBookingImages().add("/images/famb/tiffin-filled-green.png");
            } else {
                getBookingImages().add("/images/famb/tiffin-filled-gray.png");
            }
        }
    }

    /**
     * @return the ratingByIts
     */
    public int getRatingByIts() {
        return ratingByIts;
    }

    /**
     * @param ratingByIts the ratingByIts to set
     */
    public void setRatingByIts(int ratingByIts) {
        this.ratingByIts = ratingByIts;
    }
}
