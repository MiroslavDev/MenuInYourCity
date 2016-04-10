package com.miroslav.menuinyourcity.request.GetNews;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class BaseGetNewsModel {
    @Key("response")
    private List<GetNewsModel> newsModel;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public List<GetNewsModel> getNewsModel() {
        return newsModel;
    }

    public void setNewsModel(List<GetNewsModel> newsModel) {
        this.newsModel = newsModel;
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

        BaseGetNewsModel that = (BaseGetNewsModel) o;

        if (newsModel != null ? !newsModel.equals(that.newsModel) : that.newsModel != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = newsModel != null ? newsModel.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseGetNewsModel{" +
                "newsModel=" + newsModel +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
