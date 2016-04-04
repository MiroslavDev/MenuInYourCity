package com.miroslav.menuinyourcity.request;

import android.content.Context;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

public abstract class BaseRequest<Result> extends GoogleHttpClientSpiceRequest<Result> {

    private Context context;

    private static final String PROTOCOL = "http";
    private static final String DOMAIN = "192.168.1.115";

    protected BaseRequest(Class<Result> clazz, Context context) {
        super(clazz);
        this.context = context;
    }

    protected String buildURL() {
        return PROTOCOL + "://" + DOMAIN + "/restapi" + "/" + getResourceUri();
    }

    public String getResourceUri() {
        return "";
    }

    public long getCacheExpiryDuration() {
        return DurationInMillis.ALWAYS_EXPIRED;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public Result loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}