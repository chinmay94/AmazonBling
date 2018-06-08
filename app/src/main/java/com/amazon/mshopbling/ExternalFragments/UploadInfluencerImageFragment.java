package com.amazon.mshopbling.ExternalFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amazon.mshopbling.Adapters.GridviewAdapter;
import com.amazon.mshopbling.AsyncTasks.UploadInfluencer;
import com.amazon.mshopbling.R;

public class UploadInfluencerImageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String imagePath = getArguments().getString("imagePath");

        GridviewAdapter gridviewAdapter = new GridviewAdapter(getActivity());
        ImageView imageView = getView().findViewById(R.id.full_image_view);

        gridviewAdapter.setImageFromFilePathToImageViewer(imagePath, imageView);

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UploadInfluencer(getContext()).execute(imagePath);
            }
        });
    }
}
