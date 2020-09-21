package org.fmb.controller;

import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.fmb.entity.Business;
import org.fmb.entity.ItsMaster;
import org.fmb.repo.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Named(value = "businessController")
@ViewScoped
public class BusinessController extends AbstractController<Business> {

    //@Autowired
    private BusinessRepository busFacade;

    @Autowired
    public BusinessController(BusinessRepository repo) {
        // Inform the Abstract parent controller of the concrete Business Entity
        super(Business.class, repo);
        this.busFacade = repo;
    }

    public List<Business> completeFilter(String criteria) {
        System.out.println("completeFilter: " + criteria);

        //return ejbFacade.findRange(0, 10, "firstName", "ASC",
        //        ejbFacade.createFilters(new String[]{"itsNo", "firstName"}, criteria));
        return sublist(busFacade.findBusinessBySearchCriteria(criteria), 10);
    }
    
    public List<Business> getBusinesses(){
        return busFacade.findAll();
    }

        public List<Business> sublist(List<Business> list, int limit){
        if(list==null){
            return null;
        }
        if(list.size()>limit){
            return list.subList(0, limit);
        }
        return list;
    }
}
