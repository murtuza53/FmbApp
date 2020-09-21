package org.fmb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.fmb.entity.ItsMaster;
import org.fmb.repo.ItsMasterRepository;
import org.fmb.util.JsfUtil;
import org.fmb.util.Utils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;

@Named(value = "itsMasterController")
@ViewScoped
public class ItsMasterController extends AbstractController<ItsMaster> {

    //@Autowired
    private ItsMasterRepository itsFacade;

    private UploadedFile file;

    private String progressMessage = "Ready";

    private int progress = 0;
    private int count_new = 0;
    private int count_updated = 0;

    private List<String> COLUMNS;
    private String importFormat = "Address";  //Address, ItsStatus

    private String[] ENTITY_FIELDS = new String[]{
        "itsNo", "hofId",
        "prefix", "firstName", "prefix2", "secondName",
        "fatherSurname", "surname", "fullName", "arabicName",
        "mobile1", "mobile2", "tel1", "tel2",
        "fax1", "fax2", "email1", "email2",
        "flatNo", "buildingNo", "roadNo", "blockNo", "areaName",
        "gender", "cpr", "maritalStatus",
        "vatan", "nationality"
    };

    private String[] ITS_STATUS = {"REGISTERED", "ACTIVE", "LEFT", "TO_TRANSFER_OUT", "DEAD", "UNREACHABLE", "VISITOR", "UNPROCESSED", "COMMITTEE"};
    private String[] SABIL_STATUS = {"PAYS_SABIL", "NOT_PAYING_SABIL", "EXEMPTED_BY_KHIDMAT", "EXEMPTED_BY_RAZA", "VISITOR", "SABIL_RECEIVABLE", "NOT_IN_JAMAAT", "UNPROCESSED"};
    private Map<String, String> its_status = new HashMap<String, String>();
    private Map<String, String> sabil_status = new HashMap<String, String>();

    private List<ItsMaster> itsList;

    @Autowired
    public ItsMasterController(ItsMasterRepository repo) {
        // Inform the Abstract parent controller of the concrete ItsMaster Entity
        super(ItsMaster.class, repo);
        this.itsFacade = repo;
    }

    @PostConstruct
    public void init() {
        for (String s : ITS_STATUS) {
            its_status.put(s, s);
        }

        for (String s : SABIL_STATUS) {
            sabil_status.put(s, s);
        }
    }

    public int getTotalItsCount(){
        return itsFacade.findItsMasterByCriteria("").size();
    }
    
    public int getTotalHofCount(){
        return itsFacade.findItsMasterActvieHof("").size();
    }
    
    public List<ItsMaster> getItsList() {
        if (itsList == null) {
            itsList = itsFacade.findAll();
        }
        JsfUtil.addSuccessMessage("Information", "Total ITS" + itsList.size() + " Listed");
        Logger.getLogger(ItsMasterController.class.getName()).log(Level.INFO, "Total ITS {0} Listed", itsList.size());
        return itsList;
    }

    public List<ItsMaster> getHofList() {
        if(itsList==null){
            itsList = itsFacade.findItsMasterActvieHof("");
        }
        JsfUtil.addSuccessMessage("Information", "Total Active " + itsList.size() + " HOF Listed");
        return itsList;
    }

    public Map<String, String> getItsStatusList() {
        return its_status;
    }

    public Map<String, String> getSabilStatusList() {
        return sabil_status;
    }
    
    @Override
    public void save(){
        itsFacade.save(getSelected());
        JsfUtil.addSuccessMessage("ITS saved");
    }

    public void updateStatus(ActionEvent event) {
        List<ItsMaster> members = itsFacade.findItsMasterMembersUnderHofId(getSelected().getItsNo());

        itsFacade.save(getSelected());

        String msg = getSelected().getFullName() + " ITS & Sabil Status Updated";
        if (members.size() > 0) {
            for (ItsMaster m : members) {
                m.setItsStatus(getSelected().getItsStatus());
                m.setSabilStatus(getSelected().getSabilStatus());
                itsFacade.save(m);
            }
            msg = msg + " along with " + members.size() + " family members";
        }

        JsfUtil.addSuccessMessage("Update", msg);
    }

