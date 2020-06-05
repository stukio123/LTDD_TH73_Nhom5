package com.example.ecommerciandroiapp.Model;

public class MyOrderModel {
    private int bookimage;
    private String bookTitle;
    private String deliveryStatus;
    private int rating;
    private String deliveryDate;

    public MyOrderModel(int bookimage,int rating, String bookTitle, String deliveryStatus) {
        this.bookimage = bookimage;
        this.bookTitle = bookTitle;
        this.deliveryStatus = deliveryStatus;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getBookimage() {
        return bookimage;
    }

    public void setBookimage(int bookimage) {
        this.bookimage = bookimage;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
