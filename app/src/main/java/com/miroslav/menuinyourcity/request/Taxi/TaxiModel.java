package com.miroslav.menuinyourcity.request.Taxi;

import com.google.api.client.util.Key;

/**
 * Created by apple on 03.08.16.
 */
public class TaxiModel {
    @Key("id")
    private Long id;
    @Key("phone")
    private String phone;
    @Key("name")
    private String name;
    @Key("rating")
    private Double rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxiModel taxiModel = (TaxiModel) o;

        if (id != null ? !id.equals(taxiModel.id) : taxiModel.id != null) return false;
        if (phone != null ? !phone.equals(taxiModel.phone) : taxiModel.phone != null) return false;
        if (name != null ? !name.equals(taxiModel.name) : taxiModel.name != null) return false;
        return rating != null ? rating.equals(taxiModel.rating) : taxiModel.rating == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaxiModel{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}
