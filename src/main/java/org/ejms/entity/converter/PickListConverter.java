/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.entity.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.ejms.entity.famb.Menu;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Inseeyah
 */
@FacesConverter("PickListConverter")
public class PickListConverter implements Converter {

    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        Object ret = null;
        if (arg1 instanceof PickList) {
            Object dualList = ((PickList) arg1).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Object o : dl.getSource()) {
                String id = "" + ((Menu) o).getMenuNo();
                if (arg2.equals(id)) {
                    ret = o;
                    break;
                }
            }
            if (ret == null) {
                for (Object o : dl.getTarget()) {
                    String id = "" + ((Menu) o).getMenuNo();
                    if (arg2.equals(id)) {
                        ret = o;
                        break;
                    }
                }
            }
        }
        //System.out.println("PickListConverter:getAsObject: " + ret);
        return ret;

    }

    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        String str = "";
        //System.out.println("PickListConverter:getAsString: " + arg2);
        if (arg2 instanceof Menu) {
            str = "" + ((Menu) arg2).getMenuNo();
        }
        return str;
    }
}
