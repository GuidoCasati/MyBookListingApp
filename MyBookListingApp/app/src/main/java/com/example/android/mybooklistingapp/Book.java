package com.example.android.mybooklistingapp;

/**
 * Created by guido on 11/07/2017.
 */

import java.util.ArrayList;

public class Book {

    private String cover;
    private String title;
    private ArrayList<String> authors;
    private String url;


    //constructor
    public Book(String cover, String title, ArrayList<String> author, String url) {
        this.cover = cover;
        this.title = title;
        this.authors = author;
        this.url = url;
    }

    //getters
    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        String authorsString = getAuthorsString();
        return authorsString;
    }

    public String getAuthorsString() {
        String authors = this.authors.get(0);
        if (this.authors.size() > 1) {
            for (int i = 1; i < this.authors.size(); i++) {
                authors += "\n" + this.authors.get(i);
            }
        }
        return authors;
    }

    public String getURL() {
        return url;
    }
}


