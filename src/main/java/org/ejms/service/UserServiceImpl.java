/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.service;

import java.util.Optional;
import org.ejms.entity.Users;
import org.ejms.repo.UserRepository;
import org.ejms.repo.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Burhani152
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Users user) {
        //default encript round is 10
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserroles(roleRepository.findAll());
        userRepository.save(user);
    }

    @Override
    public Users findByUserName(String username) {
        //return userRepository.findByUserName(username);
        Integer itsNo = null;
        try {
            itsNo = Integer.parseInt(username);
        } catch (NumberFormatException err) {
            System.out.println("Invalid user " + username);
            return null;
        }

        Optional<Users> user = userRepository.findById(itsNo);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

}
