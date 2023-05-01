package com.foodie.foodiev1.models;

public class HomeHorModel {
    int image;
    String title;


    public HomeHorModel(int image, String title) {
        this.image = image;
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
