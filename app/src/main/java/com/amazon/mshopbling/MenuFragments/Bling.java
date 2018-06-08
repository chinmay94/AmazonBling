package com.amazon.mshopbling.MenuFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.amazon.mshopbling.Adapters.GridviewAdapter;
import com.amazon.mshopbling.ExternalFragments.FullScreenImageActivity;
import com.amazon.mshopbling.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Bling extends Fragment {

    private boolean hasPermission;
    private boolean hasPermission2;
    private String screenshotsFolderPrefix;
    private GridviewAdapter mAdapter;
    private Button button;
    private GridView gridView;
    private List<File> fileList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bling, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        screenshotsFolderPrefix = getResources().getString(R.string.screenshots_path);

        hasPermission = checkSetPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        hasPermission2 = checkSetPermission(Manifest.permission.INTERNET);

        if(hasPermission && hasPermission2){
            prepareFileList();

            button = getView().findViewById(R.id.refresh_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prepareFileList();
                    mAdapter = new GridviewAdapter(getActivity(), fileList);
                    gridView.setAdapter(mAdapter);
                }
            });

            mAdapter = new GridviewAdapter(getActivity(), fileList);

            // Set custom adapter to gridview
            gridView = getView().findViewById(R.id.gridView1);

            gridView.setAdapter(mAdapter);

            // Implement On Item click listener
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long id) {
                    Intent i = new Intent(getActivity().getApplicationContext(), FullScreenImageActivity.class);
                    // passing array index
                    i.putExtra("filePath", fileList.get(position).getAbsolutePath());
                    startActivity(i);
                }
            });
        } else {
            Toast.makeText(this.getContext(), "App does not have permission to read from storage", Toast.LENGTH_SHORT).show();
        }

        getActivity().setTitle("Title");
    }

    private void prepareFileList() {
        fileList.clear();
        File screenshotsFolder = new File(Environment.getExternalStorageDirectory().getPath() + this.screenshotsFolderPrefix);
        File[] allFileList = screenshotsFolder.listFiles();
        Arrays.sort(allFileList, Collections.<File>reverseOrder());
        for (int i=0; i<allFileList.length; i++) {
            File file = allFileList[i];
            if(!file.getName().contains("tmp")){
                fileList.add(file);
            }
        }
    }

    private boolean checkSetPermission(String permission) {
        boolean hasPermission = false;
        int MY_PERMISSIONS_REQUEST = 0;
        if (ContextCompat.checkSelfPermission(this.getContext(),
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    permission)) {
                hasPermission = false;
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{permission},
                        MY_PERMISSIONS_REQUEST);
            }
        } else {
            hasPermission = true;
        }
        return hasPermission;
    }
}