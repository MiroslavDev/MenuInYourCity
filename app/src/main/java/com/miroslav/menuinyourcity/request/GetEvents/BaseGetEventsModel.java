package com.miroslav.menuinyourcity.request.GetEvents;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by apple on 4/10/16.
 */
public class BaseGetEventsModel {
    @Key("response")
    private List<GetEventModel> eventsModel;
    @Key("error")
    private Boolean error;
    @Key("message")
    private String message;

    public List<GetEventModel> getEventsModel() {
        return eventsModel;
    }

    public void setEventsModel(List<GetEventModel> eventsModel) {
        this.eventsModel = eventsModel;
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

        BaseGetEventsModel that = (BaseGetEventsModel) o;

        if (eventsModel != null ? !eventsModel.equals(that.eventsModel) : that.eventsModel != null)
            return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = eventsModel != null ? eventsModel.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseGetEventsModel{" +
                "eventsModel=" + eventsModel +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
