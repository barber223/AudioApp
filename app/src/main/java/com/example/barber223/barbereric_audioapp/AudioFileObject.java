package com.example.barber223.barbereric_audioapp;

public class AudioFileObject {
    private String name;
    private String author;
    private String notes;

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

    AudioFileObject(String _name, String _author, String _filePath, String _notes){
        name = _name;
        author = _author;
        filePath = _filePath;
        notes = _notes;
    }

    @Override
    public String toString() {
        return "Title: " + name + "\n    " + "Author: " + author;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
