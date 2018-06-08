package com.amazon.mshopbling.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.amazon.mshopbling.R;

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
        public Button getMeToAmazonButton;
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
            view.getMeToAmazonButton = (Button) convertView.findViewById(R.id.getMeOnAmazonButton);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        TextView textView = view.asinText;
        textView.setText(customerAsins.get(position));

        return convertView;
    }
}
