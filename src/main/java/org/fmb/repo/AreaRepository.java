/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo;

import java.util.List;
import org.fmb.entity.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface AreaRepository extends BaseRepository<Area, Integer> {

    @Query("SELECT OBJECT(a) FROM Area a WHERE a.areaname LIKE %:criteria% ORDER BY a.areaname")
    List<Area> findAreaBySearchCriteria(@Param("criteria") String criteria);

}
