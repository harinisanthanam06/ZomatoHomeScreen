package com.example.britesy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

import com.example.britesy.Interface.communicator;


public class ArtistActivity extends AppCompatActivity implements communicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

    }
    public void respond(int i) {


        FragmentManager manager = getSupportFragmentManager();
        FragmentB f2 = (FragmentB) manager.findFragmentById(R.id.fragment2);
        f2.changeData(i);

    }

}