package com.amazon.mshopbling.MenuFragments;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.amazon.mshopbling.Adapters.GridviewAdapter;
import com.amazon.mshopbling.ExternalFragments.FullScreenImageFragment;
import com.amazon.mshopbling.ExternalFragments.UploadInfluencerImageFragment;
import com.amazon.mshopbling.MainActivity;
import com.amazon.mshopbling.R;
import com.amazon.mshopbling.Utils.FileUtils;
import com.amazon.mshopbling.Utils.PermissionUtils;

import java.io.File;
import java.util.List;

public class BlingInfluencer extends Fragment {

    private boolean hasPermission;
    private boolean hasPermission2;
    private String screenshotsFolderPrefix;
    private GridviewAdapter mAdapter;
    private Button button;
    private GridView gridView;
    private List<File> fileList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bling_influencer, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        screenshotsFolderPrefix = getResources().getString(R.string.screenshots_path);
        hasPermission = PermissionUtils.checkSetPermission(this.getContext(), this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        hasPermission2 = PermissionUtils.checkSetPermission(this.getContext(), this.getActivity(), Manifest.permission.INTERNET);
        if(hasPermission && hasPermission2) {
            populateList();
        } else {
            Toast.makeText(this.getContext(), "App does not have permission to read from storage", Toast.LENGTH_SHORT).show();
        }
    }

    public void populateList() {
        fileList = FileUtils.prepareFileList(screenshotsFolderPrefix);

        button = getView().findViewById(R.id.refresh_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileList = FileUtils.prepareFileList(screenshotsFolderPrefix);
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
                String imagePath = fileList.get(position).getAbsolutePath();
                Fragment fragment = new UploadInfluencerImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString("imagePath", imagePath);
                fragment.setArguments(bundle);
                MainActivity currentActivity = (MainActivity) getActivity();
                currentActivity.displayFragment(fragment);
            }
        });
    }
}