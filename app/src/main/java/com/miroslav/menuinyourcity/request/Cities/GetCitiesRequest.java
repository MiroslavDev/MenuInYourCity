package com.miroslav.menuinyourcity.request.Cities;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.request.BaseRequest;

/**
 * Created by apple on 4/8/16.
 */
public class GetCitiesRequest extends BaseRequest<BaseCitiesModel> {

    private Long id;

    public GetCitiesRequest(Long id) {
        super(com.miroslav.menuinyourcity.request.Cities.BaseCitiesModel.class);
        this.id = id;
    }

    public GetCitiesRequest() {
        super(com.miroslav.menuinyourcity.request.Cities.BaseCitiesModel.class);
    }

    @Override
    public String getResourceUri() {
        return "cities" + (id == null ? "" : "/" + id);
    }

    @Override
    public com.miroslav.menuinyourcity.request.Cities.BaseCitiesModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}
