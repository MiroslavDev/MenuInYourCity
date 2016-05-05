package com.miroslav.menuinyourcity.request.Cities;

import com.google.api.client.util.Key;

/**
 * Created by apple on 4/8/16.
 */
public class CitiesModel {

    @Key("id")
    private Long id;
    @Key("name")
    private String name;
    @Key("created_at")
    private String createdData;
    @Key("updated_at")
    private String updatedData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedData() {
        return createdData;
    }

    public void setCreatedData(String createdData) {
        this.createdData = createdData;
    }

    public String getUpdatedData() {
        return updatedData;
    }

    public void setUpdatedData(String updatedData) {
        this.updatedData = updatedData;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitiesModel that = (CitiesModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (createdData != null ? !createdData.equals(that.createdData) : that.createdData != null)
            return false;
        return !(updatedData != null ? !updatedData.equals(that.updatedData) : that.updatedData != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdData != null ? createdData.hashCode() : 0);
        result = 31 * result + (updatedData != null ? updatedData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CitiesModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdData='" + createdData + '\'' +
                ", updatedData='" + updatedData + '\'' +
                '}';
    }
}
