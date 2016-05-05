package com.miroslav.menuinyourcity.request.GetShops;

import com.google.api.client.util.Key;

/**
 * Created by apple on 4/17/16.
 */
public class BaseShopModel {

    @Key("response")
    private ShopsModel shop;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public ShopsModel getShop() {
        return shop;
    }

    public void setShop(ShopsModel shop) {
        this.shop = shop;
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

        BaseShopModel that = (BaseShopModel) o;

        if (shop != null ? !shop.equals(that.shop) : that.shop != null) return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = shop != null ? shop.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseShopModel{" +
                "shop=" + shop +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
