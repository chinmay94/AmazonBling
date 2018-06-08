package com.amazon.mshopbling.ExternalFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.amazon.mshopbling.Adapters.CustomerAsinGridViewAdapter;
import com.amazon.mshopbling.R;

import java.util.ArrayList;

public class CustomerAsinSelectorFragment extends Fragment {

    private String screenshotsFolderPrefix;
    private CustomerAsinGridViewAdapter mAdapter;
    private Button button;
    private GridView gridView;
    private ArrayList<String> asins;
    private String tagValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_asins, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateAsinList();
    }

    public void populateAsinList() {
        tagValue = getArguments().getString("tagValue");
        asins = getArguments().getStringArrayList("asins");

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<asins.size();i++) {
            stringBuilder.append(asins.get(i));
        }
        Log.e("PopulateAsinList", stringBuilder.toString());

        mAdapter = new CustomerAsinGridViewAdapter(getActivity(), asins, tagValue);

        // Set custom adapter to gridview
        gridView = getView().findViewById(R.id.gridViewCustomerAsins);

        if(gridView == null) {
            Log.e("PopulateAsinList", "gridView is null");
        }

        gridView.setAdapter(mAdapter);

        // Implement On Item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
            }
        });
    }

}
