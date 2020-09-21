/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.app;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.fmb.entity.ItsMaster;
import org.fmb.entity.UserRole;
import org.fmb.entity.Users;
import org.fmb.repo.ItsMasterRepository;
import org.fmb.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

/**
 *
 * @author Burhani152
 */
@Named
@SessionScope
public class UserSession implements Serializable {

    private ItsMaster loggedInUser;

    private Users user;

    @Autowired
    private ItsMasterRepository itsRepo;

    @Autowired
    private UserRepository userRepo;
    
    public UserSession() {

    }

    @PostConstruct
    public void init() {
        loadUser();
    }

    public void loadUser() {
        int its = Integer.parseInt(getLoggedInIts());
        if (its > 0) {
            loggedInUser = itsRepo.getOne(its);
            if (loggedInUser != null) {
                setUser(userRepo.getOne(its));
            }
        }
    }

    public String getLoggedInIts() {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //if (auth == null) {
        //    resetUser();
        //    return "0";
        //}
        //if (auth != null) {
        //    return auth.getName();
        //}
        return "0";
    }

    public void resetUser() {
        loggedInUser = null;
        setUser(null);
    }

    public String getLoggedInUserName() {
        if (loggedInUser == null) {
            loadUser();
        }
        if (loggedInUser != null) {
            return loggedInUser.getFirstName() + " " + loggedInUser.getSurname();
        }
        return "Error";
    }

    public String getUserRole() {
        if (getUser() == null) {
            loadUser();
        }
        if (getUser() != null) {
            return getUser().getUserRolesActive();
        }
        return "Error";
    }

    /**
     * @return the loggedInUser
     */
    public ItsMaster getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * @param loggedInUser the loggedInUser to set
     */
    public void setLoggedInUser(ItsMaster loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * @return the user
     */
    public Users getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Users user) {
        this.user = user;
    }

    public String getMegaMenuStyle(){
        if(isAdminUser()){
            return "";
        }
        return "display: none;";
    }
    
    public boolean isAdminUser(){
        if(getUser()!=null){
            for(UserRole er: getUser().getUserroles()){
                if(er.getRolename().equalsIgnoreCase("ADMIN")){
                    return true;
                }
            }
        }
        return false;
    }
}
