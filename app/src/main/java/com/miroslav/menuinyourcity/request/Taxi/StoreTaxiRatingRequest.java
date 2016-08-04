package com.miroslav.menuinyourcity.request.Taxi;

import android.support.annotation.IntRange;
import android.support.annotation.IntegerRes;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.request.BaseRequest;
import com.miroslav.menuinyourcity.request.PostResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 03.08.16.
 */
public class StoreTaxiRatingRequest  extends BaseRequest<PostResponse> {

    private JSONObject jsonObject;

    public StoreTaxiRatingRequest(Long taxiId, @IntRange(from=1,to=10) Integer rating) {
        super(PostResponse.class);

        jsonObject = new JSONObject();
        try {
            jsonObject.put("taxi_id", taxiId);
            jsonObject.put("rating", rating);
            jsonObject.put("user_id", Model.getInstance().currentUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResourceUri() {
        return "taxi/rating";
    }

    @Override
    public PostResponse loadDataFromNetwork() throws Exception {


        HttpRequest request = getHttpRequestFactory().buildPostRequest(new GenericUrl(buildURL()), ByteArrayContent.fromString(("application/json"), jsonObject.toString()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}