package com.miroslav.menuinyourcity.request.GetShops;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.request.BaseRequest;

/**
 * Created by apple on 4/10/16.
 */
public class GetShopsRequest extends BaseRequest<BaseGetShopsModel> {

    private Long id;

    public GetShopsRequest(Long id) {
        super(BaseGetShopsModel.class);
        this.id = id;
    }

    @Override
    public String getResourceUri() {
        return "shops/" + id;
    }

    @Override
    public BaseGetShopsModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}