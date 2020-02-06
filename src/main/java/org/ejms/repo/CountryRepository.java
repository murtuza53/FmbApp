/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.repo;

import java.util.List;
import org.ejms.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface CountryRepository extends BaseRepository<Country, Long>{
    
    @Query("SELECT OBJECT(c) FROM Country c WHERE c.countryName LIKE %:criteria% ORDER BY c.countryName")
    List<Country> findCountryBySearchCriteria(@Param("criteria") String criteria);

}
