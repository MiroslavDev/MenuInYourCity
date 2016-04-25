package com.miroslav.menuinyourcity;

/**
 * Created by apple on 4/10/16.
 */
public class Model {
    private static Model model;

    public String currentCity;
    public Long currentCityId;
    public Long currentUserId;

    private Model() {}

    public static Model getInstance() {
        if(model == null)
            model = new Model();
        return model;
    }


}
