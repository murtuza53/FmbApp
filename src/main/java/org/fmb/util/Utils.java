/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.util;

import com.hijri.HijriDate;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Burhani152
 */
public class Utils {

    public static Integer[] HIJRI_YEARS;
    public static Integer[] YEARS;
    public static String[] MONTH_NAMES = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    static {
        int hyear_start = 1435;
        int year_start = 2014;

        int hyear_end = HijriDate.getInstance(new Date()).getYear() + 2;
        int year_end = DateUtil.getYear(new Date()) + 2;

        HIJRI_YEARS = new Integer[hyear_end - hyear_start];
        YEARS = new Integer[year_end - year_start];

        for (int i = 0; i < HIJRI_YEARS.length; i++) {
            HIJRI_YEARS[i] = hyear_start + i;
        }

        for (int i = 0; i < YEARS.length; i++) {
            YEARS[i] = year_start + i;
        }
    }

    ;

    public String[] month_names_asarrays(Date date) {
        return (String[]) month_names(date).toArray();
    }

    public List<String> month_names(Date date) {
        int month = DateUtil.getMonth(date);
        return new ArrayList<String>(DateUtil.getMonthArrayShortName()).subList(0, month);
    }

    public static List sublist(List list, int limit) {
        if (list == null) {
            return null;
        }
        if (list.size() > limit) {
            return list.subList(0, limit);
        }
        return list;
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Format getFormatter(Class type){
        if(type==null){
            return null;
        }
        
        if (type.getName().equals(double.class.getName())  || type.getName().equals(Double.class.getName())) {
            return SystemConfig.DECIMAL_FORMAT;
        } else if (type.getName().equals(float.class.getName()) || type.getName().equals(Float.class.getName())) {
            return SystemConfig.DECIMAL_FORMAT;
        } else if (type.getName().equals(Date.class.getName())) {
            return SystemConfig.DATE_FORMAT;
        }
        return null;
    }
    
    public static String formatValue(Class type, Object value){
        if(value==null){
            return null;
        }
        Format format = getFormatter(type);
        if(format!=null){
            return format.format(value);
        }
        return value.toString();
    }
    
    public static String getCssStyle(Class type){
        if(type == null){
            return "inherit";
        }
        if (type.getName().equals(double.class.getName())  || type.getName().equals(Double.class.getName()) 
                || type.getName().equals(float.class.getName())  || type.getName().equals(Float.class.getName())) {
            return "right";
        } else if (type.getName().equals(Date.class.getName())  || type.getName().equals(int.class.getName())
                || type.getName().equals(Integer.class.getName())) {
            return "center";
        }
        return "inherit";
    }
}
