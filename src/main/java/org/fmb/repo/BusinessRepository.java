/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo;

import java.util.List;
import org.fmb.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface BusinessRepository extends BaseRepository<Business, Long> {
 
    @Query("SELECT e FROM Business e "
                + "WHERE TRIM(e.businessNo) LIKE %:criteria% OR "
                + "e.crNo LIKE %:criteria% OR "
                + "e.businessName LIKE %:criteria% OR "
                + "e.businessType LIKE %:criteria% OR "
                + "e.address LIKE %:criteria% OR "
                + "e.officeNumber1 LIKE %:criteria% OR "
                + "e.officeNumber2 LIKE %:criteria% OR "
                + "e.faxNumber1 LIKE %:criteria% OR "
                + "e.faxNumber2 LIKE %:criteria% OR "
                + "e.email1 LIKE %:criteria% OR "
                + "e.email2 LIKE %:criteria% "
                + "ORDER BY e.businessName")
    List<Business> findBusinessBySearchCriteria(@Param("criteria") String criteria);

    @Query("SELECT e FROM Business e "
                + "WHERE TRIM(e.businessNo) LIKE '%:criteria%' OR "
                + "e.crNo LIKE '%:criteria%' OR "
                + "e.businessName LIKE '%:criteria%' OR "
                + "e.businessType LIKE '%:criteria%' OR "
                + "e.address LIKE '%:criteria%' OR "
                + "e.officeNumber1 LIKE '%:criteria%' OR "
                + "e.officeNumber2 LIKE '%:criteria%' OR "
                + "e.faxNumber1 LIKE '%:criteria%' OR "
                + "e.faxNumber2 LIKE '%:criteria%' OR "
                + "e.email1 LIKE '%:criteria%' OR "
                + "e.email2 LIKE '%:criteria%' "
                + "ORDER BY e.businessName")
    Page<Business> findBusinessBySearchCriteria(@Param("criteria") String criteria, Pageable pageRequest);
}
