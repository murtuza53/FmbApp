package org.fmb.controller;

import java.util.LinkedList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.fmb.entity.Area;
import org.fmb.repo.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

@Named(value = "areaController")
@ViewScoped
public class AreaController extends AbstractController<Area> {

    private AreaRepository facade;

    private List<String> nameList;
    
    @Autowired
    public AreaController(AreaRepository repo) {
        // Inform the Abstract parent controller of the concrete Ledger Entity
        super(Area.class, repo);
        this.facade = repo;
        super.setSortFields(Sort.by(Sort.Direction.ASC, "areaname"));
    }
    
    public List<Area> findAll(){
        return facade.findAreaBySearchCriteria("");
    }

    public List<String> getAreaAll(){
        if(nameList==null){
            nameList = new LinkedList();
            for(Area a: findAll()){
                nameList.add(a.getAreaname());
            }
        }
        return nameList;
    }
    
}
