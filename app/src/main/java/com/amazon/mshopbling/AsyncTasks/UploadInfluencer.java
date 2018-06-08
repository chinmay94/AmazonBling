package com.amazon.mshopbling.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazon.mshopbling.R;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.InputStreamReader;

public class UploadInfluencer extends AsyncTask<String,String,String> {

    Context mContext;

    public UploadInfluencer(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String responseString = "";
        try {
            String url = mContext.getResources().getString(R.string.heroku_url);
            String apiName = mContext.getResources().getString(R.string.influencerUploadAPI);
            String apiUrl = url+apiName;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(apiUrl);

            FileBody fileContent = new FileBody(new File(strings[0]));

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", fileContent);
            builder.addTextBody("tagValue", "assocamazonops");
            httppost.setEntity(builder.build());

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            responseString = IOUtils.toString(new InputStreamReader(resEntity.getContent()));

        } catch (Exception e) {
            Log.e("","Exception");
        }

        return responseString;
    }
}
