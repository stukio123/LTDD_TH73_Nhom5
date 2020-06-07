package com.example.ecommerciandroiapp.Model;

public class HorizontalBookModel {
    private String bookTitle,bookCategory,bookImage,bookID;
    private String bookPrice;

    public HorizontalBookModel(String bookID,String bookImage ,String bookTitle, String bookCategory,  String bookPrice) {
        this.bookTitle = bookTitle;
        this.bookCategory = bookCategory;
        this.bookImage = bookImage;
        this.bookPrice = bookPrice;
        this.bookID = bookID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
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
