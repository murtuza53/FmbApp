package org.ejms.controller.famb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import javax.swing.ImageIcon;
import net.coobird.thumbnailator.Thumbnails;
import org.ejms.controller.AbstractController;
import org.ejms.entity.famb.Menu;
import org.ejms.entity.famb.MenuCategory;
import org.ejms.repo.famb.MenuCategoryRepository;
import org.ejms.repo.famb.MenuRepository;
import org.ejms.util.FileUtils;
import org.ejms.util.JsfUtil;
import org.ejms.util.Utils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;

@Named(value = "menuController")
@ViewScoped
public class MenuController extends AbstractController<Menu> {

    private StreamedContent image;
    //private Part file;
    public static String destination = "/opt/fambphoto/";    //"D:\\Project\\fambphoto\\";
    private String lambOrChicken;

    private int imageWidth = 80;
    private int imageHeight = 80;

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

    public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            String[] fileNames = FileUtils.getFileNamesForWriting(getSelected().getMenuNo());
            FileUtils.deleteFiles(fileNames);
            File file = FileUtils.copyFile(fileNames[0], event.getFile().getInputstream());
            if (file != null) {
                //FileInputStream fis = new FileInputStream(file);
                ImageIcon img = new ImageIcon(file.getAbsolutePath());
                System.out.println(fileNames[0] + ": " + img.getIconWidth() + " x " + img.getIconHeight());
                int width = 300;
                int height = 300 * img.getIconHeight() / img.getIconWidth();
                Thumbnails.of(file.getAbsoluteFile()).size(width, height).toFile(fileNames[1]);
                System.out.println(fileNames[1] + ": " + width + " x " + height);
                setImageWidth(width);
                setImageHeight(height);

                width = 80;
                height = 80 * img.getIconHeight() / img.getIconWidth();
                Thumbnails.of(file.getAbsoluteFile()).size(width, height).toFile(fileNames[2]);
                System.out.println(fileNames[2] + ": " + width + " x " + height);

                setImage(new DefaultStreamedContent(new FileInputStream(fileNames[1]), "image/jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return the image
     */
    public StreamedContent getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(StreamedContent image) {
        this.image = image;
    }

    public void validateFile(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > 51200) {
            msgs.add(new FacesMessage("File Size must be less than 50Kb"));
        }
        System.out.println("CONTENT-TYPE: " + file.getContentType());
        if (!file.getContentType().contains("image")) {
            msgs.add(new FacesMessage("Not an image file"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
            }
        }
        return null;
    }

    /**
     * @return the imageWidth
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * @param imageWidth the imageWidth to set
     */
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * @return the imageHeight
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * @param imageHeight the imageHeight to set
     */
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
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
