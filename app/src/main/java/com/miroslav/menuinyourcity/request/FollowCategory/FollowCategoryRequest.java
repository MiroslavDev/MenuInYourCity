package com.miroslav.menuinyourcity.request.FollowCategory;

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
 * Created by apple on 4/28/16.
 */
public class FollowCategoryRequest extends BaseRequest<BaseFollowCategoryModel> {

    private JSONObject jsonObject;

    public FollowCategoryRequest(Long category_id) {
        super(BaseFollowCategoryModel.class);

        jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", Model.getInstance().currentUserId);
            jsonObject.put("category_id", category_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResourceUri() {
        return "categories/follow";
    }

    @Override
    public BaseFollowCategoryModel loadDataFromNetwork() throws Exception {


        HttpRequest request = getHttpRequestFactory().buildPostRequest(new GenericUrl(buildURL()), ByteArrayContent.fromString(("application/json"), jsonObject.toString()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}
