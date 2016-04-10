package com.miroslav.menuinyourcity.request.GetShops;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class BaseGetShopsModel {

    @Key("response")
    private GetShopsModel getShopsModel;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public GetShopsModel getGetShopsModel() {
        return getShopsModel;
    }

    public void setGetShopsModel(GetShopsModel getShopsModel) {
        this.getShopsModel = getShopsModel;
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

        BaseGetShopsModel that = (BaseGetShopsModel) o;

        if (getShopsModel != null ? !getShopsModel.equals(that.getShopsModel) : that.getShopsModel != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = getShopsModel != null ? getShopsModel.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseGetShopsModel{" +
                "getShopsModel=" + getShopsModel +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
