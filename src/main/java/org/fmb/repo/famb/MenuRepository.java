/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo.famb;

import java.util.List;
import org.fmb.entity.famb.Menu;
import org.fmb.repo.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu, Integer>{
 
    @Query("SELECT OBJECT(m) FROM Menu m WHERE m.menuName LIKE %:criteria% ORDER BY m.menuName")
    List<Menu> findMenuBySearchCriteria(@Param("criteria") String criteria);

    @Query("SELECT OBJECT(m) FROM Menu m WHERE m.menuType=:menuType ORDER BY m.menuName")
    List<Menu> findMenuByMenuType(@Param("menuType") String menuType);

}
