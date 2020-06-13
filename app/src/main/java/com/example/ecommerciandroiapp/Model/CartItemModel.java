package com.example.ecommerciandroiapp.Model;

public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //Cart item
    private String bookID;
    private String bookImage;
    private String bookTitle;
    private String bookPrice;
    private String bookPublisher;
    private String cuttedPrice;
    private long bookQuantity;

    public CartItemModel(int type ,String bookID, String bookImage, String bookTitle, String bookPrice, String bookPublisher, String cuttedPrice, long bookQuantity) {
        this.type = type;
        this.bookID = bookID;
        this.bookImage = bookImage;
        this.bookTitle = bookTitle;
        this.bookPrice = bookPrice;
        this.bookPublisher = bookPublisher;
        this.cuttedPrice = cuttedPrice;
        this.bookQuantity = bookQuantity;
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

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public long getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(long bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
    //Cart item

    //Cart total

    public CartItemModel(int type) {
        this.type = type;
    }

    //Cart total
}
