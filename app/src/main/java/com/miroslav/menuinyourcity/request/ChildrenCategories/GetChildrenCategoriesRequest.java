package com.miroslav.menuinyourcity.request.ChildrenCategories;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.request.BaseRequest;
import com.miroslav.menuinyourcity.request.Categories.BaseCategoriesModel;
import com.miroslav.menuinyourcity.request.Categories.GetCategoriesRequest;

/**
 * Created by apple on 4/8/16.
 */
public class GetChildrenCategoriesRequest extends BaseRequest<BaseChildrenCategoriesModel> {

    protected Long id;

    public GetChildrenCategoriesRequest(Long id) {
        super(BaseChildrenCategoriesModel.class);
        this.id = id;
    }

    @Override
    public String getResourceUri() {
        return "categories/" + id + "/childrens";
    }

    @Override
    public BaseChildrenCategoriesModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        request.getHeaders().set(CITY_ID, Model.getInstance().currentCityId);
        request.getHeaders().set(USER_ID, Model.getInstance().currentUserId);

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}