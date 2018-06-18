package com.amazon.mshopbling.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.mshopbling.AsinHelpers.Asin;
import com.amazon.mshopbling.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lombok.AllArgsConstructor;

public class SelectAsinAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Asin> asins;
    private boolean[] thumbnailsselection;

    public SelectAsinAdapter(Activity a, ArrayList<Asin> as, boolean[] t) {
        this.activity = a;
        this.asins = as;
        this.thumbnailsselection = t;
    }

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
        public TextView textView;
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
            view.textView = (TextView) convertView.findViewById(R.id.grid_item_tv);

            view.checkBox.setChecked(false);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        ImageView imageView = view.imgView;
        TextView textView = view.textView;
        CheckBox checkBox = view.checkBox;

        view.checkBox.setId(i);
        view.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                int id = cb.getId();
                Log.w("Id of checkbox is: ", ""+id);
                if (thumbnailsselection[id]) {
                    cb.setChecked(false);
                    thumbnailsselection[id] = false;
                } else {
                    cb.setChecked(true);
                    thumbnailsselection[id] = true;
                }
            }
        });

        final Asin asin = asins.get(i);

        Picasso.get()
                .load(asin.getImageUrl())
                .into(imageView);

        textView.setText(asin.getAsinTitle());
        checkBox.setText(asin.getAsin());
        checkBox.setChecked(thumbnailsselection[i]);

        return convertView;
    }
}
