package com.miroslav.menuinyourcity.request.StoreUsers;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/9/16.
 */
public class BaseStoreUsersModel {

    @Key("response")
    private List<String> userInfo;
    @Key("error")
    private Boolean error;
    @Key("message")
    private Long message;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Long getMessage() {
        return message;
    }

    public void setMessage(Long message) {
        this.message = message;
    }

    public List<String> getUserInfo() {

        return userInfo;
    }

    public void setUserInfo(List<String> userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseStoreUsersModel that = (BaseStoreUsersModel) o;

        if (userInfo != null ? !userInfo.equals(that.userInfo) : that.userInfo != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = userInfo != null ? userInfo.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseStoreUsersModel{" +
                "userInfo=" + userInfo +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
