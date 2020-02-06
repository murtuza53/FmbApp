/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.ejms.entity.House;
import org.ejms.entity.ItsMaster;
import org.ejms.repo.HouseRepository;
import org.ejms.repo.ItsMasterRepository;
import org.ejms.util.JsfUtil;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Burhani152
 */
@Named(value = "houseController")
@ViewScoped
public class HouseController extends AbstractController<House> {

    //@Autowired
    private HouseRepository houseFacade;

    @Autowired
    ItsMasterRepository itsRepo;

    private List<House> houseList;

    private TreeNode rootNode;

    private TreeNode selectedTreeNode;

    private ItsMaster selectedHof;

    private List<ItsMaster> membersList;

    private List<ItsMaster> selectedMembersList;

    @Autowired
    public HouseController(HouseRepository repo) {
        // Inform the Abstract parent controller of the concrete ItsMaster Entity
        super(House.class, repo);
        this.houseFacade = repo;
    }

    @PostConstruct
    public void init() {
        createHouseTableModel();
    }

    public List<House> getHouseList() {
        if (houseList == null) {
            houseList = houseFacade.findByCriteria("");
        }
        JsfUtil.addSuccessMessage("Success", "Total " + houseList.size() + " houses listed");
        return houseList;
    }

    @Override
    public void save() {
        houseFacade.save(getSelected());
        createHouseTableModel();
        JsfUtil.addSuccessMessage("Success", getSelected().getHousename() + " saved");
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public boolean getDisableEdit() {
        if (getSelectedTreeNode() == null) {
            return true;
        } else if (getSelected().isItsNo()) {
            return true;
        }
        return false;
    }

    public void createHouseTableModel() {
        rootNode = new DefaultTreeNode(new House(0, "Root", "Root"), null);

        houseList = null;
        getHouseList();

        for (House h : houseList) {
            h.setStyle("font-weight: bold");
            TreeNode tn = new DefaultTreeNode(h, rootNode);
            addMembers(itsRepo.findHofInHouse(h.getHouseid()), tn);
            addMembers(itsRepo.findOtherMembersInFamilyHouse(h.getHouseid()), tn);
        }
    }

    public void addMembers(List<ItsMaster> members, TreeNode houseNode) {
        if (members != null) {
            for (ItsMaster i : members) {
                House h = new House();
                h.setStyle("");
                h.setHouseid(i.getItsNo());
                h.setAddress(i.getFullName());
                new DefaultTreeNode(h, houseNode);
            }
        }
    }

    public void removeMember(House h) {
        setSelected(h);
        if (getSelected() == null) {
            JsfUtil.addErrorMessage("Error", "No Member to remove");
        } else {
            if (getSelected().isItsNo()) {
                ItsMaster i = itsRepo.findById(getSelected().getHouseid()).get();
                i.setHouseid(null);
                itsRepo.save(i);
                createHouseTableModel();
                JsfUtil.addSuccessMessage("Success", i.getFullName() + " removed from house");
            } else {
                JsfUtil.addErrorMessage("Error", "Invalid Selection");
            }
        }
    }

    public void refreshMemberList() {
        if (selectedHof != null) {
            membersList = itsRepo.findItsMasterMembersUnderHofId(selectedHof.getHofId());
        }
    }

    public void saveMembers() {
        if (selectedMembersList != null) {
            for (ItsMaster i : selectedMembersList) {
                i.setHouseid(getSelected());
                itsRepo.save(i);
            }
            createHouseTableModel();
            JsfUtil.addSuccessMessage("Success", selectedMembersList.size() + " members added in " + getSelected().getHousename());
        } else {
            JsfUtil.addErrorMessage("No Members found to add");
        }
    }

    /**
     * @return the selectedTreeNode
     */
    public TreeNode getSelectedTreeNode() {
        return selectedTreeNode;
    }

    /**
     * @param selectedTreeNode the selectedTreeNode to set
     */
    public void setSelectedTreeNode(TreeNode selectedTreeNode) {
        this.selectedTreeNode = selectedTreeNode;
        if (selectedTreeNode != null) {
            if (selectedTreeNode.getData() != null) {
                setSelected((House) selectedTreeNode.getData());
            }
        }
    }

    /**
     * @return the selectedHof
     */
    public ItsMaster getSelectedHof() {
        return selectedHof;
    }

    /**
     * @param selectedHof the selectedHof to set
     */
    public void setSelectedHof(ItsMaster selectedHof) {
        this.selectedHof = selectedHof;
    }

    /**
     * @return the membersList
     */
    public List<ItsMaster> getMembersList() {
        return membersList;
    }

    /**
     * @param membersList the membersList to set
     */
    public void setMembersList(List<ItsMaster> membersList) {
        this.membersList = membersList;
    }

    /**
     * @return the selectedMembersList
     */
    public List<ItsMaster> getSelectedMembersList() {
        return selectedMembersList;
    }

    /**
     * @param selectedMembersList the selectedMembersList to set
     */
    public void setSelectedMembersList(List<ItsMaster> selectedMembersList) {
        this.selectedMembersList = selectedMembersList;
    }

}
