package org.fmb.controller;

import java.util.LinkedList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.fmb.entity.Country;
import org.fmb.repo.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

@Named(value = "countryController")
@ViewScoped
public class CountryController extends AbstractController<Country> {

    private CountryRepository facade;
    
    private List<String> nameList;
    
    @Autowired
    public CountryController(CountryRepository repo) {
        // Inform the Abstract parent controller of the concrete Ledger Entity
        super(Country.class, repo);
        this.facade = repo;
        super.setSortFields(Sort.by(Sort.Direction.ASC, "countryName"));
    }
    
    public List<Country> findAll(){
        return facade.findCountryBySearchCriteria("");
    }

    public List<String> getCountryNameAll(){
        if(nameList==null){
            nameList = new LinkedList();
            for(Country c: findAll()){
                nameList.add(c.getCountryName());
            }
        }
        return nameList;
    }
    
}
