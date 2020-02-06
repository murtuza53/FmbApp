/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.repo.famb;

import java.util.List;
import org.ejms.entity.famb.ThaaliAllocation;
import org.ejms.repo.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface ThaaliAllocationRepository extends BaseRepository<ThaaliAllocation, Integer>{
 
    @Query("SELECT t FROM ThaaliAllocation t ORDER BY t.thaaliNo.thaaliNo")
    List<ThaaliAllocation> getThaaliAllocationAll();

    @Query("SELECT t FROM ThaaliAllocation t WHERE t.active='TRUE' AND t.thaaliNo.thaaliNo=:thaaliNo")
    List<ThaaliAllocation> getThaaliAllocationByThaaliNo(@Param("thaaliNo") Integer thaaliNo);

    @Query("SELECT OBJECT(t) FROM ThaaliAllocation t WHERE t.closeDate IS NULL ORDER BY t.itsNo.firstName")
    List<ThaaliAllocation> findThaaliAllocationActive();
    
    @Query("SELECT OBJECT(t) FROM ThaaliAllocation t WHERE t.itsNo.itsNo=:itsNo")
    ThaaliAllocation findThaaliAllocationByItsNo(@Param("itsNo") Integer itsNo);

}
