package com.miroslav.menuinyourcity.request.Proms;

import com.google.api.client.util.Key;

/**
 * Created by apple on 4/16/16.
 */
public class PromsModel {
    @Key("id")
    private Long id;
    @Key("image")
    private String image;
    @Key("url")
    private String url;
    @Key("shop_id")
    private String shopId;
    @Key("city_id")
    private String cityId;
    @Key("created_at")
    private String createdAt;
    @Key("updated_at")
    private String updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PromsModel that = (PromsModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null)
            return false;
        return updateAt != null ? updateAt.equals(that.updateAt) : that.updateAt == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PromsModel{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                ", shopId='" + shopId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                '}';
    }
}
