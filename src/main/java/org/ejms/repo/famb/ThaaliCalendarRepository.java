/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.repo.famb;

import java.util.Date;
import org.ejms.entity.famb.ThaaliCalendar;
import org.ejms.repo.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface ThaaliCalendarRepository extends BaseRepository<ThaaliCalendar, Integer>{
    
    @Query("SELECT t FROM ThaaliCalendar t WHERE t.thaalidate = :thaaliDate ORDER BY t.thaalidate asc")
    ThaaliCalendar findThaaliCalendarByDateSingle(@Param("thaaliDate") Date thaaliDate);
}
