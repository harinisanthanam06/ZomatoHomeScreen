package com.example.britesy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RazorPay extends AppCompatActivity implements PaymentResultListener {
    Button btpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay);

        btpay = findViewById(R.id.bt_pay);
        String sAmount = "100";
        int amount = Math.round(Float.parseFloat(sAmount)*100);
        btpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout checkout = new Checkout();

                checkout.setKeyID("rzp_test_fHCsD5kpsx20Zd");
                checkout.setImage(R.drawable.razorp);

                JSONObject object = new JSONObject();

                try {
                    object.put("name","Harini Santhanam");
                    object.put("description","Test Payment");
                    object.put("theme.color","#074A97");
                    object.put("currency","INR");
                    object.put("amount",amount);
                    object.put("prefill.contact","9003460019");
                    object.put("prefill.email","harinisanthanam0@gmail.com");
                    checkout.open(RazorPay.this,object);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

    }
}