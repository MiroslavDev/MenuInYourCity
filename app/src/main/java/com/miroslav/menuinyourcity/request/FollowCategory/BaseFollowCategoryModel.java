package com.miroslav.menuinyourcity.request.FollowCategory;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/28/16.
 */
public class BaseFollowCategoryModel {

    @Key("response")
    private List<FollowCategoryModel> followCategoryModel;
    @Key("error")
    private Boolean error;

    public List<FollowCategoryModel> getFollowCategoryModel() {
        return followCategoryModel;
    }

    public void setFollowCategoryModel(List<FollowCategoryModel> followCategoryModel) {
        this.followCategoryModel = followCategoryModel;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseFollowCategoryModel that = (BaseFollowCategoryModel) o;

        if (followCategoryModel != null ? !followCategoryModel.equals(that.followCategoryModel) : that.followCategoryModel != null)
            return false;
        return error != null ? error.equals(that.error) : that.error == null;

    }

    @Override
    public int hashCode() {
        int result = followCategoryModel != null ? followCategoryModel.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseFollowCategoryModel{" +
                "followCategoryModel=" + followCategoryModel +
                ", error=" + error +
                '}';
    }
}
