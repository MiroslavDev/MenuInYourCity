package com.miroslav.menuinyourcity.request.GetEvents;

import com.google.api.client.util.Key;

/**
 * Created by apple on 4/10/16.
 */
public class GetEventModel {

    @Key("id")
    private Long id;
    @Key("category_id")
    private String categoryId;
    @Key("shop_id")
    private String ShopId;
    @Key("title")
    private String title;
    @Key("description")
    private String description;
    @Key("image")
    private String imageUrl;
    @Key("date_start")
    private String dataStart;
    @Key("date_stop")
    private String dataStop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String shopId) {
        ShopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDataStart() {
        return dataStart;
    }

    public void setDataStart(String dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataStop() {
        return dataStop;
    }

    public void setDataStop(String dataStop) {
        this.dataStop = dataStop;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetEventModel that = (GetEventModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null)
            return false;
        if (ShopId != null ? !ShopId.equals(that.ShopId) : that.ShopId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        if (dataStart != null ? !dataStart.equals(that.dataStart) : that.dataStart != null)
            return false;
        return !(dataStop != null ? !dataStop.equals(that.dataStop) : that.dataStop != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (ShopId != null ? ShopId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (dataStart != null ? dataStart.hashCode() : 0);
        result = 31 * result + (dataStop != null ? dataStop.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GetEventModel{" +
                "id=" + id +
                ", categoryId='" + categoryId + '\'' +
                ", ShopId='" + ShopId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", dataStart='" + dataStart + '\'' +
                ", dataStop='" + dataStop + '\'' +
                '}';
    }
}
