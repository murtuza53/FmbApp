/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.ejms.entity.ItsMaster;
import org.ejms.entity.PaymentInvoice;
import org.ejms.helper.ReportHelper;
import org.ejms.repo.ItsMasterRepository;
import org.ejms.report.NativeQueryReportObject;
import org.ejms.report.NativeQueryTableModel;
import org.ejms.service.DatabaseService;
import org.ejms.util.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Burhani152
 */
@Named(value = "oneWindowController")
@ViewScoped
@Transactional
public class OneWindowController implements Serializable {

    @Autowired
    DatabaseService ds;

    @Autowired
    ItsMasterRepository itsRepo;
    
    private NativeQueryTableModel sabilModel = new NativeQueryTableModel();

    private ItsMaster its;
    
    private ItsMaster hof;
    
    private List<ItsMaster> familyMembers;
    
    private List<ReportHelper> invoiceList;
    
    private double totalInvoiced;
    private double totalPaid;

    public OneWindowController() {

    }

    @PostConstruct
    public void init(){
        refreshWindow();
    }
    
    public void refreshWindow() {
        if (getIts() != null) {
            setHof(itsRepo.getOne(its.getHofId()));
            setFamilyMembers(itsRepo.findItsMasterMembersUnderHofId(hof.getHofId()));
            
            String squery = "SELECT id, fullname, yr,"
                    + "	sum(inv) AS 'Invoiced', sum(paid) AS 'Paid', sum(inv-paid) AS 'Balance' "
                    + "FROM( "
                    + "select id, fullname, hijriyear as yr, SUM(amount) AS inv, "
                    + "   SUM(CASE WHEN accountDocumentNo is not null  THEN amount ELSE 0 END) AS paid "
                    + "FROM( "
                    + "SELECT i.itsNo as id, CONCAT(i.firstName, ' ', ifnull(i.surname, '')) as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, p.invoiceType, p.sabilNo "
                    + "FROM paymentinvoice as p, itsmaster as i "
                    + "where p.itsNo=i.itsNo "
                    + "UNION "
                    + "SELECT b.businessNo as id, b.businessName as fullname, p.invoiceDate, p.amount, p.accountDocumentNo, p.hijrimonth, p.hijriyear, 'Business', p.sabilNo "
                    + "FROM paymentinvoice as p, business as b "
                    + "where p.businessNo=b.businessNo) as step1 group by yr, id) as step2 group by yr, id having id=" + getIts().getItsNo()
                    + " order by yr desc";

            sabilModel.addColumns(new String[]{"Id", "Name", "Year", "invoiced", "paid", "balance"},
                    new Class[]{String.class, String.class, Integer.class, Double.class, Double.class, Double.class},
                    new String[]{"", "", "", "", ""});
            sabilModel.setData(NativeQueryReportObject.asReportObjectList(ds.executeNativeQuery(squery)));
            
            invoiceList = new LinkedList<ReportHelper>();
            totalInvoiced = 0;
            totalPaid = 0;
            for(NativeQueryReportObject no: sabilModel.getData()){
                ReportHelper in = new ReportHelper();
                in.setYear((Integer)no.getField(2));
                in.setDebit((Double)no.getField(3));
                totalInvoiced = totalInvoiced + in.getDebit();
                in.setCredit((Double)no.getField(4));
                totalPaid = totalPaid + in.getCredit();
                in.setBalance((Double)no.getField(5));
                invoiceList.add(in);
            }
        }
    }

    public String getMemberName(){
        if(its==null){
            return "";
        }
        return its.getFullName() + "  [ITS: " + its.getItsNo() + "]";
    }

    public String getHofName(){
        if(hof==null){
            return "";
        }
        return hof.getFullName() + "  [ITS: " + hof.getItsNo() + "]";
    }
    
    public String getFamilyCount(){
        if(familyMembers==null){
            return "";
        }
        return familyMembers.size()+"";
    }
    
    public String getItsStatus(){
        if(its==null){
            return "";
        }
        return its.getItsStatus();
    }
    
    public String getSabilStatus(){
        if(its==null){
            return "";
        }
        return its.getSabilStatus();
    }
    
    public String getItsNo(){
        if(its==null){
            return "nophoto";
        }
        return its.getItsNo()+"";
    }
    
    public String getHofItsNo(){
        if(hof==null){
            return "nophoto";
        }
        return hof.getItsNo()+"";
    }

    /**
     * @return the its
     */
    public ItsMaster getIts() {
        return its;
    }

    /**
     * @param its the its to set
     */
    public void setIts(ItsMaster its) {
        this.its = its;
    }

    /**
     * @return the hof
     */
    public ItsMaster getHof() {
        return hof;
    }

    /**
     * @param hof the hof to set
     */
    public void setHof(ItsMaster hof) {
        this.hof = hof;
    }

    /**
     * @return the familyMembers
     */
    public List<ItsMaster> getFamilyMembers() {
        return familyMembers;
    }

    /**
     * @param familyMembers the familyMembers to set
     */
    public void setFamilyMembers(List<ItsMaster> familyMembers) {
        this.familyMembers = familyMembers;
    }

    /**
     * @return the invoiceList
     */
    public List<ReportHelper> getInvoiceList() {
        return invoiceList;
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

    public double getBalance(){
        return totalInvoiced-totalPaid;
    }
    
    public String getSabilFooter(){
        if(getBalance()>0){
            return "Total Outstanding: " + SystemConfig.DECIMAL_FORMAT.format(getBalance());
        }
        return "No Outstanding";
    }
    
}
