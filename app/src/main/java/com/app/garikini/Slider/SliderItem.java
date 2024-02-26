package com.app.garikini.Slider;

/**
 * Rashtro created by Sayem Hossen Saimon on 3/7/2021 at 4:06 PM.
 * Email: saimonchowdhuryi96@gmail.com.
 * Phone: +8801882046404.
 **/
public class SliderItem {
    String description;
    String imageUrl;
    String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public SliderItem(String description, String imageUrl, String companyName, String price) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.companyName = companyName;
        this.price = price;
    }
    public SliderItem(String imageUrl) {
        this.description = "";
        this.imageUrl = imageUrl;
        this.companyName = "";
        this.price = "";
    }

    String price;

    public SliderItem(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
