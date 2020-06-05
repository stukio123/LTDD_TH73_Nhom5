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
    private int bookImage;
    private String bookTitle;
    private String bookPrice;
    private String bookPublisher;
    private String cuttedPrice;
    private int bookQuantity;

    public CartItemModel(int type, int bookImage, String bookTitle, String bookPrice, String bookPublisher, String cuttedPrice, int bookQuantity) {
        this.type = type;
        this.bookImage = bookImage;
        this.bookTitle = bookTitle;
        this.bookPrice = bookPrice;
        this.bookPublisher = bookPublisher;
        this.cuttedPrice = cuttedPrice;
        this.bookQuantity = bookQuantity;
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

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
    //Cart item

    //Cart total
    private String totalItems;
    private String totalItemsPrice;
    private String deliveryPrices;
    private String savedAmount;
    private String totalAmount;

    public CartItemModel(int type,String totalItems, String totalItemsPrice, String deliveryPrices, String savedAmount, String totalAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemsPrice = totalItemsPrice;
        this.deliveryPrices = deliveryPrices;
        this.savedAmount = savedAmount;
        this.totalAmount = totalAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(String totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public String getDeliveryPrices() {
        return deliveryPrices;
    }

    public void setDeliveryPrices(String deliveryPrices) {
        this.deliveryPrices = deliveryPrices;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    //Cart total
}
