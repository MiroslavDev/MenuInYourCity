package com.miroslav.menuinyourcity.request.Categories;

import com.google.api.client.util.Key;

import java.util.Date;

/**
 * Created by apple on 4/6/16.
 */
public class CategorieModel {

    @Key("id")
    private Long id;
    @Key("parent_id")
    private String parentId;
    @Key("name")
    private String name;
    @Key("image")
    private String imageUrl;
    @Key("created_at")
    private String createdData;
    @Key("updated_at")
    private String updatedData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategorieModel that = (CategorieModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        if (createdData != null ? !createdData.equals(that.createdData) : that.createdData != null)
            return false;
        return !(updatedData != null ? !updatedData.equals(that.updatedData) : that.updatedData != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (createdData != null ? createdData.hashCode() : 0);
        result = 31 * result + (updatedData != null ? updatedData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategorieModel{" +
                "id=" + id +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdData=" + createdData +
                ", updatedData=" + updatedData +
                '}';
    }
}
