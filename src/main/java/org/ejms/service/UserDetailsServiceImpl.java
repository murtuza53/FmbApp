/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.ejms.entity.UserRole;
import org.ejms.entity.Users;
import org.ejms.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Burhani152
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Users user = userRepository.findByUserName(username)
        
        Integer itsNo = Integer.parseInt(username);
        Optional<Users> us = userRepository.findById(itsNo);
        if (!us.isPresent()) throw new UsernameNotFoundException(username);
        
        Users user = us.get();
        if(!user.getActive()) throw new UsernameNotFoundException(username);
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : user.getUserroles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRolename()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUserNo()+"", user.getPassword(), grantedAuthorities);   
    }
    
}
