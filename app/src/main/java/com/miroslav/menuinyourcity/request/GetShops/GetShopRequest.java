package com.miroslav.menuinyourcity.request.GetShops;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.request.BaseRequest;

/**
 * Created by apple on 4/17/16.
 */
public class GetShopRequest extends BaseRequest<BaseShopModel> {

    private Long id;

    public GetShopRequest(Long id) {
        super(BaseShopModel.class);
        this.id = id;
    }

    @Override
    public String getResourceUri() {
        return "shops/" + id;
    }

    @Override
    public BaseShopModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        request.getHeaders().set(CITY_ID, Model.getInstance().currentCityId);

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}