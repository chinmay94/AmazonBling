package com.amazon.mshopbling.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.amazon.mshopbling.ExternalFragments.SelectAsinFragment;
import com.amazon.mshopbling.MainActivity;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStreamReader;

public class UploadInfluencer extends AsyncTask<String,String,String> {

    Context mContext;
    private ProgressDialog dialog;
    private String mediaId;
    private String imagePath;

    public UploadInfluencer(Context context) {
        mContext = context;
        dialog = new ProgressDialog(mContext);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Uploading");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        imagePath = strings[0];
        String responseString = "";
        try {
            String url = mContext.getResources().getString(R.string.heroku_url);
            String apiName = mContext.getResources().getString(R.string.influencerUploadAPI);
            String apiUrl = url+apiName;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(apiUrl);

            FileBody fileContent = new FileBody(new File(imagePath));

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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dialog.dismiss();
        try {
            JSONObject jsonObject = new JSONObject(result);
            mediaId = jsonObject.getString("mediaId");
        } catch (JSONException e) {
            Log.e("UploadInfluencer", "Json Parsing failed");
        }
        Fragment fragment = new SelectAsinFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mediaId", mediaId);
        bundle.putString("imagePath", imagePath);
        fragment.setArguments(bundle);
        MainActivity currentActivity = (MainActivity) mContext;
        currentActivity.displayFragment(fragment);
    }

}
