package com.miroslav.menuinyourcity.request.Reviews;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/26/16.
 */
public class StoreReviewModel {

    @Key("response")
    private List<String> response;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;
    @Key("validator")
    private List<String> validator;

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
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

    public List<String> getValidator() {
        return validator;
    }

    public void setValidator(List<String> validator) {
        this.validator = validator;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreReviewModel that = (StoreReviewModel) o;

        if (response != null ? !response.equals(that.response) : that.response != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return validator != null ? validator.equals(that.validator) : that.validator == null;

    }

    @Override
    public int hashCode() {
        int result = response != null ? response.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (validator != null ? validator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StoreReviewModel{" +
                "response=" + response +
                ", error=" + error +
                ", message='" + message + '\'' +
                ", validator=" + validator +
                '}';
    }
}
