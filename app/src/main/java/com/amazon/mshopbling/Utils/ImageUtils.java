package com.amazon.mshopbling.Utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUtils {

    public static void setImageToImageViewAsPerDisplaySize(Activity activity, ImageView imageView, int resourceId) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        Picasso.get()
                .load(resourceId)
                .resize(size.x,size.y)
                .transform(new CropSquareTransformation())
                .into(imageView);

    }

}
