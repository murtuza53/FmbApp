package org.fmb.controller.famb;

import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.fmb.controller.AbstractController;
import org.fmb.entity.famb.Menu;
import org.fmb.entity.famb.MenuCategory;
import org.fmb.repo.famb.MenuCategoryRepository;
import org.fmb.repo.famb.MenuRepository;
import org.fmb.util.FileUtils;
import org.fmb.util.JsfUtil;
import org.fmb.util.Utils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Named(value = "menuController")
@ViewScoped
public class MenuController extends AbstractController<Menu> {

    public static String destination = "/opt/fambphoto/";    //"D:\\Project\\fambphoto\\";
    private String lambOrChicken;

    private List<Menu> menuList;

    @Autowired
    private MenuCategoryRepository mcFacade;

    //@Autowired
    private MenuRepository menuFacade;

    @Autowired
    public MenuController(MenuRepository repo) {
        super(Menu.class, repo);
        this.menuFacade = repo;
        destination = FileUtils.getImagePath();
    }

    public List getMenuTypes() {
        //Map<String,String> menuTypes = new HashMap<String, String>();
        //for(String str: Menu.MENU_TYPES){
        //    menuTypes.put(str, str);
        //}
        return Arrays.asList(Menu.MENU_TYPES);
    }

    public List<MenuCategory> getMenuCategories() {
        return mcFacade.findAll();
    }

    public List<Menu> getMenuList() {
        if (menuList == null) {
            menuList = menuFacade.findMenuBySearchCriteria("");
        }
        return menuList;
    }

    public List<Menu> completeFilterNonMenu(String criteria) {
        //System.out.println("completeFilter: " + criteria);

        //return ejbFacade.findRange(0, 10, "firstName", "ASC",
        //        ejbFacade.createFilters(new String[]{"itsNo", "firstName"}, criteria));
        //return menuFacade.findRangeNonMenu(new int[]{0, 20}, criteria);
        return Utils.sublist(menuFacade.findMenuBySearchCriteria(""), 10);
    }

    /**
     * @return the lambOrChicken
     */
    public String getLambOrChicken() {
        if (getSelected() == null) {
            return null;
        }

        if (getSelected().getChickenMenu()) {
            lambOrChicken = "Chicken";
        } else if (getSelected().getLambMenu()) {
            lambOrChicken = "Lamb";
        } else {
            lambOrChicken = "Vegeterian";
        }

        return lambOrChicken;
    }

    /**
     * @param lambOrChicken the lambOrChicken to set
     */
    public void setLambOrChicken(String lambOrChicken) {
        this.lambOrChicken = lambOrChicken;
        if (lambOrChicken.equalsIgnoreCase("Chicken")) {
            getSelected().setChickenMenu(true);
            getSelected().setLambMenu(false);
            getSelected().setVegMenu(false);
        } else if (lambOrChicken.equalsIgnoreCase("Lamb")) {
            getSelected().setLambMenu(true);
            getSelected().setChickenMenu(false);
            getSelected().setVegMenu(false);
        } else {
            getSelected().setVegMenu(true);
            getSelected().setChickenMenu(false);
            getSelected().setLambMenu(false);
        }
    }

    public void onCellEditMenuCategory(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            //FacesContext.getCurrentInstance().addMessage(null, msg);
            Menu menu = (Menu) ((DataTable) event.getComponent()).getRowData();
            setSelected(menu);
            if(newValue!=null){
                menu.setMenuCategoryNo((MenuCategory)newValue);
                save();
                JsfUtil.addSuccessMessage("Menu category updated to " + newValue);
            } else {
                JsfUtil.addErrorMessage("Menu category is unchanged");
            }
            //ThaaliAllocation ta = entity.getItsMaster().getThaaliAllocation();
        }
    }

}
