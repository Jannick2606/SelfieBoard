package com.example.selfieboard;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 *  This class is used to interact with the external storage
 * @author Jannick
 */
public class StorageManager {
    MainActivity ma;
    File file;
    FileName fileName;
    private ArrayList<FileName> fileNames = new ArrayList<FileName>();

    /**
     * This is the constructor for the class
     * It takes MainActivity as a parameter so it can be used to call
     * the getExternalFilesDir method
     * @param ma
     */
    public StorageManager(MainActivity ma) {
        this.ma = ma;
    }

    /**
     * Method that saves image to external storage
     * @param bitmap
     * @result Saves image to external storage
     */
    public void saveToExternalStorage(Bitmap bitmap) {
        fileName = new FileName(UUID.randomUUID().toString(), ".jpg");
        fileNames.add(fileName);
        file = new File(ma.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName.getName()+fileName.getExtension());
        if (file.exists()) {
            file.delete();
        }
        new Thread(() -> {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Method that deletes from external storage
     * @result Deletes images from the external storage
     */
    public void deleteFromExternalStorage() {

        for (FileName fileName: fileNames) {
            file = new File(ma.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName.getName()+fileName.getExtension());
            if(file.exists()){
                file.delete();
            }
        }
    }
}
