package org.ejms.controller.famb;

import com.hijri.HijriDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ejms.controller.AbstractController;
import org.ejms.entity.ItsMaster;
import org.ejms.entity.famb.Menu;
import org.ejms.entity.famb.MenuAllocation;
import org.ejms.entity.famb.ThaaliAllocation;
import org.ejms.entity.famb.ThaaliCalendar;
import org.ejms.repo.ItsMasterRepository;
import org.ejms.repo.famb.ItsReaderInfoRepository;
import org.ejms.repo.famb.MenuAllocationRepository;
import org.ejms.repo.famb.MenuRepository;
import org.ejms.repo.famb.ThaaliAllocationRepository;
import org.ejms.repo.famb.ThaaliCalendarRepository;
import org.ejms.repo.famb.ThaaliStatusRepository;
import org.ejms.util.DateUtil;
import org.ejms.util.JsfUtil;
import org.ejms.util.SystemConfig;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

@Named(value = "thaaliCalendarController")
@ViewScoped
public class ThaaliCalendarController extends AbstractController<ThaaliCalendar> {

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy HH:mm a");

    int calendarEntries = 14;
    int daysOffset = 2;
    //@Inject
    private ThaaliCalendarRepository ejbFacade;

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MenuController menuNoController;
    @Autowired
    private MenuRepository menuFacade;
    @Autowired
    private MenuAllocationRepository maFacade;
    @Autowired
    private ItsMasterRepository itsFacade;
    @Autowired
    private ItsReaderInfoRepository itsReaderFacade;
    @Autowired
    private ThaaliAllocationRepository taf;
    @Autowired
    private ThaaliStatusRepository tsFacade;

    private DualListModel<Menu> menus;
    private String menuType = "MENU";
    private Menu selectedMenu;

    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private LazyScheduleModel lazyEventModel;
    private List<ThaaliCalendar> calendarList;
    //private Rsvp rsvp = new Rsvp();
    private boolean rsvpNeeded;
    private boolean sendEmail = false;
    private Date toDate = DateUtil.endOfMonth(new Date());
    private Date fromDate = DateUtil.startOfMonth(toDate);
    private Hashtable<Long, Long> orderTable = new Hashtable<Long, Long>();
    private long orderCount = 1;
    private UploadedFile uploadedFile;

    public ThaaliCalendarController(ThaaliCalendarRepository repo) {
        // Inform the Abstract parent controller of the concrete ThaaliCalendar Entity
        super(ThaaliCalendar.class, repo);
        this.ejbFacade = repo;
    }

    @PostConstruct
    public void init() {
        //eventModel = new DefaultScheduleModel();

        //eventModel.addEvent(new DefaultScheduleEvent("Meeting with Horizon \n Nice but no time", new Date(), new Date(), true));
        //Date d1 = getRandomDate(new Date());
        //eventModel.addEvent(new DefaultScheduleEvent("Too much work\nNice but no time\nWhat to do?", d1, d1, true));
        //eventModel.addEvent(new DefaultScheduleEvent("Repeat", d1, d1));
        refreshSchedule();
        orderCount = 1;
    }

    public void prepareRsvp(ThaaliCalendar tc) {
        super.prepareCreate(null);
        super.setSelected(tc);
        //rsvp = tc.getRsvp();
        //if (rsvp == null) {
        //    rsvp = new Rsvp(tc.getThaalidate());
        //    rsvp.setVenue("Burhani Masjid");
        //    rsvp.setScanningStatus("Open");
        //    tc.setRsvp(rsvp);
        //}
    }

    public List<ThaaliCalendar> findThaaliCalendarByDate(Date date, int itsNo) {
        if (calendarList == null) {
            System.out.println("findThaaliCalendarByDate: " + date + " " + daysOffset);
            calendarList = findThaaliCalendarByDate(date, daysOffset, itsNo, calendarEntries);

            System.out.println(calendarList.size() + " calendar entry found");
        }
        return calendarList;
    }
    
    public List<ThaaliCalendar> findThaaliCalendarByDate(Date date, int daysOffset, int itsNo, int calendarEntries){
        ItsMaster its = itsFacade.getOne(itsNo);
        return findThaaliCalendarByDate(date, daysOffset, its, calendarEntries);
    }
    
