package com.amazon.mshopbling.ExternalFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazon.mshopbling.AsyncTasks.SaveAsins;
import com.amazon.mshopbling.R;

import java.util.ArrayList;
import java.util.List;

public class SelectAsinFragment extends Fragment {

    String divider = ",";
    String asinList = "";
    String mediaId;
    String imagePath;

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
        TextView tv = (TextView)getView().findViewById(R.id.text);
        tv.setText(mediaId);

        final List<EditText> editTextList= new ArrayList<>();

        EditText editText1 = (EditText)getView().findViewById(R.id.asin1);
        editTextList.add(editText1);
        EditText editText2 = (EditText)getView().findViewById(R.id.asin2);
        editTextList.add(editText2);
        EditText editText3 = (EditText)getView().findViewById(R.id.asin3);
        editTextList.add(editText3);
        EditText editText4 = (EditText)getView().findViewById(R.id.asin4);
        editTextList.add(editText4);
        EditText editText5 = (EditText)getView().findViewById(R.id.asin5);
        editTextList.add(editText5);

        Button button = (Button)getView().findViewById(R.id.submit_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asinList = "";
                for(EditText editText: editTextList) {
                    String value = editText.getText().toString();
                    if(value.length()!=0) {
                        if(asinList.length()==0) {
                            asinList = value;
                        } else {
                            asinList = asinList+divider+value;
                        }
                    }
                }
                new SaveAsins(getContext()).execute(asinList, mediaId, imagePath);
            }
        });
    }
}
