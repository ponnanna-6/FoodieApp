package com.foodie.foodiev1.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeVerModel implements Serializable {
    String image;
    String name;
    String ratings;
    String priceRange;
    ArrayList<String> menuImages;

    public ArrayList<String> getMenuImages() {
        return menuImages;
    }

    public void setMenuImages(ArrayList<String> menuImages) {
        this.menuImages = menuImages;
    }



    public HomeVerModel(String image, String name, String ratings, String priceRange, ArrayList<String> menuImages) {
        this.image = image;
        this.name = name;
        this.ratings = ratings;
        this.priceRange = priceRange;
        this.menuImages=menuImages;
    }

    public HomeVerModel() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

}
