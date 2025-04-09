package com.example.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("imdbID")
    private String imdbID;

    @SerializedName("Poster")
    private String poster;

    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getImdbID() { return imdbID; }
    public String getPoster() { return poster; }

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}
