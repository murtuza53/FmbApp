/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.repo.famb;

import org.ejms.entity.famb.ItsReaderInfo;
import org.ejms.repo.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Burhani152
 */
public interface ItsReaderInfoRepository extends BaseRepository<ItsReaderInfo, Integer>{

    @Query("SELECT i FROM ItsReaderInfo i WHERE i.hfid = :hfid")
    ItsReaderInfo findItsReaderInfoByHfId(@Param("hfid") String hfid);
    
    @Query("SELECT i FROM ItsReaderInfo i WHERE i.barcode = :barcode")
    ItsReaderInfo findItsReaderInfoByBarcode(@Param("barcode") String barcode);
    
}
