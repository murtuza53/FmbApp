/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.fmb.entity.ItsMaster;
import org.fmb.repo.ItsMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author FatemaLaptop
 */
@Named(value = "liveMajlisController")
@ViewScoped
public class LiveMajlisController implements Serializable {

    @Autowired
    private ItsMasterRepository itsRepo;

    private String itsNo;

    private boolean verified = false;

    public LiveMajlisController() {
    }

    public void majlisLogin() {
        Optional<ItsMaster> its = itsRepo.findById(Integer.parseInt(itsNo));
        if (its.isPresent()) {
            System.out.println("Login By: " + its.get() + "\t@\t" + new Date());
        } else {
            System.out.println("Failed Attempt By: " + itsNo + "\t@\t" + new Date());
        }
    }

    /**
     * @return the itsRepo
     */
    public ItsMasterRepository getItsRepo() {
        return itsRepo;
    }

    /**
     * @param itsRepo the itsRepo to set
     */
    public void setItsRepo(ItsMasterRepository itsRepo) {
        this.itsRepo = itsRepo;
    }

    /**
     * @return the itsNo
     */
    public String getItsNo() {
        return itsNo;
    }

    /**
     * @param itsNo the itsNo to set
     */
    public void setItsNo(String itsNo) {
        this.itsNo = itsNo;
    }

    /**
     * @return the verified
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

}
