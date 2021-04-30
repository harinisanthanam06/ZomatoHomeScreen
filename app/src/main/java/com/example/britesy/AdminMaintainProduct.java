package com.example.britesy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProduct extends AppCompatActivity {

    private Button applychanges,deleteart;
    private EditText mname,mprice,mdescription;
    private ImageView mimageview;

    private String productID =" ";
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);

        productID = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        applychanges = findViewById(R.id.art_button);
        mname = findViewById(R.id.maintain_name);
        mprice = findViewById(R.id.maintain_price);
        mdescription = findViewById(R.id.maintain_description);
        mimageview = findViewById(R.id.maintain_image);
        deleteart=findViewById(R.id.delete_art);

        displaySpecificProductInfo();

        applychanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChange();
            }
        });

        deleteart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletethisArt();
            }
        });


    }

    private void deletethisArt()
    {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(AdminMaintainProduct.this,AdminCategoryActivity.class);
                startActivity(intent);
                finish();


                Toast.makeText(AdminMaintainProduct.this,"The art is deleted successfully.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyChange() {
        String AName = mname.getText().toString();
        String APrice = mprice.getText().toString();
        String Adescription = mdescription.getText().toString();

        if(mname.equals(""))
        {
            Toast.makeText(this,"Change the Art name.",Toast.LENGTH_SHORT).show();
        }
        else if(mprice.equals(""))
        {
            Toast.makeText(this,"Change the Art name.",Toast.LENGTH_SHORT).show();
        }
        else if(mdescription.equals(""))
        {
            Toast.makeText(this,"Change the Art description.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", Adescription);
            productMap.put("price", APrice);
            productMap.put("pname",AName);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {

                        Intent intent = new Intent(AdminMaintainProduct.this,AdminCategoryActivity.class);
                        startActivity(intent);

                        Toast.makeText(AdminMaintainProduct.this,"Changes applied Successfully.",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }




    }

    private void displaySpecificProductInfo() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String ANname = snapshot.child("pname").getValue().toString();
                    String APrice = snapshot.child("price").getValue().toString();
                    String Adescription = snapshot.child("description").getValue().toString();
                    String Aimage = snapshot.child("image").getValue().toString();

                    mname.setText(ANname);
                    mprice.setText(APrice);
                    mdescription.setText(Adescription);
                    Picasso.get().load(Aimage).into(mimageview);





                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}