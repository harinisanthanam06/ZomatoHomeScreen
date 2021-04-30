package com.example.britesy;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.britesy.R;

public class FragmentB extends Fragment  {
    TextView text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        text=getActivity().findViewById(R.id.text);
    }
    public void changeData(int i)
    {
        Resources res=getResources();
        String [] descriptions= res.getStringArray(R.array.descriptions);
        text.setText(descriptions[i]);
    }



}
