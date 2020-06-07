package com.example.ecommerciandroiapp.Model;

public class AddressModel {
    private String name,address,phone;
    private boolean selected;

    public AddressModel(String name, String address, String phone,boolean selected) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
