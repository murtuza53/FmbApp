/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.report;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.ejms.util.Utils;

/**
 *
 * @author Burhani152
 */
public class NativeQueryTableModel implements Serializable {

    private List<NativeQueryReportObject> data;
    private LinkedList<ColumnModel> columns = new LinkedList<ColumnModel>();

    public NativeQueryTableModel(){
        
    }
    
    public NativeQueryTableModel(String[] property) {
        this(property, property);
    }

    public NativeQueryTableModel(String[] headers, String[] property) {
        addColumns(headers, property);
    }

    public NativeQueryTableModel(String[] properties, Class[] types, String[] bg){
        addColumns(properties, properties, types, bg);
    }
    
    public NativeQueryTableModel addColumn(String header, String property) {
        if (getColumns() == null) {
            columns = new LinkedList<ColumnModel>();
        }
        getColumns().add(new ColumnModel(Utils.capitalize(header), property, columns.size()));
        return this;
    }

    public NativeQueryTableModel addColumn(String header, String property, int index) {
        if (getColumns() == null) {
            columns = new LinkedList<ColumnModel>();
        }
        getColumns().add(new ColumnModel(Utils.capitalize(header), property, columns.size()));
        return this;
    }
    
    public NativeQueryTableModel addColumns(String[] properties){
        return addColumns(properties, properties);
    }

    public NativeQueryTableModel addColumns(String[] properties, Class[] types, String[] bg){
        return addColumns(properties, properties, types, bg);
    }
    
    public NativeQueryTableModel addColumns(String[] headers, String[] properties) {
        if (headers == null) {
            return this;
        }
        if (properties == null) {
            return this;
        }
        if (headers.length != properties.length) {
            return this;
        }

        for (int i = 0; i < headers.length; i++) {
            addColumn(headers[i], properties[i], i);
        }
        return this;
    }
    
    public NativeQueryTableModel addColumns(String[] headers, String[] properties, Class[] types, String[] bg){
        if (headers == null) {
            return this;
        }
        if (properties == null) {
            return this;
        }
        if (headers.length != properties.length) {
            return this;
        }
        
        for (int i = 0; i < headers.length; i++) {
            addColumn(headers[i], properties[i], i);
            ColumnModel cm = columns.getLast();
            System.out.println(cm.getHeader() + " => " + types[i]);
            cm.setType(types[i]);
            //cm.getColumnStyle().setBackgroundColor(bg[i]);
        }
        return this;
    }
    
    public void resetModel(){
        data = null;
        columns = new LinkedList<ColumnModel>();
    }

    public Object getValueAt(int row, int col) {
        return getData().get(col);
    }

    public Object getValue(NativeQueryReportObject bean, int col) {
        //return bean.getField(col);
        //return Utils.formatValue(columns.get(col).getType(), bean.getField(col));
        Object data = bean.getField(col);
        //System.out.println(data + " => " + col + " => " + columns.get(col).getColumnStyle().getFormat() + " => " + columns.get(col).getType());
        if(columns.get(col).getColumnStyle().getFormat()==null){
            return data;
        }
        return columns.get(col).getColumnStyle().getFormat().format(data);
    }
    
    public ColumnStyle getColumnStyle(int col){
        return columns.get(col).getColumnStyle();
    }
    
    /**
     * @return the data
     */
    public List<NativeQueryReportObject> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<NativeQueryReportObject> data) {
        this.data = data;
    }

    /**
     * @return the columns
     */
    public List<ColumnModel> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<ColumnModel> columns) {
        this.columns = new LinkedList(columns);
    }

}
