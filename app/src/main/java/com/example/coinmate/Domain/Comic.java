package com.example.coinmate.Domain;

import android.graphics.Bitmap;

public class Comic {
    private String id;
    private String title;
    private String description;
    private String img;

    public Comic(String id, String title, String description, String img) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img.toString();
    }
}
