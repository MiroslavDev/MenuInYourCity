package com.miroslav.menuinyourcity.request;

import android.content.Context;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

public abstract class BaseRequest<Result> extends GoogleHttpClientSpiceRequest<Result> {

    private static final String DOMAIN = "http://menu.frameapp.com.ua/api";

    protected BaseRequest(Class<Result> clazz) {
        super(clazz);
    }

    protected String buildURL() {
        return DOMAIN + "/" + getResourceUri();
    }

    public String getResourceUri() {
        return "";
    }

    public long getCacheExpiryDuration() {
        return DurationInMillis.ALWAYS_EXPIRED;
    }

    @Override
    public Result loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildGetRequest(new GenericUrl(buildURL()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}