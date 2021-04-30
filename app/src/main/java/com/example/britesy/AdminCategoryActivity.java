package com.example.britesy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView sculpture,MyCat;
    private Button maintainproduct,checkOrders,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        sculpture = (ImageView) findViewById(R.id.cat_1);
        MyCat=(ImageView)findViewById(R.id.cat_2);
        maintainproduct=(Button)findViewById(R.id.maintain);
        checkOrders=(Button)findViewById(R.id.new_orders);
        logout=(Button)findViewById(R.id.log_out);

        maintainproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,HomeActivity.class);
                intent.putExtra("Admins","Admins");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        checkOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this,AdminNewOrders.class);
                startActivity(intent);
            }
        });

        sculpture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProduct.class);
                intent.putExtra("category", "sculpture");
                startActivity(intent);
            }
        });
        MyCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminCategoryProduct.class);
                intent.putExtra("cat1", "MyCat");
                startActivity(intent);
            }
        });
    }
}