/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ejms.entity.UserRole;
import org.ejms.entity.Users;
import org.ejms.repo.UserRepository;
import org.ejms.repo.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author Burhani152
 */
@Service
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserRoleRepository uroleRepo;
    
    @Autowired
    UserRepository userRepo;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    public CustomSuccessHandler(){
        super();
    }
    
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";

        UserDetails ud = (UserDetails)authentication.getPrincipal();
        System.out.println("determineTargetUrl: " + ud);
        System.out.println("userRepo: " + userRepo);
        Users user = userRepo.findByUserName(ud.getUsername());
        //Users user = userRepo.findById(Integer.parseInt(ud.getUsername())).get();
        
        
        UserRole maxRole = null;
        
        for(UserRole ur: user.getUserroles()){
            if(maxRole==null){
                maxRole = ur;
            } else if(ur.getLevel()>=maxRole.getLevel()){
                maxRole = ur;
            }
        }
        
        /*Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isDba(roles)) {
            url = "/db";
        } else if (isAdmin(roles)) {
            url = "/dashboard";
        } else if (isUser(roles)) {
            url = "/home";
        } else {
            url = "/access";
        }*/

        if(maxRole==null){
            return "access";
        }
        
        return maxRole.getSuccessurl();
    }

    private boolean isUser(List<String> roles) {
        if (roles.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }

    private boolean isAdmin(List<String> roles) {
        if (roles.contains("ADMIN")) {
            return true;
        }
        return false;
    }

    private boolean isDba(List<String> roles) {
        if (roles.contains("ROLE_DBA")) {
            return true;
        }
        return false;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
