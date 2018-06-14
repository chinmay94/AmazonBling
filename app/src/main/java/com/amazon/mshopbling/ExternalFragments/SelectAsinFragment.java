package com.amazon.mshopbling.ExternalFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.amazon.mshopbling.Adapters.SelectAsinAdapter;
import com.amazon.mshopbling.AsinHelpers.Asin;
import com.amazon.mshopbling.AsyncTasks.SaveAsins;
import com.amazon.mshopbling.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SelectAsinFragment extends Fragment {

    String mediaId;
    String imagePath;
    ArrayList<Asin> asins;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_asin_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String mediaId = getArguments().getString("mediaId");
        final String imagePath = getArguments().getString("imagePath");
        this.mediaId = mediaId;
        this.imagePath = imagePath;

        prepareAsinList();

        final GridView gridView = (GridView) getActivity().findViewById(R.id.gridViewSelectAsins);
        gridView.setAdapter(new SelectAsinAdapter(getActivity(), asins));

        Button button = (Button)getView().findViewById(R.id.selectAsinButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder asinList = new StringBuilder();
                for(int i=0; i<gridView.getChildCount(); i++) {
                    View v = gridView.getChildAt(i);
                    CheckBox c = v.findViewById(R.id.grid_item_checkbox);
                    if(c.isChecked()) {
                        asinList.append(c.getText().toString()).append(",");
                    }
                }
                String saveAsinList = asinList.toString();
                saveAsinList = saveAsinList.substring(0,saveAsinList.length()-1);
                Log.e("saveAsins", saveAsinList);
                new SaveAsins(getContext()).execute(saveAsinList, mediaId, imagePath);
            }
        });
    }

    private void prepareAsinList() {
        asins = new ArrayList<>();
        InputStream inputStream = getActivity().getResources().openRawResource(R.raw.sampleasins);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try{
            String inputLine = "";
            while((inputLine = bufferedReader.readLine() ) != null) {
                String[] splits = inputLine.split(",",-1);
                Asin asin = Asin.builder()
                        .asin(splits[0])
                        .asinTitle(splits[1])
                        .category(splits[2])
                        .imageUrl(splits[3])
                        .build();
                asins.add(asin);
            }
        } catch (Exception e) {}
    }
}
