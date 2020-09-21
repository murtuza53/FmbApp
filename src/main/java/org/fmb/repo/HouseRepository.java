/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo;

import java.util.List;
import org.fmb.entity.House;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface HouseRepository extends BaseRepository<House, Integer>{
    
    @Query("SELECT OBJECT(t) FROM House as t "
                + "WHERE (TRIM(t.houseid) LIKE %:criteria% OR t.housecode LIKE %:criteria% OR "
                + "t.housename LIKE %:criteria%) "
                + "ORDER BY t.housename asc")
    List<House> findByCriteria(@Param("criteria") String criteria);
    
    @Query("SELECT OBJECT(t) FROM House as t "
                + "WHERE (TRIM(t.houseid) LIKE %:criteria% OR t.housecode LIKE %:criteria% OR "
                + "t.housename LIKE %:criteria%) AND t.housetype=:housetype "
                + "ORDER BY t.housename asc")
    List<House> findByIts(@Param("criteria") String criteria, @Param("housetype") String housetype);
   
}
