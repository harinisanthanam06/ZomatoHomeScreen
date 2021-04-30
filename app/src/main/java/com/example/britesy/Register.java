package com.example.britesy;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private EditText username,phn,mpassword;
    private Button register;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=(EditText) findViewById(R.id.register_username_input);
        phn=(EditText) findViewById(R.id.register_phone_number_input);
        mpassword=(EditText) findViewById(R.id.register_password_input);
        register=(Button) findViewById(R.id.register_btn);
        loadingBar = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String name = username.getText().toString();
        String phone = phn.getText().toString();
        String password = mpassword.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please type your name..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please type your phone number..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please type your password..",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait,while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            
            ValidatephoneNumber(name,phone,password);
    }
}

    private void ValidatephoneNumber(String name, String phone, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("phone", phone);
                    userdataMap.put("password",password);
                    userdataMap.put("name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(Register.this,"Congratulations,your account has been created successfully.",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(Register.this,login.class);
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(Register.this, "Network error:please try again after some time ...",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else
                {
                    Toast.makeText(Register.this,"This"+phone+ " already exists. ",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Register.this," Please try again using another phone number ",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }