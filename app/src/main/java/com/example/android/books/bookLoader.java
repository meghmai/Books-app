package com.example.android.books;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class bookLoader extends AsyncTaskLoader<List<Word>> {
    private static final String LOG_TAG = bookLoader.class.getName();
    private String mUrl;

    public bookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Word> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Word> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
