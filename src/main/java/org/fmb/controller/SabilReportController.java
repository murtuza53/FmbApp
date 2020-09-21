/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.controller;

import com.hijri.HijriDate;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.fmb.entity.ItsMaster;
import org.fmb.entity.PaymentInvoice;
import org.fmb.entity.Sabil;
import org.fmb.helper.SabilReportHelper;
import org.fmb.repo.ItsMasterRepository;
import org.fmb.repo.PaymentInvoiceRepository;
import org.fmb.repo.SabilRepository;
import org.fmb.report.NativeQueryReportObject;
import org.fmb.report.NativeQueryTableModel;
import org.fmb.service.DatabaseService;
import org.fmb.util.DateUtil;
import org.fmb.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Burhani152
 */
@Named(value = "sabilReportController")
@ViewScoped
@Transactional
public class SabilReportController implements Serializable {

    @Autowired
    DatabaseService ds;

    @Autowired
    ItsMasterRepository itsMasterRepo;

    @Autowired
    SabilRepository sabilRepo;

    @Autowired
    PaymentInvoiceRepository piRepo;

    private NativeQueryTableModel nqModel = new NativeQueryTableModel();

    private NativeQueryReportObject selected;

    private SabilReportHelper selectedReportHelper;

    private Integer year = DateUtil.getYear(new Date());

    private Integer[] years = new Integer[]{DateUtil.getYear(new Date())};

    private String queryYearFilter = " and year(p.invoicedate)=" + year;

    private List<Integer> yearRange = Arrays.asList(Utils.YEARS);

    private String queryMonthFilter = "";

    private String invoiceType = "";

    private List<SabilReportHelper> noSabilReportList;

    private int hijriYear = HijriDate.getInstance().getYear();

    public SabilReportController() {

    }

    @PostConstruct
    public void init() {
        nqModel.resetModel();
    }

    public void refreshNotInvoiced() {
        List<ItsMaster> hofList = itsMasterRepo.findItsMasterActvieHof("");

        noSabilReportList = new LinkedList<SabilReportHelper>();
        for (ItsMaster its : hofList) {
            List<Sabil> slist = sabilRepo.findSabilByItsNo(its.getItsNo());
            SabilReportHelper sr = new SabilReportHelper();
            if (slist == null || slist.size() == 0) {
                sr.setId(its.getItsNo() + "");
                sr.setName(its.getFullName());
                sr.setDescription("Sabil Not Registered");
                getNoSabilReportList().add(sr);
            } else {
                Sabil s = slist.get(0);
                List<PaymentInvoice> list = piRepo.findLastPaymentInvoiceByItsNo(its.getItsNo(), "Sabil");
                if (list == null || list.size() == 0) {
                    sr.setId(its.getItsNo() + "");
                    sr.setName(its.getFullName());
                    sr.setDescription("No Invoice Found");
                    //sr.setSabilYearly(s.getSabilCategoryNo().getAmount() * 12);
                    getNoSabilReportList().add(sr);
                } else {
                    PaymentInvoice pi = list.get(list.size() - 1);
                    if (pi.getHijriYear().intValue() <= hijriYear && pi.getHijriMonth().intValue() < 12) {
                        sr.setId(its.getItsNo() + "");
                        sr.setName(its.getFullName());
                        sr.setDescription("Last Invoice created till " + pi.getHijriMonthSpell() + " " + pi.getHijriYear());
                        sr.setSabilYearly(s.getSabilCategoryNo().getAmount() * 12);
                        getNoSabilReportList().add(sr);
                    }
                }
            }
        }
    }

    public List<NativeQueryReportObject> getYcDataList() {
        if (nqModel.getData() == null) {
            refreshYcBalances();
        }
        System.out.println("getYcDataList: " + nqModel.getData().size());
        return nqModel.getData();
    }

