package com.example.android.books;



public class Word {
    private String mpublisher;
    private String mtitle;
    private String mauthor;
    private String murl;
    public Word(String publisher,String title,String author,String url)
    {
        mpublisher=publisher;
        mtitle=title;
        mauthor=author;
        murl=url;
    }

    public String getMtitle() {
        return mtitle;
    }

    public String getMauthor() {
        return mauthor;
    }

    public String getMpublisher() {
        return mpublisher;
    }

    public String getMurl() {
        return murl;
    }
}
