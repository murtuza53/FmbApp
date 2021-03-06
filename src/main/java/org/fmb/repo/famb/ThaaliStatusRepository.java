/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo.famb;

import java.util.List;
import org.fmb.entity.famb.ThaaliStatus;
import org.fmb.repo.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface ThaaliStatusRepository extends BaseRepository<ThaaliStatus, Integer>{
    
    @Query("SELECT t FROM ThaaliStatus t WHERE t.thaaliNo.thaaliNo = :thaaliNo ORDER BY t.requestDate DESC")
    List<ThaaliStatus> findThaaliStatusByThaaliNo(@Param("thaaliNo")Integer thaaliNo);
}
