/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.repo;

import org.ejms.entity.Authentication;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface AuthenticationRepository extends BaseRepository<Authentication, Long>{
    
}
