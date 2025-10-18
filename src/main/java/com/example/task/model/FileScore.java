package com.example.task.model;


public class FileScore {
    private String fileName;
    private double score;

    public FileScore(String fileName, double score) {
        this.fileName = fileName;
        this.score = score;
    }

    public String getFileName() { return fileName; }
    public double getScore() { return score; }
}
