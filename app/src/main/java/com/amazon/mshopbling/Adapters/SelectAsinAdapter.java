package com.amazon.mshopbling.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.amazon.mshopbling.AsinHelpers.Asin;
import com.amazon.mshopbling.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SelectAsinAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Asin> asins;

    @Override
    public int getCount() {
        return asins.size();
    }

    @Override
    public Object getItem(int i) {
        return asins.get(i).getAsinTitle();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgView;
        public CheckBox checkBox;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.asin_selector, null);

            view.imgView = (ImageView) convertView.findViewById(R.id.grid_item_image);
            view.checkBox = (CheckBox) convertView.findViewById(R.id.grid_item_checkbox);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        ImageView imageView = view.imgView;
        CheckBox checkBox = view.checkBox;

        checkBox.setChecked(false);

        final Asin asin = asins.get(i);

        Picasso.get()
                .load(asin.getImageUrl())
                .into(imageView);

        checkBox.setText(asin.getAsin());

        return convertView;
    }
}