    public List<ThaaliCalendar> findThaaliCalendarByDate(Date date, int daysOffset, ItsMaster its, int calendarEntries) {
        int itsNo = its.getItsNo();
        if (date == null) {
            date = new Date();
        }
        if (daysOffset == 0) {
            daysOffset = 1;
        }
        date = new Date(DateUtil.addDays(date.getTime(), daysOffset * -1));
        ThaaliAllocation ta = taf.findThaaliAllocationByItsNo(itsNo);

        List<ThaaliCalendar> list = new LinkedList<ThaaliCalendar>();
        for (int i = 0; i < calendarEntries; i++) {
            ThaaliCalendar tc = ejbFacade.findThaaliCalendarByDateSingle(date);
            if (tc == null) {
                tc = new ThaaliCalendar();
                tc.setDescription("No Thaali on this day");
                tc.setThaalidate(date);
                //tc.setUri(MenuController.getFileNamesNoThaali()[1]);
            } else {
                //load rating here//
                //tc.setRating(findThaaliRating(tc.getCalendarNo()));

                //ThaaliCalendarFeedback tcf = findThaaliCalendarFeedbackByItsAndCalendarNo(its.getHofId(), tc.getCalendarNo());
                //if (tcf != null) {
                //    tc.setDescription3("Your Rating: " + tcf.getRating());
                //    tc.setRatingByIts(tcf.getRating());
                //} else {
                //    tc.setDescription3("Rate this Thaali");
                //}
            }
            int booked = 0;
            if (ta != null) {
                tc.setThaaliNo(ta.getThaaliNo().getThaaliNo());
                tc.setTiffinCount(ta.getThaaliNo().getNumberOfTifin());
                //booked = tsFacade.getTiffinBookedCount(tc.getThaaliNo(), date);
                //System.out.println(tc.getThaalidate() + "\t" + tc.getThaaliNo() + ": " + booked);
                tc.updateTiffinBooking(booked);
            }
            if (booked > 0) {
                tc.setDescription2("BOOKED");
                System.out.println(tc.getThaalidate() + ": " + "BOOKED");
            } else {
                tc.setDescription2("NOT_BOOKED");
                System.out.println(tc.getThaalidate() + ": " + "NOT_BOOKED");
            }
            list.add(tc);
            tc.setFormattedGregDate(SystemConfig.DATE_FORMAT.format(tc.getThaalidate()));
            HijriDate hd = HijriDate.getInstance(tc.getThaalidate());
            tc.setFormattedHijriDate(hd.getDay() + " " + hd.getMonthName());
            date = new Date(DateUtil.addDays(date.getTime(), 1));
        }
        return list;
    }

    public boolean isVisible(Date date, ThaaliCalendar tc) {
        if (tc.isMenuType()) {
            return false;
        } else if (tc.getMenuNamesForCalendar().equalsIgnoreCase("NO THAALI")) {
            return false;
        }
        return DateUtil.isFuture(new Date(), date) || DateUtil.isToday(date);
    }

    //public boolean isRsvp(Date date, ThaaliCalendar tc) {
    //    if (tc.getRsvp() != null) {
    //        return true;
    //    }
    //    return false;
    //}

    public List<ThaaliCalendar> findThaaliCalendarBetweenDateTypeUrsOrEvent(Date fromDate, Date toDate){
        List<ThaaliCalendar> list = findThaaliCalendarBetweenDate(fromDate, toDate);
        List<ThaaliCalendar> retList = new LinkedList();
        if(list!=null){
            for(ThaaliCalendar tc: list){
                if(tc.isEventType() || tc.isUrsType()){
                    retList.add(tc);
                }
            }
        }
        return retList;
    }
    
    public List<ThaaliCalendar> findThaaliCalendarBetweenDate(Date fromDate, Date toDate) {
        calendarList = new LinkedList<ThaaliCalendar>();
        for (Date date = fromDate; DateUtil.isFuture(date, toDate);
                date = new Date(DateUtil.addDays(date.getTime(), 1))) {
            ThaaliCalendar tc = ejbFacade.findThaaliCalendarByDateSingle(date);
            if (tc.isMenuType()) {
                tc.setStatus("N/A");
            } /*else {
                if (tc.getRsvp() == null) {
                    if (DateUtil.isFuture(new Date(), date)) {
                        if (tc.getMenuNamesForCalendar().equalsIgnoreCase("NO THAALI")) {
                            tc.setStatus("RSVP not Available");
                        } else if (tc.isUrsType() || tc.isEventType()) {
                            tc.setStatus("RSVP Available");
                        }
                    } else {
                        tc.setStatus("RSVP Available");
                    }
                } else {
                    tc.setStatus(tc.getRsvp().getRSVPStatus(new Date()));
                    tc.getRsvp().setTotalLog(rsvpLogFacade.findRsvpLogCount(tc.getRsvp().getRsvpId()));
                }
            }*/
            calendarList.add(tc);
        }
        return calendarList;
    }

