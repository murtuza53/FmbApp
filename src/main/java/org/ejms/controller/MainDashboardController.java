/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.controller;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.ejms.helper.ReportHelper;
import org.ejms.report.NativeQueryReportObject;
import org.ejms.report.NativeQueryTableModel;
import org.ejms.service.DatabaseService;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Burhani152
 */
@Named(value = "mainDashboardController")
@ViewScoped
public class MainDashboardController implements Serializable {

    @Autowired
    DatabaseService ds;

    private NativeQueryTableModel nqModel = new NativeQueryTableModel();
    private List<NativeQueryReportObject> nativeDataList;
    private BarChartModel sabilBarModel = new BarChartModel();
    private BarChartModel lagatBarModel = new BarChartModel();
    private BarChartModel busBarModel = new BarChartModel();
    private PieChartModel summaryPieModel = new PieChartModel();

    private List<ReportHelper> sabilReportList;
    private List<ReportHelper> lagatReportList;
    private List<ReportHelper> busReportList;
    private List<ReportHelper> summaryReportList;

    private double totalInvoiced;
    private double totalPaid;
    private String query;
    String invType = "having invtype='Sabil'";

    private int itsCount;
    private int hofCount;
    private int personalSabilCount;
    private int businessSabilCount;

    public MainDashboardController() {
        query = "SELECT invtype, yr, "
                + "	sum(inv) AS 'Invoiced', sum(paid) AS 'Paid', sum(inv-paid) AS 'Balance' "
                + "FROM( "
                + "select invtype, hijriyear as yr, SUM(amount) AS inv, "
                + "   SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                + "FROM( "
                + "SELECT i.itsNo as id, CONCAT(i.firstName, ' ', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, itsmaster as i "
                + "where p.itsNo=i.itsNo "
                + "UNION "
                + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business' as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, business as b "
                + "where p.businessNo=b.businessNo) as step1 group by yr, invtype) as step2 group by yr, invtype " + invType + " order by yr desc ";
    }

    @PostConstruct
    public void init() {
        //refreshCount();
        //refresh();
    }

    public void refreshCount() {
        String q = "select count(*) from itsmaster where itsstatus='ACTIVE'";
        itsCount = ((BigInteger) ds.executeCountNativeQuery(q).get(0)).intValue();
        //System.out.println("ITS_COUNT: " + ((BigInteger)ds.executeCountNativeQuery(q).get(0)).intValue());

        q = "select count(*) from itsmaster where itsstatus='ACTIVE' and itsNo=hofId";
        hofCount = ((BigInteger) ds.executeCountNativeQuery(q).get(0)).intValue();

        q = "select count(s.itsNo) from sabil as s, itsmaster as i where s.itsNo=i.itsNo and i.itsstatus='ACTIVE'";
        personalSabilCount = ((BigInteger) ds.executeCountNativeQuery(q).get(0)).intValue();

        q = "select count(s.businessNo) from sabil as s, business as b where s.businessNo=b.businessNo";
        businessSabilCount = ((BigInteger) ds.executeCountNativeQuery(q).get(0)).intValue();
    }

    public void refresh() {
        refreshCount();
        invType = "";
        query = "SELECT invoiceType, "
                + "sum(inv) AS 'Invoiced', sum(paid) AS 'Paid', sum(inv-paid) AS 'Balance' "
                + "FROM( "
                + "select id, fullname, invoiceType, year(invoiceDate) as yr, SUM(amount) AS inv, "
                + "SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                + "FROM( "
                + "SELECT i.itsNo as id, CONCAT(i.firstName, ' ', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType, p.sabilNo "
                + "FROM paymentinvoice as p, itsmaster as i "
                + "WHERE p.itsNo=i.itsNo "
                + "UNION "
                + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business', p.sabilNo "
                + "FROM paymentinvoice as p, business as b "
                + "where p.businessNo=b.businessNo) as step1 group by invoiceType ) as step2 group by invoiceType";
        /*query = "SELECT invtype, yr, "
                + "	sum(inv) AS 'Invoiced', sum(paid) AS 'Paid', sum(inv-paid) AS 'Balance' "
                + "FROM( "
                + "select invtype, hijriyear as yr, SUM(amount) AS inv, "
                + "   SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                + "FROM( "
                + "SELECT i.itsNo as id, CONCAT(i.firstName, ' ', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, itsmaster as i "
                + "where p.itsNo=i.itsNo "
                + "UNION "
                + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business' as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, business as b "
                + "where p.businessNo=b.businessNo) as step1 group by yr, invtype) as step2 group by yr, invtype " + invType + " order by yr desc ";
         */

        refreshSummaryReport(query);

        invType = "having invtype='Sabil'";
        query = "SELECT invtype, yr, "
                + "	sum(inv) AS 'Invoiced', sum(paid) AS 'Paid', sum(inv-paid) AS 'Balance' "
                + "FROM( "
                + "select invtype, hijriyear as yr, SUM(amount) AS inv, "
                + "   SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                + "FROM( "
                + "SELECT i.itsNo as id, CONCAT(i.firstName, ' ', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, itsmaster as i "
                + "where p.itsNo=i.itsNo "
                + "UNION "
                + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business' as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, business as b "
                + "where p.businessNo=b.businessNo) as step1 group by yr, invtype) as step2 group by yr, invtype " + invType + " order by yr desc ";

        refreshSabilReport(query);

        invType = "having invtype='Lagat'";
        query = "SELECT invtype, yr, "
                + "	sum(inv) AS 'Invoiced', sum(paid) AS 'Paid', sum(inv-paid) AS 'Balance' "
                + "FROM( "
                + "select invtype, hijriyear as yr, SUM(amount) AS inv, "
                + "   SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                + "FROM( "
                + "SELECT i.itsNo as id, CONCAT(i.firstName, ' ', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, itsmaster as i "
                + "where p.itsNo=i.itsNo "
                + "UNION "
                + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business' as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, business as b "
                + "where p.businessNo=b.businessNo) as step1 group by yr, invtype) as step2 group by yr, invtype " + invType + " order by yr desc ";

        refreshLagatReport(query);

        invType = "having invtype='Business'";
        query = "SELECT invtype, yr, "
                + "	sum(inv) AS 'Invoiced', sum(paid) AS 'Paid', sum(inv-paid) AS 'Balance' "
                + "FROM( "
                + "select invtype, hijriyear as yr, SUM(amount) AS inv, "
                + "   SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                + "FROM( "
                + "SELECT i.itsNo as id, CONCAT(i.firstName, ' ', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, itsmaster as i "
                + "where p.itsNo=i.itsNo "
                + "UNION "
                + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business' as invtype, p.sabilNo "
                + "FROM paymentinvoice as p, business as b "
                + "where p.businessNo=b.businessNo) as step1 group by yr, invtype) as step2 group by yr, invtype " + invType + " order by yr desc ";

        refreshBusinessReport(query);
    }

    public void refreshSummaryReport(String exe_query) {
        nativeDataList = NativeQueryReportObject.asReportObjectList(ds.executeNativeQuery(exe_query));

        summaryReportList = new LinkedList<ReportHelper>();
        totalInvoiced = 0;
        totalPaid = 0;
        summaryPieModel = new PieChartModel();
        //summaryPieModel.setTitle("Outstanding Pie");
        summaryPieModel.setLegendPosition("w");
        summaryPieModel.setShowDataLabels(true);
        //summaryPieModel.setDiameter(150);
        //summaryPieModel.setShadow(false);

        for (NativeQueryReportObject ro : nativeDataList) {
            ReportHelper rh = new ReportHelper();
            rh.setDescription(ro.getField(0).toString());
            rh.setDebit(Double.parseDouble(ro.getField(1).toString()));
            rh.setCredit(Double.parseDouble(ro.getField(2).toString()));
            rh.setBalance(Double.parseDouble(ro.getField(3).toString()));
            totalInvoiced = totalInvoiced + rh.getDebit();
            totalPaid = totalPaid + rh.getCredit();
            if (rh.getBalance() > 0) {
                summaryReportList.add(rh);
                summaryPieModel.set(rh.getDescription(), rh.getBalance());
            }
        }
        ReportHelper rh = new ReportHelper();
        rh.setDescription("TOTAL");
        rh.setDebit(totalInvoiced);
        rh.setCredit(totalPaid);
        rh.setBalance(totalInvoiced - totalPaid);
        summaryReportList.add(rh);
    }

    public void refreshSabilReport(String exe_query) {
        nativeDataList = NativeQueryReportObject.asReportObjectList(ds.executeNativeQuery(exe_query));

        sabilReportList = new LinkedList<ReportHelper>();
        totalInvoiced = 0;
        totalPaid = 0;
        sabilBarModel = new BarChartModel();
        ChartSeries invSeries = new ChartSeries();
        invSeries.setLabel("Invoiced");
        ChartSeries paidSeries = new ChartSeries();
        paidSeries.setLabel("Paid");
        //barModel.setTitle("Bar Chart");
        sabilBarModel.setLegendPosition("ne");
        sabilBarModel.setAnimate(true);

        Axis xAxis = sabilBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Year");

        Axis yAxis = sabilBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Amount");
        for (NativeQueryReportObject ro : nativeDataList) {
            ReportHelper rh = new ReportHelper();
            rh.setDescription(ro.getField(1).toString());
            rh.setDebit(Double.parseDouble(ro.getField(2).toString()));
            rh.setCredit(Double.parseDouble(ro.getField(3).toString()));
            rh.setBalance(Double.parseDouble(ro.getField(4).toString()));
            totalInvoiced = totalInvoiced + rh.getDebit();
            totalPaid = totalPaid + rh.getCredit();
            if (rh.getBalance() > 0) {
                sabilReportList.add(rh);
                invSeries.set(rh.getDescription(), rh.getDebit());
                paidSeries.set(rh.getDescription(), rh.getCredit());
            }
        }
        sabilBarModel.addSeries(invSeries);
        sabilBarModel.addSeries(paidSeries);
        ReportHelper rh = new ReportHelper();
        rh.setDescription("TOTAL");
        rh.setDebit(totalInvoiced);
        rh.setCredit(totalPaid);
        rh.setBalance(totalInvoiced - totalPaid);
        sabilReportList.add(rh);
    }

    public void refreshLagatReport(String exe_query) {
        nativeDataList = NativeQueryReportObject.asReportObjectList(ds.executeNativeQuery(exe_query));

        lagatReportList = new LinkedList<ReportHelper>();
        totalInvoiced = 0;
        totalPaid = 0;
        lagatBarModel = new BarChartModel();
        ChartSeries invSeries = new ChartSeries();
        invSeries.setLabel("Invoiced");
        ChartSeries paidSeries = new ChartSeries();
        paidSeries.setLabel("Paid");
        //barModel.setTitle("Bar Chart");
        lagatBarModel.setLegendPosition("ne");
        lagatBarModel.setAnimate(true);

        Axis xAxis = lagatBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Year");

        Axis yAxis = lagatBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Amount");
        for (NativeQueryReportObject ro : nativeDataList) {
            ReportHelper rh = new ReportHelper();
            rh.setDescription(ro.getField(1).toString());
            rh.setDebit(Double.parseDouble(ro.getField(2).toString()));
            rh.setCredit(Double.parseDouble(ro.getField(3).toString()));
            rh.setBalance(Double.parseDouble(ro.getField(4).toString()));
            totalInvoiced = totalInvoiced + rh.getDebit();
            totalPaid = totalPaid + rh.getCredit();
            if (rh.getBalance() > 0) {
                lagatReportList.add(rh);
                invSeries.set(rh.getDescription(), rh.getDebit());
                paidSeries.set(rh.getDescription(), rh.getCredit());
            }
        }
        lagatBarModel.addSeries(invSeries);
        lagatBarModel.addSeries(paidSeries);
        ReportHelper rh = new ReportHelper();
        rh.setDescription("TOTAL");
        rh.setDebit(totalInvoiced);
        rh.setCredit(totalPaid);
        rh.setBalance(totalInvoiced - totalPaid);
        lagatReportList.add(rh);
    }

    public void refreshBusinessReport(String exe_query) {
        nativeDataList = NativeQueryReportObject.asReportObjectList(ds.executeNativeQuery(exe_query));

        busReportList = new LinkedList<ReportHelper>();
        totalInvoiced = 0;
        totalPaid = 0;
        busBarModel = new BarChartModel();
        ChartSeries invSeries = new ChartSeries();
        invSeries.setLabel("Invoiced");
        ChartSeries paidSeries = new ChartSeries();
        paidSeries.setLabel("Paid");
        //barModel.setTitle("Bar Chart");
        busBarModel.setLegendPosition("ne");
        busBarModel.setAnimate(true);

        Axis xAxis = busBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Year");

        Axis yAxis = busBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Amount");
        for (NativeQueryReportObject ro : nativeDataList) {
            ReportHelper rh = new ReportHelper();
            rh.setDescription(ro.getField(1).toString());
            rh.setDebit(Double.parseDouble(ro.getField(2).toString()));
            rh.setCredit(Double.parseDouble(ro.getField(3).toString()));
            rh.setBalance(Double.parseDouble(ro.getField(4).toString()));
            totalInvoiced = totalInvoiced + rh.getDebit();
            totalPaid = totalPaid + rh.getCredit();
            if (rh.getBalance() > 0) {
                busReportList.add(rh);
                invSeries.set(rh.getDescription(), rh.getDebit());
                paidSeries.set(rh.getDescription(), rh.getCredit());
            }
        }
        busBarModel.addSeries(invSeries);
        busBarModel.addSeries(paidSeries);
        ReportHelper rh = new ReportHelper();
        rh.setDescription("TOTAL");
        rh.setDebit(totalInvoiced);
        rh.setCredit(totalPaid);
        rh.setBalance(totalInvoiced - totalPaid);
        busReportList.add(rh);
    }

    /**
     * @return the totalInvoiced
     */
    public double getTotalInvoiced() {
        return totalInvoiced;
    }

    /**
     * @return the totalPaid
     */
    public double getTotalPaid() {
        return totalPaid;
    }

    public double getBalance() {
        return totalInvoiced - totalPaid;
    }

    /**
     * @return the sabilReportList
     */
    public List<ReportHelper> getSabilReportList() {
        return sabilReportList;
    }

    /**
     * @param sabilReportList the sabilReportList to set
     */
    public void setSabilReportList(List<ReportHelper> sabilReportList) {
        this.sabilReportList = sabilReportList;
    }

    /**
     * @return the lagatReportList
     */
    public List<ReportHelper> getLagatReportList() {
        return lagatReportList;
    }

    /**
     * @param lagatReportList the lagatReportList to set
     */
    public void setLagatReportList(List<ReportHelper> lagatReportList) {
        this.lagatReportList = lagatReportList;
    }

    /**
     * @return the busReportList
     */
    public List<ReportHelper> getBusReportList() {
        return busReportList;
    }

    /**
     * @param busReportList the busReportList to set
     */
    public void setBusReportList(List<ReportHelper> busReportList) {
        this.busReportList = busReportList;
    }

    public BarChartModel getSabilBarchartModel() {
        return sabilBarModel;
    }

    public BarChartModel getLagatBarchartModel() {
        return lagatBarModel;
    }

    public BarChartModel getBusBarchartModel() {
        return busBarModel;
    }

    /**
     * @return the summaryReportList
     */
    public List<ReportHelper> getSummaryReportList() {
        return summaryReportList;
    }

    /**
     * @param summaryReportList the summaryReportList to set
     */
    public void setSummaryReportList(List<ReportHelper> summaryReportList) {
        this.summaryReportList = summaryReportList;
    }

    /**
     * @return the summaryPieModel
     */
    public PieChartModel getSummaryPieModel() {
        return summaryPieModel;
    }

    /**
     * @return the itsCount
     */
    public int getItsCount() {
        return itsCount;
    }

    /**
     * @param itsCount the itsCount to set
     */
    public void setItsCount(int itsCount) {
        this.itsCount = itsCount;
    }

    /**
     * @return the hofCount
     */
    public int getHofCount() {
        return hofCount;
    }

    /**
     * @param hofCount the hofCount to set
     */
    public void setHofCount(int hofCount) {
        this.hofCount = hofCount;
    }

    /**
     * @return the personalSabilCount
     */
    public int getPersonalSabilCount() {
        return personalSabilCount;
    }

    /**
     * @param personalSabilCount the personalSabilCount to set
     */
    public void setPersonalSabilCount(int personalSabilCount) {
        this.personalSabilCount = personalSabilCount;
    }

    /**
     * @return the businessSabilCount
     */
    public int getBusinessSabilCount() {
        return businessSabilCount;
    }

    /**
     * @param businessSabilCount the businessSabilCount to set
     */
    public void setBusinessSabilCount(int businessSabilCount) {
        this.businessSabilCount = businessSabilCount;
    }
}
