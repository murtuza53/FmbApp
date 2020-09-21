/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo;

import java.util.List;
import org.fmb.entity.Sabil;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Burhani152
 */
public interface SabilRepository extends BaseRepository<Sabil, Long>{
    
    @Query("SELECT s FROM Sabil s WHERE s.itsNo.itsNo=:itsNo")
    List<Sabil> findSabilByItsNo(@Param("itsNo") int itsNo);
    
    @Query("SELECT OBJECT(s) FROM Sabil s WHERE (TRIM(s.itsNo.itsNo) LIKE %:criteria% "
                + "OR s.itsNo.cpr LIKE %:criteria% "
                + "OR s.itsNo.firstName LIKE %:criteria% "
                + "OR s.itsNo.secondName LIKE %:criteria% "
                + "OR s.itsNo.surname LIKE %:criteria%) AND s.itsNo.itsStatus='ACTIVE' "
                + "ORDER BY s.itsNo.firstName, s.itsNo.secondName, s.itsNo.surname")
    List<Sabil> findSabilByItsNoBySearchCriteria(@Param("criteria") String criteria);

    @Query("SELECT OBJECT(s) FROM Sabil s WHERE (TRIM(s.itsNo.itsNo) LIKE '%:criteria:%' "
                + "OR s.itsNo.cpr LIKE '%:criteria%' "
                + "OR s.itsNo.firstName LIKE '%:criteria%' "
                + "OR s.itsNo.secondName LIKE '%:criteria%' "
                + "OR s.itsNo.surname LIKE '%:criteria%' ) "
                + "AND s.sabilCategoryNo.parentNo = :sabilCategoryNo AND s.itsNo.itsStatus='ACTIVE' "
                + "ORDER BY s.itsNo.firstName, s.itsNo.secondName, s.itsNo.surname")
    List<Sabil> findSabilByItsNoBySabilCategoryNo(@Param("criteria") String criteria, @Param("sabilCategoryNo") long sabilCategoryNo);
    
    @Query("SELECT s FROM Sabil s WHERE s.businessNo.businessNo=:businessNo")
    List<Sabil> findSabilByBusinessNo(@Param("businessNo") long businessNo);
    
    @Query("SELECT s FROM Sabil s WHERE TRIM(s.businessNo.businessNo) LIKE %:criteria% "
                + "OR s.businessNo.crNo LIKE %:criteria% "
                + "OR s.businessNo.businessName LIKE %:criteria% "
                + "ORDER BY s.businessNo.businessName")
    List<Sabil> findSabilByBusinessNoBySearchCriteria(@Param("criteria") String criteria);
    
    @Query("SELECT s FROM Sabil s WHERE (TRIM(s.businessNo.businessNo) LIKE '%:criteria%' "
                + "OR s.businessNo.crNo LIKE '%:criteria%' "
                + "OR s.businessNo.businessName LIKE '%:criteria%') "
                + "AND s.sabilCategoryNo.parentNo = :sabilCategoryNo "
                + "ORDER BY s.businessNo.businessName")
    List<Sabil> findSabilByBusinessNoBySabilCategoryNo(@Param("criteria") String criteria, @Param("sabilCategoryNo") long sabilCategoryNo);
    
    @Query(value = "select distinct itsNo from accountdetails where year>=:fromYear AND year<=:toYear", nativeQuery = true)
    List<String> findDistinctItsFromAccountDetailsBetweenHijriYear(@Param("fromYear") int fromYear, @Param("toYear") int toYear);
}
