package com.visionet.fengniao.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Insurance implements Parcelable {
    private String icon;
    private String name;
    private int minPrice;
    private int maxPrice;
    private String guarantee;//保障
    private String agreed;//约定

    private String imgTop;




    public Insurance() {
    }

    public Insurance(String icon, String name, int minPrice,int maxPrice, String guarantee, String agreed,String imgTop) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.icon = icon;
        this.guarantee = guarantee;
        this.agreed = agreed;
        this.imgTop = imgTop;
    }
    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }


    public String getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(String guarantee) {
        this.guarantee = guarantee;
    }

    public String getAgreed() {
        return agreed;
    }

    public void setAgreed(String agreed) {
        this.agreed = agreed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImgTop() {
        return imgTop;
    }

    public void setImgTop(String imgTop) {
        this.imgTop = imgTop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.name);
        dest.writeInt(this.minPrice);
        dest.writeInt(this.maxPrice);
        dest.writeString(this.guarantee);
        dest.writeString(this.agreed);
        dest.writeString(this.imgTop);
    }

    protected Insurance(Parcel in) {
        this.icon = in.readString();
        this.name = in.readString();
        this.minPrice = in.readInt();
        this.maxPrice = in.readInt();
        this.guarantee = in.readString();
        this.agreed = in.readString();
        this.imgTop = in.readString();
    }

    public static final Creator<Insurance> CREATOR = new Creator<Insurance>() {
        @Override
        public Insurance createFromParcel(Parcel source) {
            return new Insurance(source);
        }

        @Override
        public Insurance[] newArray(int size) {
            return new Insurance[size];
        }
    };
}
