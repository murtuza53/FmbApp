/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ejms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.ejms.entity.famb.Menu;

/**
 *
 * @author Burhani152
 */
public class FileUtils {

    static String destination;
    static String itsdestination;
    
    static {
        System.out.println("getImagePath -> OsValidator -> " + OSValidator.getOS());
        if (OSValidator.isWindows()) {
            destination = "D:\\Projects\\fambphoto\\";
            itsdestination = "D:\\Projects\\itsphoto\\";
        } else {
            destination = "/opt/fambphoto/";
            itsdestination = "/opt/itsphoto/";
        }
    }
    
    public static String getImagePath() {
        return destination;
    }

    public static String getItsImagePath(){
        return itsdestination;
    }
    
    public static File copyFile(String fileName, InputStream in) {
        try {

            File file = new File(fileName);
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
            return file;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean hasFileImage(int menuId) {
        String fileName = destination + "Menu_" + menuId + "_mob.jpg";

        File file = new File(fileName);
        return file.exists();
    }

    public static String[] getFileNamesForWriting(int menuId) {
        String[] fileNames = new String[]{destination + "Menu_" + menuId + ".jpg",
            destination + "Menu_" + menuId + "_mob.jpg",
            destination + "Menu_" + menuId + "_tb.jpg"};

        for (String name : fileNames) {
            System.out.println(name);
        }
        return fileNames;
    }

    public static String[] getFileNames(int menuId) {
        String[] fileNames = new String[]{destination + "Menu_" + menuId + ".jpg",
            destination + "Menu_" + menuId + "_mob.jpg",
            destination + "Menu_" + menuId + "_tb.jpg"};

        if (menuId == 0) {
            return getFileNamesNoThaali();
        }

        File file = new File(fileNames[1]);
        if (!file.exists()) {
            //System.out.println("COULD NOT FIND IMAGE FOR " + menuId);
            fileNames = getFileNamesNoImage();
        }
        //for (String name : fileNames) {
        //    System.out.println(name);
        //}
        return fileNames;
    }

    public static String[] getFileNames(Menu menu) {
        if (menu == null) {
            return getFileNamesNoImage();
        }
        return getFileNames(menu.getMenuNo());
    }

    public static String[] getFileNamesNoImage() {
        return new String[]{destination + "no_image.jpg",
            destination + "no_image_mob.jpg",
            destination + "no_image_tb.jpg"};
    }

    public static String[] getFileNamesNoThaali() {
        return new String[]{destination + "no_thaali.jpg",
            destination + "no_thaali_mob.jpg",
            destination + "no_thaali_tb.jpg"};
    }

    public static void deleteFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
