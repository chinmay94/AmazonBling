package com.amazon.mshopbling.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.amazon.mshopbling.R;
import com.amazon.mshopbling.Utils.CropSquareTransformation;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class GridviewAdapter extends BaseAdapter {

    private Activity activity;
    private List<File> fileList;

    public GridviewAdapter(Activity activity) {
        this.activity = activity;
    }

    public GridviewAdapter(Activity activity, List<File> fileList) {
        super();
        this.fileList = fileList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileList.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgViewFlag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_row, null);

            view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        ImageView imageView = view.imgViewFlag;

        setImageFromFilePathToImageViewer(fileList.get(position).getAbsolutePath(), imageView);

        return convertView;
    }

    public void setImageFromFilePathToImageViewer(String filePath, ImageView imageView) {
        File file = new File(filePath);
        Picasso.get()
                .load(file)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }
}
