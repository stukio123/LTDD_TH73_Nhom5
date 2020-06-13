package com.example.ecommerciandroiapp.Model;



public class WishListModel {
    private String bookID;
    private String bookImage;
    private String bookTitle,bookAuthor;
    private long rating;
    private long totalRating;
    private String bookPrice;
    private String cuttedPrice;

    public WishListModel(String bookID,String bookImage, String bookTitle, String bookAuthor, long rating, long totalRating, String bookPrice, String cuttedPrice) {
        this.bookID = bookID;
        this.bookImage = bookImage;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.rating = rating;
        this.totalRating = totalRating;
        this.bookPrice = bookPrice;
        this.cuttedPrice = cuttedPrice;
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

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(long totalRating) {
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
