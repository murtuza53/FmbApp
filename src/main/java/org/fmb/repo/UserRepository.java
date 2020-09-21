/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo;

import java.util.List;
import org.fmb.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface UserRepository extends BaseRepository<Users, Integer>{
    
    Users findByUserName(String userName);
    
    @Query("SELECT OBJECT(a) FROM Users as a "
                + "ORDER BY a.active desc")
    List<Users> findUsersSortByActive();


    @Query("SELECT OBJECT(a) FROM Users as a "
                + "WHERE a.active = :status "
                + "ORDER BY a.active desc")
    List<Users> findUsersByActive(@Param("status") Boolean status);
}
