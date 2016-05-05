package com.miroslav.menuinyourcity.request.Proms;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.request.BaseRequest;

/**
 * Created by apple on 4/16/16.
 */
public class PromsRequest  extends BaseRequest<BasePromsModel> {

    protected Long id;

    public PromsRequest(Long id) {
        super(BasePromsModel.class);
        this.id = id;
    }

    @Override
    public String getResourceUri() {
        return "promos/"; //+ (id == null ? "" : id);
    }

    @Override
    public BasePromsModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        //request.getHeaders().set(CITY_ID, Model.getInstance().currentCityId);

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}