    public List<ThaaliCalendar> getCalendarList() {
        if (calendarList == null) {
            refreshRsvp();
        }
        return calendarList;
    }

    public void refreshRsvp() {
        findThaaliCalendarBetweenDate(getFromDate(), getToDate());
    }

    public void refreshSchedule_Old() {
        eventModel = new DefaultScheduleModel();

        Date date = new Date();
        Date start = DateUtil.startOfMonth(DateUtil.getMonth(date), DateUtil.getYear(date));
        Date end = DateUtil.endOfMonth(DateUtil.getMonth(date), DateUtil.getYear(date));
        boolean spellMonth = true;
        for (Date d = start; DateUtil.isFuture(d, end); d = new Date(DateUtil.addDays(d.getTime(), 1))) {
            ThaaliCalendar tc = ejbFacade.findThaaliCalendarByDateSingle(d);
            if (tc != null) {
                ScheduleEvent e = makeEvent(tc, d);
                if (e != null) {
                    System.out.println("EVENT_" + e.getData() + ": " + e.getId());
                    eventModel.addEvent(e);
                }
            }
            eventModel.addEvent(makeHijriDate(d, spellMonth, "hijridate"));
            if (spellMonth) {
                spellMonth = false;
            }
        }

    }

    public void refreshSchedule() {
        lazyEventModel = new LazyScheduleModel() {

            @Override
            public void loadEvents(Date start, Date end) {
                boolean spellMonth = true;
                int status = 0;
                for (Date d = start; DateUtil.isFuture(d, end); d = new Date(DateUtil.addDays(d.getTime(), 1))) {
                    ThaaliCalendar tc = ejbFacade.findThaaliCalendarByDateSingle(d);
                    if (tc != null) {
                        ScheduleEvent e = makeEvent(tc, d);
                        if (e != null) {
                            System.out.println("EVENT_" + e.getData() + ": " + e.getId());
                            addEvent(e);
                        }
                    }
                    int day = DateUtil.getDay(d);
                    if (day == 1) {
                        status++;
                    }
                    String style = "hijridate-other-month";
                    if (status == 1) {
                        style = "hijridate";
                    }
                    addEvent(makeHijriDate(d, spellMonth, style));
                    if (spellMonth) {
                        spellMonth = false;
                    }
                }
            }
        };
    }

    public ScheduleEvent makeHijriDate(Date d, boolean spellMonth, String style) {
        Date ed = DateUtil.startOfDay(d);
        DefaultScheduleEvent e = new DefaultScheduleEvent(HijriDate.getInstance(d).getCalendarDate(spellMonth), ed, ed);
        e.setStyleClass(style);
        return e;
    }

