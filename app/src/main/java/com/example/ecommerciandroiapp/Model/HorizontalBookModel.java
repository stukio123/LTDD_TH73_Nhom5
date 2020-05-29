package com.example.ecommerciandroiapp.Model;

public class HorizontalBookModel {
    private String bookTitel,bookCategory;
    private int bookPrice,bookImage;

    public HorizontalBookModel(int bookImage, String bookTitel, String bookCategory, int bookPrice) {
        this.bookImage = bookImage;
        this.bookTitel = bookTitel;
        this.bookCategory = bookCategory;
        this.bookPrice = bookPrice;
    }

    public int getBookImage() {
        return bookImage;
    }

    public void setBookImage(int bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookTitel() {
        return bookTitel;
    }

    public void setBookTitel(String bookTitel) {
        this.bookTitel = bookTitel;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }
}