    public List<ItsMaster> completeFilter(String criteria) {
        System.out.println("completeFilter: " + criteria);

        //return ejbFacade.findRange(0, 10, "firstName", "ASC",
        //        ejbFacade.createFilters(new String[]{"itsNo", "firstName"}, criteria));
        //Page<ItsMaster> page = itsFacade.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName")));
        return Utils.sublist(itsFacade.findItsMasterByCriteria(criteria), 10);
    }

    public List<ItsMaster> completeFilterActiveHof(String criteria) {
        System.out.println("completeFilterActiveHof: " + criteria);

        //return ejbFacade.findRange(0, 10, "firstName", "ASC",
        //        ejbFacade.createFilters(new String[]{"itsNo", "firstName"}, criteria));
        //return itsFacade.findRangeActvieHof(new int[]{0, 20}, criteria);
        return Utils.sublist(itsFacade.findHofItsStatus("ACTIVE"), 10);
    }
    
    public List<ItsMaster> completeFilterActiveSabil(String criteria){
        System.out.println("completeFilterActiveSabil: " + criteria);
        
        return Utils.sublist(itsFacade.findHofBySabilStatus("PAYS_SABIL", criteria), 10);
    }
    
    public List<ItsMaster> completeFilterActiveSabilAndCommittee(String criteria){
        System.out.println("completeFilterActiveSabilAndCommittee: " + criteria);
        
        return Utils.sublist(itsFacade.findHofBySabilStatusOrItsStatus("PAYS_SABIL", "COMMITTEE", criteria), 10);
    }
    
    public List<ItsMaster> completeFilterHOFByThaaliNotRegistered(String criteria){
        System.out.println("completeFilterHOFByThaaliNotRegistered: " + criteria);
        
        List<ItsMaster> hof = itsFacade.findHofBySabilStatus("PAYS_SABIL", criteria);
        hof.removeAll(itsFacade.findHOFByThaaliRegistered("PAYS_SABIL", criteria));
        
        //return Utils.sublist(hof, 10);
        return hof;
    }
    
