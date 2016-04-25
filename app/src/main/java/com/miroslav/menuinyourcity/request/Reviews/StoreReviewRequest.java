package com.miroslav.menuinyourcity.request.Reviews;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.request.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 4/26/16.
 */
public class StoreReviewRequest  extends BaseRequest<StoreReviewModel> {

    private JSONObject jsonResult;

    public StoreReviewRequest(String name, String phone, String rating, String shopId, String review) {
        super(StoreReviewModel.class);

        jsonResult = new JSONObject();
        try {
            jsonResult.put("name", name);
            jsonResult.put("phone", phone);
            jsonResult.put("rating", rating);
            jsonResult.put("user_id", Model.getInstance().currentUserId);
            jsonResult.put("shop_id", shopId);
            jsonResult.put("review", review);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResourceUri() {
        return "reviews";
    }

    @Override
    public StoreReviewModel loadDataFromNetwork() throws Exception {
        HttpRequest request = getHttpRequestFactory().buildPostRequest(new GenericUrl(buildURL()), ByteArrayContent.fromString("application/json", jsonResult.toString()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}