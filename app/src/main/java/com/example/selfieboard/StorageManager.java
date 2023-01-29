package com.example.selfieboard;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class StorageManager {
    MainActivity ma;
    File file;
    FileName fileName;
    private ArrayList<FileName> fileNames = new ArrayList<FileName>();

    public StorageManager(MainActivity ma) {
        this.ma = ma;
    }

    /**
     * @param bitmap
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
     * @param name
     */
    public void deleteFromExternalStorage(String name) {
        file = new File(ma.getExternalFilesDir(Environment.DIRECTORY_PICTURES), name);

        if (file.exists()) {
            file.delete();
        }
    }
}
