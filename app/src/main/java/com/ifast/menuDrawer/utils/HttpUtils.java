package com.ifast.menuDrawer.utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by zhiwen on 2014/11/4.
 */
public class HttpUtils {

    public static String sendPostMessage(String url,String encode){
        String result = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity!=null){
                try {
                    result = EntityUtils.toString(httpEntity, encode);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Log.i("result", result);
        }
        return result;
    }
}