    /*public List<ItsMaster> sublist(List<ItsMaster> list, int limit){
        if(list==null){
            return null;
        }
        if(list.size()>limit){
            return list.subList(0, limit);
        }
        return list;
    }*/

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        progressMessage = file.getFileName() + " uploaded";
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            saveUploadedItsMaster();
        }
    }

    public void saveUploadedItsMaster() {
        try {
            progressMessage = progressMessage + "\nNow Processing...\n";
            //readColumns(file.getInputstream());
            //System.out.println("Processed Columns");
            processCsvFile(file.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(ItsMasterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void processCsvFile(InputStream stream) {
        try {

            String l;
            int count = 0;
            InputStreamReader isr = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(isr);
            List<String> lines = new LinkedList<String>();
            while ((l = br.readLine()) != null) {
                count++;
                lines.add(l);
            }

            progressMessage = progressMessage + "\n" + "Total " + count + " records found";
            count_new = 0;
            count_updated = 0;

            List<String> data = new LinkedList<String>();
            isr = new InputStreamReader(stream);
            br = new BufferedReader(isr);
            int i = 0;
            //while ((line = br.readLine()) != null) {
            for (String line : lines) {
                if (i == 0) {
                    //this is header row, skip it
                    i++;
                    continue;
                }
                String[] tokens = line.split(",");
                //StringTokenizer tokens = new StringTokenizer(line, ",");
                //System.out.println(line);
                data = Arrays.asList(tokens);
                //int j=0;
                //while (tokens.hasMoreTokens()) {
                //    String token = tokens.nextToken();
                //    System.out.println(j + ": " + token);
                //    j++;
                //    if(token==null){
                //        token = "";
                //    }
                //    data.add(token);
                //}
                i++;
                progress = i * 100 / count;
                processItsData(data);
            }
            System.out.println("New Created: " + count_new);
            progressMessage = "New Created: " + count_new + "\n" + progressMessage;
            System.out.println("New Updated: " + count_updated);
            progressMessage = "New Updated: " + count_updated + "\n" + progressMessage;
            System.out.println("Total: " + count);
            progressMessage = "Total: " + count + "\n" + progressMessage;
        } catch (Exception er) {
            er.printStackTrace();
        }
    }


    public void processItsData(List<String> data) {
        if (data == null) {
            return;
        }

        int itsNo = Integer.parseInt(data.get(0));
        ItsMaster its = itsFacade.findById(itsNo).get();

        if (importFormat.equalsIgnoreCase("Address")) {
            //"itsNo","hofId",
            //"prefix","firstName","prefix2","secondName",
            //"fatherSurname","surname","fullName","arabicName",
            //"mobile1","mobile2","tel1","tel2",
            //"fax1","fax2","email1","email2",
            //"flatNo","buildingNo","roadNo","blockNo","areaName",
            //"gender","cpr", "maritalStatus",
            //"vatan", "nationality"
            boolean create = false;
            if (its == null) {
                create = true;
                its = new ItsMaster();
                its.setItsNo(itsNo);
                //its.setHofId(Integer.parseInt(data.get(1)));
                //its.setPrefix(getString(data.get(2)));
                //its.setFirstName(getString(data.get(3)));
                //its.setPrefix2(getString(data.get(4)));
                //its.setSecondName(getString(data.get(5)));
                //its.setFatherSurname(getString(data.get(6)));
                //its.setHusbandName(getString(data.get(7)));
                //its.setSurname(getString(data.get(8)));
                //its.setFullName(getString(data.get(9)));
                //its.setArabicName(getString(data.get(10)));
                its.setMobile1(getString(data.get(11)));
                its.setMobile2(getString(data.get(12)));
                its.setTel1(getString(data.get(13)));
                its.setTel2(getString(data.get(14)));
                its.setFax1(getString(data.get(15)));
                its.setFax2(getString(data.get(16)));
                its.setEmail1(getString(data.get(17)));
                its.setEmail2(getString(data.get(18)));
                //skip flat no 19
                //skip bldg no 20
                //skip road no 21
                //skip block no 22
                //skip area no 23
                its.setGender(getString(data.get(24)));
                its.setCpr(getString(data.get(25)));
                //its.setMaritalStatus(getString(data.get(26)));
                its.setVatan(getString(data.get(27)));
                its.setNationality(getString(data.get(28)));
            }
            its.setHofId(Integer.parseInt(data.get(1)));
            its.setPrefix(getString(data.get(2)));
            its.setFirstName(getString(data.get(3)));
            its.setPrefix2(getString(data.get(4)));
            its.setSecondName(getString(data.get(5)));
            its.setFatherSurname(getString(data.get(6)));
            its.setHusbandName(getString(data.get(7)));
            its.setSurname(getString(data.get(8)));
            its.setFullName(getString(data.get(9)));
            its.setArabicName(getString(data.get(10)));
            its.setMaritalStatus(getString(data.get(26)));

            if (create) {
                itsFacade.save(its);
                progressMessage = its + " -> CREATED NEW" + "\n" + progressMessage;
                System.out.println(its + " -> CREATED NEW");
                count_new++;
            } else {
                itsFacade.save(its);
                progressMessage = its + " -> UPDATED" + "\n" + progressMessage;
                //System.out.println(its + " -> UPDATED");
                count_updated++;
            }
        } else {    //ItsStatus
            if (its == null) {
                System.out.println("Cannot & Update Find ItsStatus: " + itsNo);
                progressMessage = "Cannot & Update Find ItsStatus: " + itsNo + "\n" + progressMessage;
            } else {
                its.setItsStatus(getString(data.get(1)));
                itsFacade.save(its);
                System.out.println("ItsStatus Updated: " + its);
                progressMessage = "ItsStatus Updated: " + its + "\n" + progressMessage;
            }
        }
    }

    public String getString(String data) {
        if (data == null) {
            return null;
        } else if (data.trim().length() == 0) {
            return null;
        }
        return data.trim();
    }

    public int getInt(String data) {
        if (data == null) {
            return 0;
        }
        return Integer.parseInt(data);
    }

    /**
     * @return the progressMessage
     */
    public String getProgressMessage() {
        return progressMessage;
    }

    /**
     * @param progressMessage the progressMessage to set
     */
    public void setProgressMessage(String progressMessage) {
        this.progressMessage = progressMessage;
    }

    /**
     * @return the progress
     */
    public int getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /**
     * @return the importFormat
     */
    public String getImportFormat() {
        return importFormat;
    }

    /**
     * @param importFormat the importFormat to set
     */
    public void setImportFormat(String importFormat) {
        this.importFormat = importFormat;
    }

}
