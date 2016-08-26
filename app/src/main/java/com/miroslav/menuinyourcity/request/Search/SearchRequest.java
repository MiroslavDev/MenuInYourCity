package com.miroslav.menuinyourcity.request.Search;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.request.BaseRequest;
import com.miroslav.menuinyourcity.request.GetShops.BaseGetShopsModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 02.08.16.
 */
public class SearchRequest extends BaseRequest<BaseGetShopsModel> {

    private JSONObject jsonObject;

    public SearchRequest(String searchQuery) {
        super(BaseGetShopsModel.class);

        jsonObject = new JSONObject();
        try {
            jsonObject.put("search", searchQuery);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResourceUri() {
        return "shops/search";
    }

    @Override
    public BaseGetShopsModel loadDataFromNetwork() throws Exception {


        HttpRequest request = getHttpRequestFactory().buildPostRequest(new GenericUrl(buildURL()), ByteArrayContent.fromString(("application/json"), jsonObject.toString()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}