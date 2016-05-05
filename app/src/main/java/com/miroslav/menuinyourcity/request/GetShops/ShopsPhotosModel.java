package com.miroslav.menuinyourcity.request.GetShops;

import com.google.api.client.util.Key;

/**
 * Created by apple on 4/10/16.
 */
public class ShopsPhotosModel {
    @Key("id")
    private Long id;
    @Key("shop_id")
    private String shopId;
    @Key("image")
    private String image;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopsPhotosModel that = (ShopsPhotosModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        return !(image != null ? !image.equals(that.image) : that.image != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopsPhotosModel{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
