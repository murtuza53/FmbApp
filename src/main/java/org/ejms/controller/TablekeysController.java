package org.ejms.controller;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ejms.entity.AccountDocument;
import org.ejms.entity.Tablekeys;
import org.ejms.repo.TablekeysRepository;
import org.ejms.util.TableKeyFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Named(value = "tablekeysController")
@Component
public class TablekeysController extends AbstractController<Tablekeys> {

    private TablekeysRepository tablekeys;
    
    @Autowired
    public TablekeysController(TablekeysRepository repo) {
        // Inform the Abstract parent controller of the concrete Tablekeys Entity
        super(Tablekeys.class, repo);
        this.tablekeys = repo;
    }

    public synchronized long getNextValue(String table) {
        Optional<Tablekeys> o = tablekeys.findById(table);
        Tablekeys key = null;// = tablekeys.findById(table);
        if(o.isPresent()){
            key = o.get();
        }
        System.out.println(table + ": key=>" + key);
        long value = 2;
        if (key == null) {
            try {
                key = new Tablekeys(table);
                key.setNextValue(value + "");
                tablekeys.save(key);
            } catch (Exception ex) {
                Logger.getLogger(TablekeysController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } else {
            value = Long.parseLong(key.getNextValue()) + 1;
            key.setNextValue(value + "");
            try {
                tablekeys.save(key);
            } catch (Exception ex) {
                Logger.getLogger(TablekeysController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        value = value -1;
        System.out.println("getNextValue: " + table + value);
        return value;
    }
    
    public synchronized long getAccountNextValue(String table) {
        Optional<Tablekeys> o = tablekeys.findById(table);
        Tablekeys key = null;  // = find(table);
        if(o.isPresent()){
            key = o.get();
        }
        long value = 2;
        if (key == null) {
            try {
                key = new Tablekeys(table);
                key.setNextValue(value + "");
                tablekeys.save(key);
            } catch (Exception ex) {
                Logger.getLogger(TablekeysController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } else {
            value = Long.parseLong(key.getNextValue()) + 1;
            key.setNextValue(value + "");
            try {
                tablekeys.save(key);
            } catch (Exception ex) {
                Logger.getLogger(TablekeysController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        return value - 1;
    }

    public synchronized String getAVNextNo(int year) {
        return TableKeyFormatter.formatTableKey(AccountDocument.ACCOUNT_VOUCHER, getNextValue(AccountDocument.ACCOUNT_VOUCHER + year), year);
    }

    public synchronized String getJVNextNo(int year) {
        return TableKeyFormatter.formatTableKey(AccountDocument.JOURNAL_VOUCHER, getNextValue(AccountDocument.JOURNAL_VOUCHER + year), year);
    }

    public synchronized String getPVNextNo(int year) {
        return TableKeyFormatter.formatTableKey(AccountDocument.PAYMENT_VOUCHER, getNextValue(AccountDocument.PAYMENT_VOUCHER + year), year);
    }

    public synchronized String getRCTNextNo(int year) {
        return TableKeyFormatter.formatTableKey(AccountDocument.RECEIPT, getNextValue(AccountDocument.RECEIPT + year), year);
    }

    public synchronized String getSRCTNextNo(int year) {
        return TableKeyFormatter.formatTableKey(AccountDocument.SABIL_RECEIPT_VOUCHER, getNextValue(AccountDocument.SABIL_RECEIPT_VOUCHER + year), year);
    }
}
