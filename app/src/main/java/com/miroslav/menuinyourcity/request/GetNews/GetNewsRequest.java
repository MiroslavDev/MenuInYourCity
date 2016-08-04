package com.miroslav.menuinyourcity.request.GetNews;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.request.BaseRequest;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by apple on 4/10/16.
 */
public class GetNewsRequest extends BaseRequest<BaseGetNewsModel> {

    public GetNewsRequest() {
        super(BaseGetNewsModel.class);
    }

    @Override
    public String getResourceUri() {
        return "news/";
    }

    public long getCacheExpiryDuration() {
        return DurationInMillis.ALWAYS_EXPIRED;
    }

    @Override
    public BaseGetNewsModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        request.getHeaders().set(CITY_ID, Model.getInstance().currentCityId);

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}
