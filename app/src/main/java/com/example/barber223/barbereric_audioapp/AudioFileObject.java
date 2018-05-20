package com.example.barber223.barbereric_audioapp;

public class AudioFileObject {
    private String name;
    private String author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String filePath;

    AudioFileObject(String _name, String _author, String _filePath){
        name = _name;
        author = _author;
        filePath = _filePath;
    }
}
