package com.miroslav.menuinyourcity.request.Cities;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/8/16.
 */
public class BaseCitiesModel {

    @Key("response")
    private List<com.miroslav.menuinyourcity.request.Cities.CitiesModel> citiesModel;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public List<com.miroslav.menuinyourcity.request.Cities.CitiesModel> getCitiesModel() {
        return citiesModel;
    }

    public void setCitiesModel(List<com.miroslav.menuinyourcity.request.Cities.CitiesModel> citiesModel) {
        this.citiesModel = citiesModel;
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

        BaseCitiesModel that = (BaseCitiesModel) o;

        if (citiesModel != null ? !citiesModel.equals(that.citiesModel) : that.citiesModel != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = citiesModel != null ? citiesModel.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseCitiesModel{" +
                "citiesModel=" + citiesModel +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}