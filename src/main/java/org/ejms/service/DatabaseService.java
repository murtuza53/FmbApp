/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Burhani152
 */
@Service
public class DatabaseService {

    @PersistenceContext
    EntityManager em;
    
    @Transactional
    public List<T> executeNativeQuery(String query, Class T){
        return em.createNativeQuery(query, T).getResultList();
    }
    
    @Transactional
    public List<Object> executeCountNativeQuery(String query){
        return em.createNativeQuery(query).getResultList();
    }    
    
    @Transactional
    public List<Object[]> executeNativeQuery(String query){
        return em.createNativeQuery(query).getResultList();
    }

    @Transactional
    public List<Tuple> executeNativeQueryTuple(String query){
        return em.createNativeQuery(query).getResultList();
    }
    
}
