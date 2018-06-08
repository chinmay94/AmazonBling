package com.amazon.mshopbling.Utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

public class CropSquareTransformation implements Transformation {

    @Override public Bitmap transform(Bitmap source) {
        int x = source.getWidth();
        int y = source.getHeight();
        Bitmap result = source;

        if(x>y) {
            result = rotateImage(result, x, y,  90);
        }

        if (result != source) {
            source.recycle();
        }

        return result;
    }

    @Override public String key() { return "square()"; }

    private static Bitmap rotateImage(Bitmap img, int x, int y, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img,  0, 0, x, y, matrix, true);
        return rotatedImg;
    }

}
