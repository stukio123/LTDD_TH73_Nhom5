package com.example.ecommerciandroiapp.Model;

public class RewardModel {
    private String tile;
    private String date;
    private String descript;

    public RewardModel(String tile, String date, String descript) {
        this.tile = tile;
        this.date = date;
        this.descript = descript;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
