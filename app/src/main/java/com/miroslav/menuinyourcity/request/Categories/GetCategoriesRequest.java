package com.miroslav.menuinyourcity.request.Categories;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.request.BaseRequest;
import com.miroslav.menuinyourcity.request.ChildrenCategories.BaseChildrenCategoriesModel;

/**
 * Created by apple on 4/6/16.
 */
public class GetCategoriesRequest extends BaseRequest<BaseCategoriesModel> {

    protected Long id;

    public GetCategoriesRequest(Long id) {
        super(BaseCategoriesModel.class);
        this.id = id;
    }

    public GetCategoriesRequest() {
        super(BaseCategoriesModel.class);
    }

    @Override
    public String getResourceUri() {
        return "categories" + (id == null ? "" : "/" + id);
    }

    @Override
    public BaseCategoriesModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}