    public void refreshYcBalances() {
        nqModel.resetModel();
        if (years == null || years.length == 0) {
            years = new Integer[]{DateUtil.getYear(new Date())};
        }
        if (years.length > 0 && years[0] == 0) { //all is selected
            years = new Integer[]{0};
            String query = "SELECT id, fullname, "
                    + "	sum(inv) AS 'Invoiced', sum(paid) AS 'Paid', sum(inv-paid) AS 'Balance' "
                    + "FROM( "
                    + "select id, fullname, year(invoiceDate) as yr, SUM(amount) AS inv, "
                    + "   SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                    + "FROM( "
                    + "SELECT i.itsNo as id, CONCAT(i.firstName, ' ', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType, p.sabilNo "
                    + "FROM paymentinvoice as p, itsmaster as i "
                    + "WHERE p.itsNo=i.itsNo "
                    + "UNION "
                    + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business', p.sabilNo "
                    + "FROM paymentinvoice as p, business as b "
                    + "where p.businessNo=b.businessNo) as step1 where invoiceType LIKE '%" + invoiceType + "%' group by yr, id ) as step2 group by id";
            nqModel.addColumns(new String[]{"Id", "Name", "invoiced", "paid", "balance"},
                    new Class[]{String.class, String.class, Double.class, Double.class, Double.class},
                    new String[]{"", "", "", ""});
            nqModel.setData(NativeQueryReportObject.asReportObjectList(ds.executeNativeQuery(query)));
        } else {
            queryYearFilter = null;
            for (Integer y : years) {
                if (queryYearFilter == null) {
                    queryYearFilter = "sum(CASE WHEN yr=" + y + "  THEN inv ELSE 0 END) AS 'Inv" + y + "', "
                            + "sum(CASE WHEN yr=" + y + "  THEN paid ELSE 0 END) AS 'Paid" + y + "', "
                            + "sum(CASE WHEN yr=" + y + "  THEN (inv-paid) ELSE 0 END) AS 'Bal" + y + "'";
                } else {
                    queryYearFilter = queryYearFilter + ", sum(CASE WHEN yr=" + year + "  THEN inv ELSE 0 END) AS 'Inv" + y + "', "
                            + "sum(CASE WHEN yr=" + y + "  THEN paid ELSE 0 END) AS 'Paid" + y + "', "
                            + "sum(CASE WHEN yr=" + y + "  THEN (inv-paid) ELSE 0 END) AS 'Bal" + y + "'";
                }
            }

            String query = "SELECT id, fullname, " + queryYearFilter
                    + "FROM ( "
                    + "select id, fullname, year(invoiceDate) as yr, SUM(amount) AS inv, "
                    + "   SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                    + "FROM( "
                    + "SELECT i.itsNo as id, CONCAT(i.firstName, '', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType, p.sabilNo "
                    + "FROM paymentinvoice as p, itsmaster as i "
                    + "where p.itsNo=i.itsNo "
                    + "UNION "
                    + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business', p.sabilNo "
                    + "FROM paymentinvoice as p, business as b "
                    + "where p.businessNo=b.businessNo) as step1 where invoiceType LIKE '%" + invoiceType + "%' group by yr, id) as step2 group by id";
            nqModel.addColumns(new String[]{"Id", "Name"}, new Class[]{String.class, String.class}, new String[]{"", ""});
            for (Integer y : years) {
                nqModel.addColumns(new String[]{"Inv" + y, "Paid" + y, "Bal" + y},
                        new Class[]{Double.class, Double.class, Double.class},
                        new String[]{"", "", ""});
            }
            nqModel.setData(NativeQueryReportObject.asReportObjectList(ds.executeNativeQuery(query)));
        }
    }

    public List<NativeQueryReportObject> getMcDataList() {
        if (nqModel.getData() == null) {
            refreshMcBalances();
            String query = "SELECT i.itsNo, i.firstName, i.surname,"
                    + queryMonthFilter
                    + "   ifnull(SUM(p.amount),0) AS 'TOTAL'"
                    + " FROM paymentinvoice as p, itsmaster as i, accountdocument as ac"
                    + " where i.itsNo=p.itsNo and p.accountDocumentNo=ac.accountDocumentNo and year(p.invoicedate)=" + year
                    + " group by i.itsNo order by i.firstname, i.surname";

            nqModel.setData(NativeQueryReportObject.asReportObjectList(ds.executeNativeQuery(query)));
        }
        System.out.println("getMcDataList: " + nqModel.getData().size());
        return nqModel.getData();
    }

    public void refreshMcBalances() {
        prepareQueryMonthFilter();
        if (year == 0) {
            queryYearFilter = "";
        } else {
            queryYearFilter = " and year(ac.docDate)=" + year;
        }
    }

    private void prepareQueryMonthFilter() {
        nqModel.resetModel();
        if (year < DateUtil.getYear(new Date())) {
            queryMonthFilter = "   ifnull(SUM(CASE WHEN YEAR(p.invoiceDate) = " + year + "  THEN p.amount END),0) AS 'Invoiced',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 1  THEN p.amount END),0) AS 'Jan',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 2  THEN p.amount END),0) AS 'Feb',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 3  THEN p.amount END),0) AS 'Mar',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 4  THEN p.amount END),0) AS 'Apr',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 5  THEN p.amount END),0) AS 'May',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 6  THEN p.amount END),0) AS 'Jun',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 7  THEN p.amount END),0) AS 'Jul',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 8  THEN p.amount END),0) AS 'Aug',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 9  THEN p.amount END),0) AS 'Sep',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 10 THEN p.amount END),0) AS 'Oct',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 11 THEN p.amount END),0) AS 'Nov',"
                    + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 12 THEN p.amount END),0) AS 'Dec',";
            //pimRb = ReportBuilder.newInstance().addColumns(new String[]{"itsNo", "firstName", "surname", "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec", "total"});
            //nqModel = new NativeQueryTableModel(new String[]{"itsNo", "firstName", "surname", "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec", "total"});
            nqModel.addColumns(new String[]{"itsNo", "firstName", "surname", "Invoiced In " + year, "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec", "total"},
                    new Class[]{String.class, String.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class},
                    new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
        } else {
            int month = DateUtil.getMonth(new Date());
            queryMonthFilter = "   ifnull(SUM(CASE WHEN YEAR(p.invoiceDate) = " + year + "  THEN p.amount END),0) AS 'Invoiced',";
            //pimRb = ReportBuilder.newInstance().addColumns(new String[]{"itsNo", "firstName", "surname"});
            //nqModel = new NativeQueryTableModel(new String[]{"itsNo", "firstName", "surname"});
            nqModel.addColumns(new String[]{"itsNo", "firstName", "surname", "Invoiced In " + year},
                    new Class[]{String.class, String.class, String.class, Double.class},
                    new String[]{"", "", "", ""});
            for (int i = 0; i <= month; i++) {
                if (i == 0) {
                    queryMonthFilter = queryMonthFilter
                            + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = 1  THEN p.amount END),0) AS 'Jan',";
                    nqModel.addColumns(new String[]{"Jan"}, new Class[]{Double.class}, new String[]{""});
                } else {
                    queryMonthFilter = queryMonthFilter
                            + "   ifnull(SUM(CASE WHEN MONTH(ac.docDate) = " + (i + 1) + "  THEN p.amount END),0) AS '" + Utils.MONTH_NAMES[i] + "',";
                    //nqModel.addColumn(Utils.MONTH_NAMES[i], Utils.MONTH_NAMES[i]);
                    nqModel.addColumns(new String[]{Utils.MONTH_NAMES[i]}, new Class[]{Double.class}, new String[]{""});
                }
            }
            nqModel.addColumns(new String[]{"Total"}, new Class[]{Double.class}, new String[]{""});
        }

    }

    public NativeQueryTableModel getReportModel() {
        return nqModel;
    }

    /**
     * @return the selected
     */
    public NativeQueryReportObject getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(NativeQueryReportObject selected) {
        this.selected = selected;
    }

    /**
     * @return the yearRange
     */
    public List<Integer> getYearRange() {
        return yearRange;
    }

    /**
     * @return the year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return the years
     */
    public Integer[] getYears() {
        return years;
    }

    /**
     * @param years the years to set
     */
    public void setYears(Integer[] years) {
        this.years = years;
    }

    /**
     * @return the invoiceType
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * @param invoiceType the invoiceType to set
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * @return the noSabilReportList
     */
    public List<SabilReportHelper> getNoSabilReportList() {
        return noSabilReportList;
    }

    /**
     * @return the selectedReportHelper
     */
    public SabilReportHelper getSelectedReportHelper() {
        return selectedReportHelper;
    }

    /**
     * @param selectedReportHelper the selectedReportHelper to set
     */
    public void setSelectedReportHelper(SabilReportHelper selectedReportHelper) {
        this.selectedReportHelper = selectedReportHelper;
    }

}
