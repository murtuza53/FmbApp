/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo;

import java.util.List;
import org.fmb.entity.ItsMaster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface ItsMasterRepository extends BaseRepository<ItsMaster, Integer>{
    
    @Query("SELECT OBJECT(t) FROM ItsMaster as t "
                + "WHERE (TRIM(t.itsNo) LIKE %:criteria% OR t.firstName LIKE %:criteria% OR "
                + "t.secondName LIKE %:criteria% OR t.surname LIKE %:criteria%) AND (t.itsStatus='ACTIVE' OR t.itsStatus='COMMITTEE') "
                + "ORDER BY t.firstName asc")
    List<ItsMaster> findItsMasterByCriteria(@Param("criteria") String criteria);
    
    @Query("SELECT OBJECT(t) FROM ItsMaster as t "
                + "WHERE (TRIM(t.itsNo) LIKE %:criteria% OR t.firstName LIKE %:criteria% OR "
                + "t.secondName LIKE %:criteria% OR t.surname LIKE %:criteria%) "
                + " AND t.itsNo = t.hofId AND t.itsStatus='ACTIVE' "
                + "ORDER BY t.firstName asc")
    List<ItsMaster> findItsMasterActvieHof(@Param("criteria") String criteria);
    
    @Query("SELECT i FROM ItsMaster i WHERE i.hofId = :hofId AND i.itsStatus='ACTIVE' ORDER BY i.firstName")
    List<ItsMaster> findItsMasterMembersUnderHofId(@Param("hofId") int hofId);
    
    @Query("SELECT i FROM ItsMaster i WHERE i.itsNo = i.hofId AND i.sabilStatus=:sabilStatus "
            + "AND (TRIM(i.itsNo) LIKE %:criteria% OR i.firstName LIKE %:criteria% OR "
                + "i.secondName LIKE %:criteria% OR i.surname LIKE %:criteria%) "
            + "ORDER BY i.firstName")
    List<ItsMaster> findHofBySabilStatus(@Param("sabilStatus") String sabilStatus, @Param("criteria") String criteria);
    
    @Query("SELECT i FROM ItsMaster i WHERE (i.itsStatus=:itsStatus OR (i.itsNo = i.hofId AND i.sabilStatus=:sabilStatus)) "
            + "AND (TRIM(i.itsNo) LIKE %:criteria% OR i.firstName LIKE %:criteria% OR "
                + "i.secondName LIKE %:criteria% OR i.surname LIKE %:criteria%)  "
            + "ORDER BY i.firstName")
    List<ItsMaster> findHofBySabilStatusOrItsStatus(@Param("sabilStatus") String sabilStatus, 
                                                    @Param("itsStatus") String itsStatus, 
                                                    @Param("criteria") String criteria);

    //@Query("SELECT i FROM ItsMaster i WHERE i.itsNo = i.hofId AND i.sabilStatus=:sabilStatus "
    //        + "AND i.itsNo NOT IN (SELECT ta.itsNo.itsNo FROM ThaaliAllocation ta WHERE ta.closeDate IS NULL)")
    //List<ItsMaster> findHOFByThaaliNotRegistered(@Param("sabilStatus") String sabilStatus);
    
    @Query(value="SELECT DISTINCT i.* FROM itsmaster i, thaaliallocation ta "
            + "WHERE i.itsNo = ta.itsNo AND i.sabilStatus=:sabilStatus AND ta.closeDate IS NULL "
            + "AND (TRIM(i.itsNo) LIKE %:criteria% OR i.firstName LIKE %:criteria% OR "
                + "i.secondName LIKE %:criteria% OR i.surname LIKE %:criteria%) "
            + "ORDER BY i.firstName",
            nativeQuery = true)
    List<ItsMaster> findHOFByThaaliRegistered(@Param("sabilStatus") String sabilStatus, @Param("criteria") String criteria);

    @Query("SELECT i FROM ItsMaster i WHERE i.itsNo = i.hofId AND i.itsStatus=:itsStatus")
    List<ItsMaster> findHofItsStatus(@Param("itsStatus") String itsStatus);
    
    @Query("SELECT i FROM ItsMaster i WHERE i.itsNo IN (:itsNos)")
    List<ItsMaster> findItsMasterIn(@Param("itsNos") List<String> itsNos);
    
    @Query("SELECT i FROM ItsMaster i WHERE i.houseid.houseid=:houseid")
    List<ItsMaster> findByHouseId(@Param("houseid") int houseid);
    
    @Query("SELECT i FROM ItsMaster i WHERE i.houseid.houseid=:houseid AND i.itsNo=i.hofId")
    List<ItsMaster> findHofInHouse(@Param("houseid") int houseid);

    @Query("SELECT i FROM ItsMaster i WHERE i.houseid.houseid=:houseid AND i.itsNo<>i.hofId")
    List<ItsMaster> findOtherMembersInFamilyHouse(@Param("houseid") int houseid);
}
