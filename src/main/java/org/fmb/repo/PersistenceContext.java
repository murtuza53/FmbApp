/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.repo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Burhani152
 */
 
@Configuration
@EnableJpaRepositories(basePackages = {"org.ejms.repo"},
        repositoryBaseClass = BaseRepositoryImpl.class
)
@EnableTransactionManagement
public class PersistenceContext {
    
}
