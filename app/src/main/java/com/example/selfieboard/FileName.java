package com.example.selfieboard;

public class FileName {
    private String name;
    private String extension;

    public FileName(String name, String extension){
        this.name=name;
        this.extension=extension;
    }
    public String getName(){
        return name;
    }
    public String getExtension() {
        return extension;
    }
}
