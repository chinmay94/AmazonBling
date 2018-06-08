package com.amazon.mshopbling.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.amazon.mshopbling.ExternalFragments.CustomerAsinSelectorFragment;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UploadCustomer extends AsyncTask<String,String,String> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String responseString = "";

    public UploadCustomer(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Uploading...");
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String url = mContext.getResources().getString(R.string.heroku_url);
            String apiName = mContext.getResources().getString(R.string.customerUploadAPI);
            String apiUrl = url+apiName;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(apiUrl);

            FileBody fileContent = new FileBody(new File(strings[0]));

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", fileContent);
            httppost.setEntity(builder.build());

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            responseString = IOUtils.toString(new InputStreamReader(resEntity.getContent()));
            Log.e("Response", responseString);
        } catch (Exception e) {
            Log.e("","Exception");
        }

        return responseString;
    }

    @Override
    protected void onPostExecute(String result)
    {
        mProgressDialog.dismiss();
        if(responseString != null) {
            try {
                JSONObject resultJSON = new JSONObject(responseString);
                JSONArray creativeAsinsJSONArray = resultJSON.getJSONArray("asinList");
                ArrayList<String> creativeAsins = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for(int i=0; i<creativeAsinsJSONArray.length(); i++) {
                    creativeAsins.add(creativeAsinsJSONArray.get(i).toString());
                    sb.append(creativeAsinsJSONArray.get(i).toString()+",");
                }
                Log.e("SB", sb.toString());
                String tagValue = resultJSON.getString("tagValue");
                if(creativeAsins.isEmpty() || tagValue == null || tagValue.isEmpty()) {
                    Log.e("UploadCustomer", "Response not as expected");
                } else {
                    Fragment fragment = new CustomerAsinSelectorFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("tagValue", tagValue);
                    bundle.putStringArrayList("asins", creativeAsins);
                    fragment.setArguments(bundle);

                    MainActivity currentActivity = (MainActivity) mContext;
                    currentActivity.displayFragment(fragment);
                }
            } catch (Exception e) {
                Log.e("UploadCustomer", "Unable to parse response");
            }
        }
    }
}
