package com.wemsuser.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wemsuser.app.Activity.LoginActivity;
import com.wemsuser.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {
    TextView textView;


    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_three, container, false);

        return v;
    }


}
