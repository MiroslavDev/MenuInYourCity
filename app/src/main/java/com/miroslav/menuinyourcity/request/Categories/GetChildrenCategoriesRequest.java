package com.miroslav.menuinyourcity.request.Categories;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Created by apple on 4/8/16.
 */
public class GetChildrenCategoriesRequest  extends GetCategoriesRequest {

    public GetChildrenCategoriesRequest(Long id) {
        super(id);
    }

    @Override
    public String getResourceUri() {
        return super.getResourceUri() + "/childrens";
    }

    @Override
    public BaseCategoriesModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }

}