package com.example.android.mybooklistingapp;

/**
 * Created by guido on 11/07/2017.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ItemLoader extends AsyncTaskLoader<List<Book>> {

    private String query_url;

    //constructor
    public ItemLoader(Context context, String url) {
        super(context);
        query_url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //background thread
    @Override
    public List<Book> loadInBackground() {
        if (query_url == null) {
            return null;
        }
        List<Book> books = QueryUtils.getBookResponse(query_url);
        return books;
    }
}