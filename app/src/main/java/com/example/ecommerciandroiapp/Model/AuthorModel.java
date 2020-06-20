package com.example.ecommerciandroiapp.Model;

public class AuthorModel {
    private String authorName;
    private String authorImage;

    public AuthorModel() {
    }

    public AuthorModel(String authorName, String authorImage) {
        this.authorName = authorName;
        this.authorImage = authorImage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }
}
