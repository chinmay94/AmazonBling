package com.amazon.mshopbling.ExternalFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amazon.mshopbling.Adapters.GridviewAdapter;
import com.amazon.mshopbling.R;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        Intent intent = getIntent();
        String filePath = intent.getExtras().getString("filePath");

        GridviewAdapter gridviewAdapter = new GridviewAdapter(this);
        ImageView imageView = findViewById(R.id.full_image_view);

        gridviewAdapter.setImageFromFilePathToImageViewer(filePath, imageView);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Upload Complete", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}
