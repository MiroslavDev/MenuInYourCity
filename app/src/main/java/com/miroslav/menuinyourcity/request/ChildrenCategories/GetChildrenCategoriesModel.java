package com.miroslav.menuinyourcity.request.ChildrenCategories;

import com.google.api.client.util.Key;

/**
 * Created by apple on 4/9/16.
 */
public class GetChildrenCategoriesModel {
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
    @Key("user_follow")
    private Boolean userFollow;
    @Key("count_childrens_category")
    private Long countChildrensCategory;
    @Key("count_shops_in_category")
    private Long countShoupsInCategory;

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

    public Boolean getUserFollow() {
        return userFollow;
    }

    public void setUserFollow(Boolean userFollow) {
        this.userFollow = userFollow;
    }

    public Long getCountChildrensCategory() {
        return countChildrensCategory;
    }

    public void setCountChildrensCategory(Long countChildrensCategory) {
        this.countChildrensCategory = countChildrensCategory;
    }

    public Long getCountShoupsInCategory() {
        return countShoupsInCategory;
    }

    public void setCountShoupsInCategory(Long countShoupsInCategory) {
        this.countShoupsInCategory = countShoupsInCategory;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetChildrenCategoriesModel that = (GetChildrenCategoriesModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        if (createdData != null ? !createdData.equals(that.createdData) : that.createdData != null)
            return false;
        if (updatedData != null ? !updatedData.equals(that.updatedData) : that.updatedData != null)
            return false;
        if (userFollow != null ? !userFollow.equals(that.userFollow) : that.userFollow != null)
            return false;
        if (countChildrensCategory != null ? !countChildrensCategory.equals(that.countChildrensCategory) : that.countChildrensCategory != null)
            return false;
        return countShoupsInCategory != null ? countShoupsInCategory.equals(that.countShoupsInCategory) : that.countShoupsInCategory == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (createdData != null ? createdData.hashCode() : 0);
        result = 31 * result + (updatedData != null ? updatedData.hashCode() : 0);
        result = 31 * result + (userFollow != null ? userFollow.hashCode() : 0);
        result = 31 * result + (countChildrensCategory != null ? countChildrensCategory.hashCode() : 0);
        result = 31 * result + (countShoupsInCategory != null ? countShoupsInCategory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GetChildrenCategoriesModel{" +
                "id=" + id +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdData='" + createdData + '\'' +
                ", updatedData='" + updatedData + '\'' +
                ", userFollow=" + userFollow +
                ", countChildrensCategory=" + countChildrensCategory +
                ", countShoupsInCategory=" + countShoupsInCategory +
                '}';
    }
}
