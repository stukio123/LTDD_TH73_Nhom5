package com.example.ecommerciandroiapp.Model;

public class SliderModel {
    private String bannerImage;
    private String backgroundColor;

    public SliderModel(String banner, String backgroundColor) {
        this.bannerImage = banner;
        this.backgroundColor = backgroundColor;
    }

    public String getBanner() {
        return bannerImage;
    }

    public void setBanner(String banner) {
        this.bannerImage = banner;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
