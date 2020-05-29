package com.example.ecommerciandroiapp.Model;

public class BookModel {
    private String author, categoryID,description,title,imageURL;
    private int isbn,price,sku;
    public BookModel(){}

    public BookModel(String author, String categoryID, String description, String title, String imageURL, int isbn, int price, int sku) {
        this.author = author;
        this.categoryID = categoryID;
        this.description = description;
        this.title = title;
        this.imageURL = imageURL;
        this.isbn = isbn;
        this.price = price;
        this.sku = sku;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
}
