package com.example.britesy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.britesy.Interface.communicator;



public class FragmentA extends Fragment implements AdapterView.OnItemClickListener {
    ListView list;
    communicator comm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (communicator) getActivity();

        list=getActivity().findViewById(R.id.listView);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(getActivity(),R.array.titles, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        comm.respond(position);

    }

}
