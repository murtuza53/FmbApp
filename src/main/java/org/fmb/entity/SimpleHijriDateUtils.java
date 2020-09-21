/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fmb.entity;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author FS
 */
public class SimpleHijriDateUtils {

    public static String[] HIJRI_MONTHS = new String[]{"Moharram-ul-Haram", "Safar-ul-Muzaffar", "Rabi-ul-Awwal",
        "Rabi-ul-Aakhar", "Jumadil-Ula", "Jumadil-Ukhra",
        "Rajab-ul-Asab", "Shaban-al-Karim", "Ramadan-al-Moazzam",
        "Shawwal-al-Mukarram", "Zilqadatil-Haram", "Zilhajjatil-Haram"};

    public static List<String> HIJRI_MONTHS_LIST = Arrays.asList(SimpleHijriDateUtils.HIJRI_MONTHS);

    public static String spellMonth(int month) {
        return HIJRI_MONTHS[month];
    }

    public static String[] getHijriMonthName() {
        return HIJRI_MONTHS;
    }
}
