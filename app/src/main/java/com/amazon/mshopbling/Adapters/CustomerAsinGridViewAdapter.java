package com.amazon.mshopbling.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.mshopbling.R;
import com.amazon.mshopbling.Utils.ImageUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerAsinGridViewAdapter extends BaseAdapter{

    private Activity activity;
    private List<String> customerAsins;
    private String tagValue;

    public CustomerAsinGridViewAdapter(Activity activity, ArrayList<String> customerAsins, String tagValue) {
        super();
        this.customerAsins = customerAsins;
        this.activity = activity;
        this.tagValue = tagValue;
    }

    @Override
    public int getCount() {
        return customerAsins.size();
    }

    @Override
    public Object getItem(int position) {
        return customerAsins.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder
    {
        public TextView asinText;
        public ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        CustomerAsinGridViewAdapter.ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_customer_asins, null);

            view.asinText = (TextView) convertView.findViewById(R.id.textViewAsins);
            view.imageView = (ImageView) convertView.findViewById(R.id.imageViewCustomerAsin);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        String asinInfo = customerAsins.get(position);
        try {
            final JSONObject asinInfoJson = new JSONObject(asinInfo);

            TextView textView = view.asinText;
            textView.setText(asinInfoJson.getString("asinTitle"));

            ImageView imageView = view.imageView;
            Picasso.get()
                    .load(asinInfoJson.getString("asinImage"))
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String amazonUrl = "com.amazon.mobile.shopping://www.amazon.in/products/" + asinInfoJson.getString("asin") + "/?tag=" + tagValue;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(amazonUrl));
                        view.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.e("AmazonIntent", "JsonParserException");
                    }
                }
            });

        } catch (Exception e) {
            Log.e("customerAsinGridView", "JsonParserException");
        }

        return convertView;
    }
}
