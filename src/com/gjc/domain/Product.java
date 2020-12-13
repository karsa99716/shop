package com.gjc.domain;

import java.util.Date;

public class Product {
    private int productid;
    private String name;
    private float price;
    private float markprice;
    private int hit;
    private int quality;
    private Date time;
    private String photo;
    private String content;
    private int categoryid;

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMarkprice() {
        return markprice;
    }

    public void setMarkprice(float markprice) {
        this.markprice = markprice;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productid=" + productid +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", markprice=" + markprice +
                ", hit=" + hit +
                ", quality=" + quality +
                ", time=" + time +
                ", photo='" + photo + '\'' +
                ", content='" + content + '\'' +
                ", categoryid=" + categoryid +
                '}';
    }

}
