package com.example.britesy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.britesy.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class shipping extends AppCompatActivity {

    private EditText shipping;
    private Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        shipping = (EditText)findViewById(R.id.ship);
        send=(Button)findViewById(R.id.show);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    private void send() {
        String ShippingState = shipping.getText().toString();

        if (TextUtils.isEmpty(ShippingState))
        {
            Toast.makeText(this,"Please type your name..",Toast.LENGTH_SHORT).show();
        }
        else
        {
            shipp(ShippingState);
        }
    }

    private void shipp(String ShippingState) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Orders").child(Prevalent.currentOnlineUser.getPhone()).exists()))
                {
                    HashMap<String, Object> ordersMap = new HashMap<>();
                    ordersMap.put("ShippingState", ShippingState);

                    RootRef.child("Orders").child(Prevalent.currentOnlineUser.getPhone()).updateChildren(ordersMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(shipping.this,"Congratulations,your account has been created successfully.",Toast.LENGTH_SHORT).show();


                                    }
                                    else
                                    {

                                        Toast.makeText(shipping.this, "Network error:please try again after some time ...",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else
                {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}