package com.example.ecommerciandroiapp.Model;

public class NotificationModel {
    private String content;
    private String image;
    private String chitiet;

    public NotificationModel(String content, String image,String chitiet) {
        this.content = content;
        this.chitiet = chitiet;
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
