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

public class feedback extends AppCompatActivity {
    private EditText personname,email,phoneno,msg;
    private Button sent;
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        personname = findViewById(R.id.msg_Name);
        email = findViewById(R.id.msg_EmailAddress);
        phoneno = findViewById(R.id.msg_Phone);
        msg = findViewById(R.id.message);
        sent = findViewById(R.id.msgbtn);
        loading = new ProgressDialog(this);

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sent();
            }
        });
    }

    private void sent() {
        String Name = personname.getText().toString();
        String Email  = email.getText().toString();
        String PhoneNumber = phoneno.getText().toString();
        String Message = msg.getText().toString();
        if (TextUtils.isEmpty(Name))
        {
            Toast.makeText(this,"Please type your name..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PhoneNumber))
        {
            Toast.makeText(this,"Please type your phone number..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Email))
        {
            Toast.makeText(this,"Please type your email..",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Message))
        {
            Toast.makeText(this,"Please type your feedback..",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading.setTitle("Sending feedback");
            loading.setMessage("Please wait,while we are sending the feedback.");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            ValidatephoneNumber(Name,PhoneNumber,Email,Message);
        }
    }

    private void ValidatephoneNumber(String Name, String PhoneNumber, String Email,String Message) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Feedback").child(PhoneNumber).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("PhoneNumber", PhoneNumber);
                    userdataMap.put("Email",Email);
                    userdataMap.put("Name", Name);
                    userdataMap.put("Message",Message);

                    RootRef.child("Feedback").child(PhoneNumber).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(feedback.this,"Congratulations,your feedback has been sent  successfully.",Toast.LENGTH_SHORT).show();
                                        loading.dismiss();
                                        Intent intent = new Intent(feedback.this,HomeActivity.class);
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                        loading.dismiss();
                                        Toast.makeText(feedback.this, "Network error:please try again after some time ...",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
                else
                {
                    Toast.makeText(feedback.this,"This"+PhoneNumber+ " already exists. ",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    Toast.makeText(feedback.this," Please try again using another phone number ",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(feedback.this,HomeActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}