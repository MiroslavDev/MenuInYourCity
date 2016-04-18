package com.miroslav.menuinyourcity.request.GetShops;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class GetShopsModel {
    @Key("id")
    private Long id;
    @Key("category_id")
    private String categoryId;
    @Key("city_id")
    private String cityId;
    @Key("title")
    private String title;
    @Key("description")
    private String description;
    @Key("time")
    private String time;
    @Key("lat")
    private String latitude;
    @Key("lon")
    private String longitude;
    @Key("street")
    private String street;
    @Key("phone")
    private String phone;
    @Key("date_start")
    private String dataStart;
    @Key("date_stop")
    private String dataStop;
    @Key("created_at")
    private String createdData;
    @Key("updated_at")
    private String updatedData;
    @Key("rating")
    private Double rating;
    @Key("photos")
    private List<ShopsPhotosModel> photos;
    @Key("reviews")
    private List<ShopsReviewsModel> reviews;

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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getCreatedData() {
        return createdData;
    }

    public void setCreatedData(String createdData) {
        this.createdData = createdData;
    }

    public String getUpdatedData() {
        return updatedData;
    }

    public void setUpdatedData(String updatedData) {
        this.updatedData = updatedData;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<ShopsPhotosModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ShopsPhotosModel> photos) {
        this.photos = photos;
    }

    public List<ShopsReviewsModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ShopsReviewsModel> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetShopsModel that = (GetShopsModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null)
            return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null)
            return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null)
            return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (dataStart != null ? !dataStart.equals(that.dataStart) : that.dataStart != null)
            return false;
        if (dataStop != null ? !dataStop.equals(that.dataStop) : that.dataStop != null)
            return false;
        if (createdData != null ? !createdData.equals(that.createdData) : that.createdData != null)
            return false;
        if (updatedData != null ? !updatedData.equals(that.updatedData) : that.updatedData != null)
            return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (photos != null ? !photos.equals(that.photos) : that.photos != null) return false;
        return reviews != null ? reviews.equals(that.reviews) : that.reviews == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (dataStart != null ? dataStart.hashCode() : 0);
        result = 31 * result + (dataStop != null ? dataStop.hashCode() : 0);
        result = 31 * result + (createdData != null ? createdData.hashCode() : 0);
        result = 31 * result + (updatedData != null ? updatedData.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GetShopsModel{" +
                "id=" + id +
                ", categoryId='" + categoryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", street='" + street + '\'' +
                ", phone='" + phone + '\'' +
                ", dataStart='" + dataStart + '\'' +
                ", dataStop='" + dataStop + '\'' +
                ", createdData='" + createdData + '\'' +
                ", updatedData='" + updatedData + '\'' +
                ", rating=" + rating +
                ", photos=" + photos +
                ", reviews=" + reviews +
                '}';
    }
}
