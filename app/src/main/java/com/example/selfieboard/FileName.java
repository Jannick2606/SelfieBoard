package com.example.selfieboard;

/**
 *  This class contains the name and extension of the created files
 * @author Jannick
 */
public class FileName {
    private String name;
    private String extension;

    /**
     *
     * @param name
     * @param extension
     */
    public FileName(String name, String extension){
        this.name=name;
        this.extension=extension;
    }

    /**
     * This method is used to get the name of the files
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * This method is used to get the extension for the files
     * @return extension
     */
    public String getExtension() {
        return extension;
    }
}
