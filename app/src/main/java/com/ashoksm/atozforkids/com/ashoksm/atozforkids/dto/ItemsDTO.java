package com.ashoksm.atozforkids.com.ashoksm.atozforkids.dto;

public class ItemsDTO {

    private String itemName;

    private int imageResource;

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
}
