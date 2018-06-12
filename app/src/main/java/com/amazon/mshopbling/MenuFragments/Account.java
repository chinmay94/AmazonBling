package com.amazon.mshopbling.MenuFragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amazon.mshopbling.R;
import com.amazon.mshopbling.Utils.CropSquareTransformation;
import com.amazon.mshopbling.Utils.ImageUtils;
import com.squareup.picasso.Picasso;

public class Account extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_image, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) getView().findViewById(R.id.image);
        ImageUtils.setImageToImageViewAsPerDisplaySize(this.getActivity(), imageView, R.drawable.mock_account);
    }
}