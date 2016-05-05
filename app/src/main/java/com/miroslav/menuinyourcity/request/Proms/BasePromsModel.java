package com.miroslav.menuinyourcity.request.Proms;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/16/16.
 */
public class BasePromsModel {

    @Key("response")
    private List<PromsModel> promList;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public List<PromsModel> getPromList() {
        return promList;
    }

    public void setPromList(List<PromsModel> promList) {
        this.promList = promList;
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

        BasePromsModel that = (BasePromsModel) o;

        if (promList != null ? !promList.equals(that.promList) : that.promList != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = promList != null ? promList.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BasePromsModel{" +
                "promList=" + promList +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
