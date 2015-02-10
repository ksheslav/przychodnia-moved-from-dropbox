package com.example.moher.chitchat;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class JSONParser {
    private JSONObject mJsonObject = null;
    HttpEntity mHttpEntity;

    public JSONObject makeRequest(String url, List<NameValuePair> list, String method) {
        try {
            if (method.equals("GET")) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(list, "utf-8");
                url += "?" + paramString;
                Log.e("SHOW", url);
                HttpGet httpGet = new HttpGet(url);

                HttpResponse mHttpResponse = httpClient.execute(httpGet);

                mHttpEntity = mHttpResponse.getEntity();
            } else if (method.equals("POST")) {
                DefaultHttpClient mDefaultHttpClient = new DefaultHttpClient();
                HttpPost mHttpPost = new HttpPost(url);
                mHttpPost.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse mHttpResponse = mDefaultHttpClient.execute(mHttpPost);
                mHttpEntity = mHttpResponse.getEntity();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String entityString = EntityUtils.toString(mHttpEntity);
            Log.e("SHOW", entityString);
            mJsonObject = new JSONObject(entityString);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJsonObject;
    }
}
