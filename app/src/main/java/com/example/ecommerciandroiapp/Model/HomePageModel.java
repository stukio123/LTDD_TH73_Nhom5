package com.example.ecommerciandroiapp.Model;

import java.util.List;

public class HomePageModel {

    public static final int BANNER_SLIDER = 0;
    public static final int HORIZONTAL_BOOK_VIEW = 1;
    public static final int GRID_BOOK_VIEW = 2;
    public static final int CATEGORIES_BOOK_VIEW = 3;
    public static final int HORIZONTAL_AUTHOR_VIEW = 3;
    private int type;

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    //Khởi tạo category
    /*private List<CategoryModel> categoryModelList;

    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    public List<CategoryModel> getCategoryModelList(){return categoryModelList;}*/
    //Kết thúc khởi tạo category

    //Khởi Tạo Banner
    private List<SliderModel> sliderModelList;
    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //Kết thúc khởi tạo banner

    //Khởi tạo Horizontal view và GridView Layout
    private String bookTitle;
    private List<HorizontalBookModel> horizontalBookModelList;
    private List<WishListModel> viewAllList;
    public HomePageModel(int type, String bookTitle, List<HorizontalBookModel> horizontalBookModelList,List<WishListModel> viewAllList) {
        this.type = type;
        this.bookTitle = bookTitle;
        this.horizontalBookModelList = horizontalBookModelList;
        this.viewAllList = viewAllList;
    }

    public HomePageModel(int type, String bookTitle, List<HorizontalBookModel> horizontalBookModelList) {
        this.type = type;
        this.bookTitle = bookTitle;
        this.horizontalBookModelList = horizontalBookModelList;
    }
    public List<WishListModel> getViewAllList() {
        return viewAllList;
    }
    public void setViewAllList(List<WishListModel> viewAllList) {
        this.viewAllList = viewAllList;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    public List<HorizontalBookModel> getHorizontalBookModelList() {
        return horizontalBookModelList;
    }
    public void setHorizontalBookModelList(List<HorizontalBookModel> horizontalBookModelList) {
        this.horizontalBookModelList = horizontalBookModelList;
    }
    //Kết thúc khởi tạo Horizontal view

    //GridLayout

    //GridLayout

}
