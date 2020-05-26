package com.example.ecommerciandroiapp.Model;

public class HorizontalBookModel {
    private String bookTitle,bookCategory,bookImage;
    private String bookPrice;

    public HorizontalBookModel(String bookImage, String bookTitle, String bookCategory, String bookPrice) {
        this.bookImage = bookImage;
        this.bookTitle = bookTitle;
        this.bookCategory = bookCategory;
        this.bookPrice = bookPrice;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitel) {
        this.bookTitle = bookTitel;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }
}
