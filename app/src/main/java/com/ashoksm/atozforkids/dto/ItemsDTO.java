package com.ashoksm.atozforkids.dto;

import android.content.res.ColorStateList;

public class ItemsDTO implements Cloneable {

    private String itemName;
    private String itemNumber;
    private int imageResource;
    private int imageResource2;
    private int vibrantColor;
    private boolean showBack;
    private ColorStateList colorStateList;
    private int audioResource;

    public ItemsDTO(String itemName) {
        this.itemName = itemName;
    }

    public ItemsDTO(String itemName, int imageResource) {
        this.itemName = itemName;
        this.imageResource = imageResource;
    }

    public ItemsDTO(String itemName, int imageResource, int audioResource) {
        this.itemName = itemName;
        this.imageResource = imageResource;
        this.audioResource = audioResource;
    }

    public ItemsDTO(String itemName, String itemNumber) {
        this.itemName = itemName;
        this.itemNumber = itemNumber;
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

    public boolean isShowBack() {
        return showBack;
    }

    public void setShowBack(boolean showBack) {
        this.showBack = showBack;
    }

    public ColorStateList getColorStateList() {
        return colorStateList;
    }

    public void setColorStateList(ColorStateList colorStateList) {
        this.colorStateList = colorStateList;
    }

    public int getAudioResource() {
        return audioResource;
    }

    public void setAudioResource(int audioResource) {
        this.audioResource = audioResource;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }
}
