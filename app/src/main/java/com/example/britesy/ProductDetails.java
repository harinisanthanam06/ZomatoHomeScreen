package com.example.britesy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.britesy.Model.Products;
import com.example.britesy.Model.categories;
import com.example.britesy.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {
    private ImageView image1;
    private TextView product1, product2, product3;
    private Button btn1;
    private String productID = "";

    private ElegantNumberButton bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");
        image1 = (ImageView) findViewById(R.id.product_details);
        product1 = (TextView) findViewById(R.id.product_name_details);
        product2 = (TextView) findViewById(R.id.product_description_details);
        product3 = (TextView) findViewById(R.id.product_price_details);
        btn1 = (Button) findViewById(R.id.button);
        bt = (ElegantNumberButton) findViewById(R.id.elegant);

        getProductDetails(productID);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void addingToCartList() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", product1.getText().toString());
        cartMap.put("price", product3.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("discount", "");
        cartMap.put("quantity", bt.getNumber());

        cartListRef.child("Admin View").child(Prevalent.currentOnlineUser
                .getPhone()).child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) ;
                        {
                            cartListRef.child("User View").child(Prevalent.currentOnlineUser
                                    .getPhone()).child("Products").child(productID)
                                    .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProductDetails.this, "Added to Cart List", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProductDetails.this, HomeActivity.class);
                                        startActivity(intent);


                                    }
                                }
                            });

                        }

                    }
                });


    }

    private void getProductDetails(String productID) {
        DatabaseReference productsref = FirebaseDatabase.getInstance().getReference().child("Products");
        productsref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products products = snapshot.getValue(Products.class);
                    product1.setText(products.getPname());
                    product3.setText(products.getPrice());
                    product2.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(image1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference cateref = FirebaseDatabase.getInstance().getReference().child("categories");
        cateref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    categories c = snapshot.getValue(categories.class);
                    product1.setText(c.getPname());
                    product2.setText(c.getPrice());
                    product3.setText(c.getDescription());
                    Picasso.get().load(c.getImage()).into(image1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }}




