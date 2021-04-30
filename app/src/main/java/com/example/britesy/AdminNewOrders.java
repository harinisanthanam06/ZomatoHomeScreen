package com.example.britesy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.britesy.Model.AdminOrders;
import com.example.britesy.Prevalent.Prevalent;
import com.example.britesy.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminNewOrders extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);





        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        ordersList = findViewById(R.id.order_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));

    }







    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>().setQuery(ordersRef,AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder adminOrdersViewHolder, final int position, @NonNull AdminOrders adminOrders) {
              adminOrdersViewHolder.username.setText("Name:"+adminOrders.getFullname());
                adminOrdersViewHolder.usermobnumber.setText("Mobile number:"+adminOrders.getMobilenumber());
                adminOrdersViewHolder.usertotalprice.setText("Total Amount:"+adminOrders.getTotalAmount());
                adminOrdersViewHolder.userdateTime.setText("order at:"+adminOrders.getDate()+" "+adminOrders.getTime());
                adminOrdersViewHolder.usershippingaddress.setText("address:"+adminOrders.getAddress());
                adminOrdersViewHolder.usercity.setText("city:"+adminOrders.getCity());
                adminOrdersViewHolder.userstate.setText("state:"+adminOrders.getState());


                   adminOrdersViewHolder.showOrdersbtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           String uID = getRef(position).getKey();


                           Intent intent = new Intent(AdminNewOrders.this,showarts.class);
                           intent.putExtra("uid",uID);
                           startActivity(intent);
                       }
                   });

                   adminOrdersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           CharSequence options[] = new CharSequence[]
                                   {
                                           "YES",
                                           "NO"

                                   };
                           AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrders.this);
                           builder.setTitle("Have you shipped this arts to customers ?");

                           builder.setItems(options, new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int i) {
                                   if(i==0)
                                   {
                                       String uID = getRef(position).getKey();

                                       RemoveOrder(uID);


                                   }
                                   else
                                   {
                                       finish();
                                   }

                               }
                           });
                           builder.show();
                       }
                   });

            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlayout, parent, false);
                return new  AdminOrdersViewHolder(view);
            }
        };


        ordersList.setAdapter(adapter);
        adapter.startListening();
    }




    public static  class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username,usermobnumber,usertotalprice,userdateTime,usershippingaddress,usercity,userstate;
        public Button showOrdersbtn;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_name);
            usermobnumber = itemView.findViewById(R.id.phone_number);
            usertotalprice = itemView.findViewById(R.id.art_price);
            userdateTime = itemView.findViewById(R.id.date);
            usershippingaddress = itemView.findViewById(R.id.user_address);
            usercity = itemView.findViewById(R.id.user_city);
            userstate = itemView.findViewById(R.id.user_state);
            showOrdersbtn = itemView.findViewById(R.id.show_arts);
        }
    }
    private void RemoveOrder(String uID) {
        ordersRef.child(uID).removeValue();

    }


    }

