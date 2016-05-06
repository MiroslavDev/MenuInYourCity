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
 * Created by apple on 5/6/16.
 */
public class UnfollowCategoryRequest extends BaseRequest<BaseFollowCategoryModel> {

    private JSONObject jsonObject;

    public UnfollowCategoryRequest(Long category_id) {
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
        return "categories/unfollow";
    }

    @Override
    public BaseFollowCategoryModel loadDataFromNetwork() throws Exception {


        HttpRequest request = getHttpRequestFactory().buildPostRequest(new GenericUrl(buildURL()), ByteArrayContent.fromString(("application/json"), jsonObject.toString()));
        request.setParser(new JacksonFactory().createJsonObjectParser());

        HttpResponse response = request.execute();
        return response.parseAs(getResultType());
    }
}
