package com.miroslav.menuinyourcity.request.Taxi;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 03.08.16.
 */
public class BaseTaxiModel {
    @Key("response")
    private List<TaxiModel> taxiList;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public List<TaxiModel> getTaxiList() {
        return taxiList;
    }

    public void setTaxiList(List<TaxiModel> taxiList) {
        this.taxiList = taxiList;
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

        BaseTaxiModel baseTaxiModel = (BaseTaxiModel) o;

        if (taxiList != null ? !taxiList.equals(baseTaxiModel.taxiList) : baseTaxiModel.taxiList != null)
            return false;
        if (error != null ? !error.equals(baseTaxiModel.error) : baseTaxiModel.error != null) return false;
        return message != null ? message.equals(baseTaxiModel.message) : baseTaxiModel.message == null;

    }

    @Override
    public int hashCode() {
        int result = taxiList != null ? taxiList.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseTaxiModel{" +
                "taxiList=" + taxiList +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
