package com.miroslav.menuinyourcity.request.GetEvents;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.request.BaseRequest;

/**
 * Created by apple on 4/10/16.
 */
public class GetEventRequest  extends BaseRequest<BaseGetEventsModel> {

    public GetEventRequest() {
        super(BaseGetEventsModel.class);
    }

    @Override
    public String getResourceUri() {
        return "events/";
    }

    @Override
    public BaseGetEventsModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}