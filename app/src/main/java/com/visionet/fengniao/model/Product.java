package com.visionet.fengniao.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private String spec;
    private String imageTop;
    private String imageCenter;
    private String imageBottom;
    private String icon;
    private int price;
    public Product(){

    }
    public Product(String name,String spec,String icon,int price,String imageTop,String imageCenter ,String imageBottom){
        this.name = name;
        this.spec = spec;
        this.icon = icon;
        this.price = price;
        this.icon = icon;
        this.imageBottom = imageBottom;
        this.imageCenter = imageCenter;
        this.imageTop= imageTop;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getImageTop() {
        return imageTop;
    }

    public void setImageTop(String imageTop) {
        this.imageTop = imageTop;
    }

    public String getImageCenter() {
        return imageCenter;
    }

    public void setImageCenter(String imageCenter) {
        this.imageCenter = imageCenter;
    }

    public String getImageBottom() {
        return imageBottom;
    }

    public void setImageBottom(String imageBottom) {
        this.imageBottom = imageBottom;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.spec);
        dest.writeString(this.imageTop);
        dest.writeString(this.imageCenter);
        dest.writeString(this.imageBottom);
        dest.writeString(this.icon);
        dest.writeInt(this.price);
    }

    protected Product(Parcel in) {
        this.name = in.readString();
        this.spec = in.readString();
        this.imageTop = in.readString();
        this.imageCenter = in.readString();
        this.imageBottom = in.readString();
        this.icon = in.readString();
        this.price = in.readInt();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