    public ScheduleEvent makeEvent(ThaaliCalendar tc, Date d) {
        DefaultScheduleEvent e = null;
        if (tc.isMenuType()) {
            if (tc.getMenuAllocationList() != null && tc.getMenuAllocationList().size() > 0) {
                String text = tc.getMenuNamesForCalendar();
                if (text == null || text.trim().length() == 0) {
                    text = "Broken";
                }
                e = new DefaultScheduleEvent(text, d, d, false);
                e.setData(tc);
            }
        } else if (tc.isUrsType()) {
            if (tc.getMenuAllocationList() != null && tc.getMenuAllocationList().size() > 0) {
                String text = wrapString(tc.getMenuNamesForCalendar(), "\n", 30);
                if (text == null || text.trim().length() == 0) {
                    text = "Broken";
                }
                e = new DefaultScheduleEvent(text, d, d, false);
                e.setId("10" + tc.getCalendarNo());
                e.setData(tc);
                e.setStyleClass("urs");
            }
        } else if (tc.getMenuAllocationList() != null && tc.getMenuAllocationList().size() > 0) {
            String text = tc.getMenuNamesForCalendar();
            if (text == null || text.trim().length() == 0) {
                text = "Broken";
            }
            e = new DefaultScheduleEvent(text, d, d, false);
            e.setData(tc);
            e.setStyleClass("event");
        }
        return e;
    }

    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random() * 5)) + 1);    //set random day of month

        return date.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public LazyScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        ThaaliCalendar tc = ejbFacade.findThaaliCalendarByDateSingle(event.getStartDate());
        DefaultScheduleEvent evt = new DefaultScheduleEvent("", event.getStartDate(), event.getEndDate());
        evt.setData(tc);
        event = evt;
        prepareThaaliCalendar();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        DefaultScheduleEvent evt = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        ThaaliCalendar tc = ejbFacade.findThaaliCalendarByDateSingle(evt.getStartDate());
        evt.setData(tc);
        event = evt;
        prepareThaaliCalendar();
    }

    public void prepareThaaliCalendar() {
        orderCount = 1;
        if (event.getData() == null) {
            super.prepareCreate(null);
            Date date = new Date();
            if (event.getStartDate() != null) {
                date = event.getStartDate();
            }
            getSelected().setThaalidate(date);
        } else {
            setSelected((ThaaliCalendar) event.getData());
        }
        /*rsvp = getSelected().getRsvp();
        if(rsvp!=null){
            rsvpNeeded = true;
        } else {
            rsvpNeeded = false;
            rsvp = new Rsvp(getSelected().getThaalidate());
        }*/
        prepareMenuPickList();
    }

    public int deleteMenuAllocation(int calendarNo) {
        int count = em.createQuery("DELETE FROM "
                + "MenuAllocation ma WHERE ma.calendarNo.calendarNo=:calendarNo")
                .setParameter("calendarNo", calendarNo)
                .executeUpdate();

        return count;
    }

    public void saveThaaliCalendar(ActionEvent event) {
        System.out.println("saveThaaliCalendar: " + getSelected());
        if (getSelected().getCalendarNo() == null || getSelected().getCalendarNo()==0) {
            getSelected().setMenuAllocationList(getSelectedMenuAllocation());
            ////if(!menuType.equalsIgnoreCase("MENU") && rsvpNeeded){
            ////    getSelected().setRsvp(rsvp);
            ////}
            ejbFacade.save(getSelected());
            //for (MenuAllocation m : getSelected().getMenuAllocationList()) {
            //    m.setCalendarNo(getSelected());
            //    maFacade.create(m);
            //}
        } else {
            deleteMenuAllocation(getSelected().getCalendarNo());
            getSelected().setMenuAllocationList(getSelectedMenuAllocation());
            ////if(menuType.equalsIgnoreCase("MENU")){
            ////    if(getSelected().getRsvp()!=null){
            //delete rsvp as menu is changed to menu

            ////    }
            ////}
            ejbFacade.save(getSelected());
            //for (MenuAllocation m : getSelected().getMenuAllocationList()) {
            //    m.setCalendarNo(getSelected());
            //    maFacade.create(m);
            //}
        }
        JsfUtil.addSuccessMessage("Success", "New Menu added");
        //System.out.println(RsvpEmailTemplate.formatRSVPEmail(rsvp.getEmail(), getSelected()));
        refreshSchedule();
    }

    /*public void saveRsvp(ActionEvent event) {
        //rsvp.setDescription("");
        ejbFacade.saveRsvp(getSelected().getCalendarNo(), rsvp);
        JsfUtil.addSuccessMessage("R S V P", "Rsvp was saved");
        refreshRsvp();
    }

    public void itsChanged() {
        if (rsvp != null) {
            if (rsvp.getHostItsNo() != null) {
                rsvp.setMobile(rsvp.getHostItsNo().getMobile1());
                rsvp.setEmail(rsvp.getHostItsNo().getEmail1());
                if (rsvp.getDescription() == null) {
                    rsvp.setDescription(RsvpEmailTemplate.URS_1);
                }
            }
        }
    }

    public void deleteThaaliCalendar(ActionEvent event) {
        deleteMenuAllocation(getSelected().getCalendarNo());
        ejbFacade.delete(getSelected());
        JsfUtil.addSuccessMessage("Calendar Menu", "Menu was deleted");
        refreshSchedule();
    }

    public void deleteRsvp(ActionEvent event) {
        //ejbFacade.removeRsvp(getSelected().getCalendarNo());
        JsfUtil.addSuccessMessage("RSVP Delete", "Rsvp was deleted");
        refreshRsvp();
    }*/

    public void menuChanged() {
        /*if(rsvp!=null){
            rsvp.setDescription(RsvpEmailTemplate.URS_1);
        }*/
        System.out.println("menuChanged: " + getMenuType());
    }

    public boolean getMenuVisible() {
        return menuType.equalsIgnoreCase(Menu.MENU);
    }

    public boolean getOtherVisible() {
        return !getMenuVisible();
    }

    public List getMenuTypes() {
        return Arrays.asList(Menu.MENU_TYPES);
    }

    public List<Menu> getMenuOthers() {
        return menuFacade.findMenuByMenuType(menuType);
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public ThaaliCalendar getThaaliCalendar(java.lang.Integer id) {
        return ejbFacade.getOne(id);
    }

    /**
     * @return the menus
     */
    public DualListModel<Menu> getMenus() {
        return menus;
    }

    /**
     * @param menus the menus to set
     */
    public void setMenus(DualListModel<Menu> menus) {
        this.menus = menus;
    }

    public List<MenuAllocation> getSelectedMenuAllocation() {
        if (menuType.equalsIgnoreCase(Menu.MENU)) {
            if (menus.getTarget() != null) {
                List<MenuAllocation> list = new LinkedList<MenuAllocation>();
                for (Menu menu : menus.getTarget()) {
                    MenuAllocation ma = new MenuAllocation();
                    ma.setCalendarNo(getSelected());
                    ma.setMenuNo(menu);
                    ma.setOrderTimeStamp(new Date().getTime());
                    if (orderTable.contains((long) menu.getMenuNo())) {
                        ma.setOrderTimeStamp(orderTable.get((long) menu.getMenuNo()));
                    } else {
                        ma.setOrderTimeStamp(orderCount);
                        orderCount++;
                    }
                    System.out.println("MENU_ALLOCATION: " + menu + "\tOrder: " + ma.getOrderTimeStamp());
                    list.add(ma);
                }
                return list;
            }
        } else {
            List<MenuAllocation> list = new LinkedList<MenuAllocation>();
            MenuAllocation ma = new MenuAllocation();
            ma.setMenuNo(selectedMenu);
            ma.setCalendarNo(getSelected());
            list.add(ma);
            return list;
        }
        return null;
    }

    public void prepareMenuPickList() {
        List<Menu> source = menuFacade.findMenuByMenuType(Menu.MENU);
        List<Menu> target = new ArrayList<Menu>();

        if (getSelected().getMenuAllocationList() != null) {
            for (MenuAllocation ma : getSelected().getMenuAllocationList()) {
                target.add(ma.getMenuNo());
            }
        }

        menus = new DualListModel<Menu>(source, target);
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            Menu m = (Menu) item;
            builder.append(m.getMenuName()).append("\n");
            if (orderTable.contains((long) m.getMenuNo())) {
                orderTable.remove((long) m.getMenuNo());
            }
            orderTable.put((long) m.getMenuNo(), orderCount);
            orderCount++;
        }

        //FacesMessage msg = new FacesMessage();
        //msg.setSeverity(FacesMessage.SEVERITY_INFO);
        //msg.setSummary("Menu Selected");
        //msg.setDetail(builder.toString());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        JsfUtil.addSuccessMessage("Menu Selected" + " at index: " + (orderCount - 1), builder.toString());
    }

    /**
     * @return the menuType
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * @param menuType the menuType to set
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /**
     * @return the selectedMenu
     */
    public Menu getSelectedMenu() {
        return selectedMenu;
    }

    /**
     * @param selectedMenu the selectedMenu to set
     */
    public void setSelectedMenu(Menu selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        menuNoController.setSelected(null);
    }

    public static String wrapString(String s, String deliminator, int length) {
        String result = "";
        int lastdelimPos = 0;
        for (String token : s.split(" ", -1)) {
            if (result.length() - lastdelimPos + token.length() > length) {
                result = result + deliminator + token;
                lastdelimPos = result.length() + 1;
            } else {
                result += (result.isEmpty() ? "" : " ") + token;
            }
        }
        return result;
    }

    /**
     * @return the sendEmail
     */
    public boolean isSendEmail() {
        return sendEmail;
    }

    /**
     * @param sendEmail the sendEmail to set
     */
    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the uploadedFile
     */
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    /**
     * @param uploadedFile the uploadedFile to set
     */
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void handleFileUpload(FileUploadEvent event) {
        uploadedFile = event.getFile();
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /*
    public Rsvp getRsvp() {
        return rsvp;
    }

    public void setRsvp(Rsvp rsvp) {
        this.rsvp = rsvp;
    }

    public boolean isRsvpNeeded() {
        return rsvpNeeded;
    }

    public void setRsvpNeeded(boolean rsvpNeeded) {
        this.rsvpNeeded = rsvpNeeded;
    }

    public DefaultStreamedContent downloadSanningFile(ThaaliCalendar tc) {
        try {
            List<RsvpRegistration> rlist = rsvpRegistrationFacade.findAll(tc.getRsvp().getRsvpId(), "Yes");
            for (RsvpRegistration rr : rlist) {
                rr.getItsNo().setItsReaderInfo(itsReaderInfoFacade.find(rr.getItsNo().getItsNo()));
            }
            return ItsScanningFile.getInstance().makeCsvStream(rlist);
        } catch (IOException ex) {
            Logger.getLogger(ThaaliCalendarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(ThaaliCalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void completeUpload(ThaaliCalendar tc) {
        int count = 0;
        if (uploadedFile != null) {
            try {
                System.out.println("Uploaded File: " + uploadedFile.getFileName() + "\tSize: " + uploadedFile.getSize());
                CSVReader reader = new CSVReader(new InputStreamReader(uploadedFile.getInputstream()), ',', '\'', 1);
                List<String[]> lines = reader.readAll();
                for (String[] line : lines) {
                    //System.out.println(line[0] + "\t" + line[1] + "\t" + line[2] + "\t" + line[3] + "\t" + line[4] + "\t" + line[5]);
                    System.out.println(line[0]);
                    int itsNo = Integer.parseInt(line[0]);
                    ItsMaster its = itsFacade.find(itsNo);
                    if (its == null) {
                        ItsReaderInfo info = itsReaderFacade.findItsReaderInfoByHfid(line[0]);
                        if (info != null) {
                            itsNo = info.getItsNo();
                            its = itsFacade.find(itsNo);
                        }
                    }
                    if (its == null) {
                        ItsReaderInfo info = itsReaderFacade.findItsReaderInfoByBackBarcode(line[0]);
                        if (info != null) {
                            itsNo = info.getItsNo();
                            its = itsFacade.find(itsNo);
                        }
                    }
                    if (its != null) {
                        RsvpRegistration rr = rsvpRegistrationFacade.findRsvpRegistration(tc.getRsvp().getRsvpId(), itsNo);
                        RsvpAttendance ra = rsvpAttendanceFacade.findRsvpAttendanceByRsvpIdAndItsNo(tc.getRsvp().getRsvpId(), itsNo);
                        Date date = new Date();
                        //try {
                        //    date = DATE_FORMAT.parse(line[4] + " " + line[5]);
                        //} catch (ParseException ex) {
                        //    Logger.getLogger(ThaaliCalendarController.class.getName()).log(Level.SEVERE, null, ex);
                        //}
                        if (ra == null) {
                            ra = new RsvpAttendance();
                            if (its == null) {
                                ra.setGuestItsNo(itsNo);
                            } else {
                                ra.setItsNo(its);
                            }
                            if (rr == null) {
                                ra.setRegistrationStatus("No");
                            } else {
                                ra.setRegistrationStatus(rr.getRegistrationStatus());
                            }
                        }
                        ra.setAttendanceStatus("Yes");
                        ra.setAttendanceDateTime(date);
                        ra.setRsvpId(tc.getRsvp());

                        rsvpAttendanceFacade.saveRsvpAttendance(ra);
                        count++;
                    }
                }
                if (count == 0) {
                    JsfUtil.addErrorMessage("No Records Updated");
                } else {
                    JsfUtil.addErrorMessage("Total " + count + " scan records updated");
                }
            } catch (IOException ex) {
                Logger.getLogger(ThaaliCalendarController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    */
}
