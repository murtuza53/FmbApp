/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo.famb;

import java.util.List;
import org.fmb.entity.famb.Menu;
import org.fmb.entity.famb.MenuCategory;
import org.fmb.repo.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Burhani152
 */
@Repository
public interface MenuCategoryRepository extends BaseRepository<MenuCategory, Integer>{
 
    @Query("SELECT OBJECT(m) FROM MenuCategory m WHERE m.categoryName LIKE %:criteria% ORDER BY m.categoryName")
    List<Menu> findMenuCategoryBySearchCriteria(@Param("criteria") String criteria);
}
