package com.miroslav.menuinyourcity.request.GetShops;

import com.google.api.client.util.Key;

/**
 * Created by apple on 4/10/16.
 */
public class ShopsReviewsModel {
    @Key("id")
    private Long id;
    @Key("name")
    private String name;
    @Key("review")
    private String review;
    @Key("phone")
    private String phone;
    @Key("shop_id")
    private String shopId;
    @Key("user_id")
    private String userId;
    @Key("rating")
    private String rating;
    @Key("publish")
    private String publich;
    @Key("created_at")
    private String createdAt;
    @Key("updated_at")
    private String updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPublich() {
        return publich;
    }

    public void setPublich(String publich) {
        this.publich = publich;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopsReviewsModel that = (ShopsReviewsModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (review != null ? !review.equals(that.review) : that.review != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (publich != null ? !publich.equals(that.publich) : that.publich != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null)
            return false;
        return !(updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (publich != null ? publich.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopsReviewsModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", review='" + review + '\'' +
                ", phone='" + phone + '\'' +
                ", shopId='" + shopId + '\'' +
                ", userId='" + userId + '\'' +
                ", rating='" + rating + '\'' +
                ", publich='" + publich + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
