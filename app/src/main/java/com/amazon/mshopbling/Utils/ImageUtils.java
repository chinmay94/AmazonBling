package com.amazon.mshopbling.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class ImageUtils {

    public static void setImageToImageViewAsPerDisplaySize(Activity activity, ImageView imageView, int resourceId) {

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(activity.getResources(), resourceId, dimensions);
        int height = dimensions.outHeight;
        int width =  dimensions.outWidth;

        Picasso.get()
                .load(resourceId)
                .resize(width,height)
                .transform(new CropSquareTransformation())
                .into(imageView);

    }

}
