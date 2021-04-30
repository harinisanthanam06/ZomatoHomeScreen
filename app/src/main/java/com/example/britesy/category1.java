package com.example.britesy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.britesy.Model.Products;
import com.example.britesy.Model.categories;
import com.example.britesy.Prevalent.Prevalent;
import com.example.britesy.ViewHolder.CategoryViewHolder;
import com.example.britesy.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;





public class category1 extends AppCompatActivity
{

    private DatabaseReference cateRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category1);


        cateRef = FirebaseDatabase.getInstance().getReference().child("categories");











        recyclerView = findViewById(R.id.recycler_one);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<categories> options =
                new FirebaseRecyclerOptions.Builder<categories>()
                        .setQuery(cateRef, categories.class)
                        .build();


        FirebaseRecyclerAdapter<categories, CategoryViewHolder> adapter =
                new FirebaseRecyclerAdapter<categories, CategoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i, @NonNull categories model) {
                        categoryViewHolder.txtProductName.setText(model.getPname());
                        categoryViewHolder.txtProductDescription.setText(model.getDescription());
                        categoryViewHolder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(categoryViewHolder.imageView);

                        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(category1.this,ProductDetails.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });


                    }



                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_category, parent, false);
                        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
                        return categoryViewHolder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }













    }
