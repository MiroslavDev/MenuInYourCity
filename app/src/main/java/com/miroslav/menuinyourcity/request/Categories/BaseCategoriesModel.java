package com.miroslav.menuinyourcity.request.Categories;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/6/16.
 */
public class BaseCategoriesModel {

    @Key("response")
    private List<CategorieModel> categorieList;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public List<CategorieModel> getCategorieList() {
        return categorieList;
    }

    public void setCategorieList(List<CategorieModel> categorieList) {
        this.categorieList = categorieList;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseCategoriesModel that = (BaseCategoriesModel) o;

        if (categorieList != null ? !categorieList.equals(that.categorieList) : that.categorieList != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = categorieList != null ? categorieList.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseCategoriesModel{" +
                "categorieList=" + categorieList +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
