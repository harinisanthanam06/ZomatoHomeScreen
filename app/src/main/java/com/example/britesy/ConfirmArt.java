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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmArt extends AppCompatActivity {

    private TextInputLayout full_Name,mob,cty,address,pin,state,country;
    private Button button_add,btn2;


    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_art);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this,"Total Price = " + totalAmount, Toast.LENGTH_SHORT).show();


        full_Name = (TextInputLayout) findViewById(R.id.fullname);
        mob = (TextInputLayout) findViewById(R.id.mob_number);
        cty = (TextInputLayout) findViewById(R.id.city_add);
        address = (TextInputLayout) findViewById(R.id.address);
        pin = (TextInputLayout) findViewById(R.id.pincode);
        state = (TextInputLayout) findViewById(R.id.state);
        country =(TextInputLayout)findViewById(R.id.country);

        button_add=(Button)findViewById(R.id.add_address_btn);
        btn2=(Button)findViewById(R.id.payment_btn);


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmArt.this,RazorPay.class);
                startActivity(intent);
            }
        });


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();

            }
        });

    }

    private void Check()
    {
        if (TextUtils.isEmpty(full_Name.getEditText().getText().toString()))
        {
            Toast.makeText(this,"Please provide your full name.",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(mob.getEditText().getText().toString()))
        {
            Toast.makeText(this,"Please provide your mobile number.",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address.getEditText().getText().toString()))
        {
            Toast.makeText(this,"Please provide your address.",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cty.getEditText().getText().toString()))
        {
            Toast.makeText(this,"Please provide your city name.",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pin.getEditText().getText().toString()))
        {
            Toast.makeText(this,"Please provide your pincode.",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(state.getEditText().getText().toString()))
        {
            Toast.makeText(this,"Please provide your state name.",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(country.getEditText().getText().toString()))
        {
            Toast.makeText(this,"Please provide your country name.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            CondirmOrder();
        }
    }

    private void CondirmOrder()
    {
        final String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());
        HashMap<String,Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",totalAmount);
        ordersMap.put("Fullname",full_Name.getEditText().getText().toString());
        ordersMap.put("Mobilenumber",mob.getEditText().getText().toString());
        ordersMap.put("address",address.getEditText().getText().toString());
        ordersMap.put("city",cty.getEditText().getText().toString());
        ordersMap.put("state",state.getEditText().getText().toString());
        ordersMap.put("country",country.getEditText().getText().toString());
        ordersMap.put("pincode",pin.getEditText().getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("shippingState","not shipped");


       orderRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(ConfirmArt.this,"Your final order has been placed successfully",Toast.LENGTH_SHORT).show();

                           }
                        }
                    });

                }
            }
        });
    }

}