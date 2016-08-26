package com.miroslav.menuinyourcity.request.Taxi;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.request.BaseRequest;

/**
 * Created by apple on 03.08.16.
 */
public class GetTaxiRequest extends BaseRequest<BaseTaxiModel> {

    public GetTaxiRequest() {
        super(BaseTaxiModel.class);
    }

    @Override
    public String getResourceUri() {
        return "taxi";
    }

    @Override
    public BaseTaxiModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        request.getHeaders().set(CITY_ID, Model.getInstance().currentCityId);

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}
