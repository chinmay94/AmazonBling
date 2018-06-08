package com.amazon.mshopbling.ExternalFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amazon.mshopbling.Adapters.GridviewAdapter;
import com.amazon.mshopbling.AsyncTasks.UploadInfluencer;
import com.amazon.mshopbling.R;

import java.io.File;

public class UploadInfluencerImageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_image_influencer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String imagePath = getArguments().getString("imagePath");

        GridviewAdapter gridviewAdapter = new GridviewAdapter(getActivity());
        ImageView imageView = getView().findViewById(R.id.full_image_view_in);

        gridviewAdapter.setImageFromFilePathToImageViewer(imagePath, imageView);

        FloatingActionButton floatingActionButton = getView().findViewById(R.id.fabIn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UploadInfluencer(getContext()).execute(imagePath);
            }
        });

        FloatingActionButton fabShare = getView().findViewById(R.id.fabShareIn);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Share",imagePath);
                createShareIntent(imagePath);
            }
        });
    }

    private Intent createShareIntent(String imageUri) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        if(imageUri != null){
            File media = new File(imageUri);
            Uri uri = Uri.fromFile(media);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
        }

        startActivity(Intent.createChooser(shareIntent,  "Share with..."));

        return shareIntent;
    }
}
