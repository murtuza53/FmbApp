/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.service;

import org.ejms.entity.Users;

/**
 *
 * @author Burhani152
 */
public interface UserService {
    
    void save(Users user);

    Users findByUserName(String username);
}
