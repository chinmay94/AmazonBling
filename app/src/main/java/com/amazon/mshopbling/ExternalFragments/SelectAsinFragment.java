package com.amazon.mshopbling.ExternalFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
    boolean[] selectionStatus;

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

        selectionStatus = new boolean[asins.size()];

        final GridView gridView = (GridView) getActivity().findViewById(R.id.gridViewSelectAsins);
        gridView.setAdapter(new SelectAsinAdapter(getActivity(), asins, selectionStatus));

        Button button = (Button)getView().findViewById(R.id.selectAsinButton);
        final EditText editText = (EditText) getActivity().findViewById(R.id.editTextSelectAsin);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder asinList = new StringBuilder();
                for(int i=0; i<asins.size(); i++) {
                    if(selectionStatus[i]) {
                        asinList.append(asins.get(i).getAsin()).append(",");
                    }
                }

                try{
                    String splits[] = editText.getText().toString().split(",",-1);
                    for(int i=0; i<splits.length; i++) {
                        if(splits[i].length()>1){
                            asinList.append(splits[i]).append(",");
                        }
                    }
                } catch (Exception e) {}
                String saveAsinList = asinList.toString();
                if(asinList.length()>1) {
                    saveAsinList = saveAsinList.substring(0, saveAsinList.length() - 1);
                    Log.e("saveAsins", saveAsinList);
                    Log.e("saveAsins", "mediaPath:"+mediaId);
                    Log.e("imagePath", "mediaPath:"+imagePath);
                    new SaveAsins(getContext()).execute(saveAsinList, mediaId, imagePath);
                } else {
                    Toast.makeText(getContext(), "Please select atleast one ASIN", Toast.LENGTH_SHORT).show();
                }
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
