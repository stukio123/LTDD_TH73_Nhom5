package com.example.ecommerciandroiapp.Model;



public class WishListModel {
    private int bookImage;
    private String bookTitle,bookAuthor;
    private String rating;
    private int totalRating;
    private String bookPrice;
    private String cuttedPrice;

    public WishListModel(int bookImage, String bookTitle, String bookAuthor, String rating, int totalRating, String bookPrice, String cuttedPrice) {
        this.bookImage = bookImage;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.rating = rating;
        this.totalRating = totalRating;
        this.bookPrice = bookPrice;
        this.cuttedPrice = cuttedPrice;
    }

    public int getBookImage() {
        return bookImage;
    }

    public void setBookImage(int bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }
}
