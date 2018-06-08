package com.amazon.mshopbling.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amazon.mshopbling.R;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStreamReader;

public class SaveAsins extends AsyncTask<String,String,String> {

    Context mContext;
    private ProgressDialog dialog;
    private String mediaId;

    public SaveAsins(Context context) {
        mContext = context;
        dialog = new ProgressDialog(mContext);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Saving Asins");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String responseString = "";
        try {
            String url = mContext.getResources().getString(R.string.heroku_url);
            String apiName = mContext.getResources().getString(R.string.addAsinApi);
            String apiUrl = url+apiName;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(apiUrl);

            //FileBody fileContent = new FileBody(new File(strings[0]));

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            //builder.addPart("file", fileContent);
            builder.addTextBody("asinList", strings[0]);
            builder.addTextBody("mediaId", strings[1]);
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
        Toast.makeText(mContext, "Asins Added", Toast.LENGTH_SHORT).show();
        /*Fragment fragment = new SelectAsinFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mediaId", mediaId);
        fragment.setArguments(bundle);
        MainActivity currentActivity = (MainActivity) mContext;
        currentActivity.displayFragment(fragment);*/
    }
}
