/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.controller.famb;

import java.util.LinkedList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.fmb.controller.AbstractController;
import org.fmb.entity.Sabil;
import org.fmb.entity.famb.ThaaliAllocation;
import org.fmb.entity.famb.ThaaliPickupPerson;
import org.fmb.helper.FambHelper;
import org.fmb.repo.SabilRepository;
import org.fmb.repo.famb.ThaaliAllocationRepository;
import org.fmb.repo.famb.ThaaliPickupPersonRepository;
import org.fmb.util.JsfUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Burhani152
 */
@Named(value = "thaaliAllocationController")
@ViewScoped
public class ThaaliAllocationController extends AbstractController<ThaaliAllocation> {

    ThaaliAllocationRepository taRepo;

    @Autowired
    SabilRepository sabilRepo;

    @Autowired
    ThaaliPickupPersonRepository tppRepo;

    private List<ThaaliAllocation> taList;
    private List<FambHelper> helperList;

    private List<ThaaliPickupPerson> tppList;

    private FambHelper selectedHelper;

    @Autowired
    public ThaaliAllocationController(ThaaliAllocationRepository repo) {
        super(ThaaliAllocation.class, repo);
        this.taRepo = repo;
    }

    public List<ThaaliAllocation> getActiveList() {
        if (taList == null) {
            taList = taRepo.findThaaliAllocationActive();
            helperList = new LinkedList<FambHelper>();
            FambHelper fh;
            for (ThaaliAllocation ta : taList) {
                List<Sabil> sl = sabilRepo.findSabilByItsNo(ta.getItsNo().getItsNo());
                if (sl != null && sl.size() > 0) {
                    Sabil s = sl.get(0);
                    fh = new FambHelper(s, ta);
                    helperList.add(fh);
                } else {
                    fh = new FambHelper(null, ta);
                }
            }
        }
        JsfUtil.addSuccessMessage("Info", "Total " + taList.size() + " Members Listed");
        return taList;
    }

    /**
     * @return the helperList
     */
    public List<FambHelper> getHelperList() {
        if (helperList == null) {
            getActiveList();
        }
        return helperList;
    }

    public List<ThaaliPickupPerson> getPickupPersonList() {
        if (tppList == null) {
            tppList = tppRepo.findAll();
        }
        return tppList;
    }

    /**
     * @return the selectedHelper
     */
    public FambHelper getSelectedHelper() {
        return selectedHelper;
    }

    /**
     * @param selectedHelper the selectedHelper to set
     */
    public void setSelectedHelper(FambHelper selectedHelper) {
        this.selectedHelper = selectedHelper;
        if (selectedHelper != null) {
            super.setSelected(selectedHelper.getThaaliAllocation());
        } else {
            super.setSelected(null);
        }
    }

}
