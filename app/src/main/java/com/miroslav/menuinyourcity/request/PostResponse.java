package com.miroslav.menuinyourcity.request;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 03.08.16.
 */
public class PostResponse {
    @Key("response")
    private List<String> responseList;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public List<String> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<String> responseList) {
        this.responseList = responseList;
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

        PostResponse that = (PostResponse) o;

        if (responseList != null ? !responseList.equals(that.responseList) : that.responseList != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = responseList != null ? responseList.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "responseList=" + responseList +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
