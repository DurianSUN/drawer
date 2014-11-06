package com.ifast.menuDrawer.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ifast.menuDrawer.R;
import com.ifast.menuDrawer.activity.SelectLanguagesActivity;

public class SettingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        Button language = (Button) rootView.findViewById(R.id.languages);
        language.setText(getResources().getString(R.string.languages));
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button click.", "Button clicked.");
                Intent intent = new Intent(rootView.getContext(), SelectLanguagesActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
