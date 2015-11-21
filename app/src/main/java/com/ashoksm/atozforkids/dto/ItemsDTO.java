package com.ashoksm.atozforkids.dto;

public class ItemsDTO {

    private String itemName;

    private int imageResource;

    private int imageResource2;

    private int vibrantColor;

    public ItemsDTO(String itemName, int imageResource) {
        this.itemName = itemName;
        this.imageResource = imageResource;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getImageResource2() {
        return imageResource2;
    }

    public void setImageResource2(int imageResource2) {
        this.imageResource2 = imageResource2;
    }

    public int getVibrantColor() {
        return vibrantColor;
    }

    public void setVibrantColor(int vibrantColor) {
        this.vibrantColor = vibrantColor;
    }
}
