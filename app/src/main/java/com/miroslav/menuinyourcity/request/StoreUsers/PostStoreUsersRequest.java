package com.miroslav.menuinyourcity.request.StoreUsers;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.miroslav.menuinyourcity.request.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 4/9/16.
 */
public class PostStoreUsersRequest extends BaseRequest<BaseStoreUsersModel> {

    private String type = "android";
    private JSONObject jsonObject;

    public PostStoreUsersRequest(String token) {
        super(BaseStoreUsersModel.class);

        jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResourceUri() {
        return "users/";
    }

    @Override
    public BaseStoreUsersModel loadDataFromNetwork() throws Exception {


        HttpRequest request = getHttpRequestFactory().buildPostRequest(new GenericUrl(buildURL()), ByteArrayContent.fromString(("application/json"), jsonObject.toString()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}