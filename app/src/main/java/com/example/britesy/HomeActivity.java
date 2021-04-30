package com.example.britesy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.britesy.Model.Products;
import com.example.britesy.Prevalent.Prevalent;
import com.example.britesy.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageSlider imageSlider;

    private BottomNavigationView bottomBar;

    private  String type = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         imageSlider = (ImageSlider) findViewById(R.id.id);
        List<SlideModel> slideModels= new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.unnamed,"NEWS"));
        slideModels.add(new SlideModel(R.drawable.th1,"LATEST UPDATES ON WORSHOPS AND EXHIBITIONS"));
        slideModels.add(new SlideModel(R.drawable.th1,"NEWS"));
       imageSlider.setImageList(slideModels,true);
       imageSlider.setClickable(true);

       imageSlider.setItemClickListener(new ItemClickListener() {
           @Override
           public void onItemSelected(int i) {
               if(i==0){
                   Intent intent = new Intent(HomeActivity.this,webview1.class);
                   startActivity(intent);
               }
               else if
               (i==1){
                   Intent intent = new Intent(HomeActivity.this,webview2.class);
                   startActivity(intent);
               }
           }
       });



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            type = getIntent().getExtras().get("Admins").toString();
        }


        bottomBar=findViewById(R.id.bottomBar);

        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.home)
                {

                }
                else if (id == R.id.search)
                {
                    Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
                    startActivity(intent);

                }
                else if (id == R.id.shop)
                {
                    Intent intent = new Intent(HomeActivity.this,CartActivity.class);
                    startActivity(intent);

                }


                return true;
            }
        });




        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        if(!type.equals("Admins"))
        {
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
        }


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }



    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(type.equals("Admins"))
                                {
                                    Intent intent = new Intent(HomeActivity.this,AdminMaintainProduct.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent = new Intent(HomeActivity.this,ProductDetails.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);

                                }

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,CartActivity.class);
                startActivity(intent);
            }

        }
        else if (id == R.id.search_input)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
                startActivity(intent);
            }


        }
        else if (id == R.id.nav_categories)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,webview1.class);
                startActivity(intent);
            }


        }
        else if (id == R.id.artists)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,ArtistActivity.class);
                startActivity(intent);

            }

        }

        else if (id == R.id.nav_logout)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }


        }
        else if (id == R.id.nav_login)
        {
            Intent intent = new Intent(HomeActivity.this,login.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_register)
        {
            Intent intent = new Intent(HomeActivity.this,Register.class);
            startActivity(intent);

        }

        else if(id==R.id.nav_about)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,aboutus.class);
                startActivity(intent);
            }



        }

        else if(id==R.id.nav_contact)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,ContactUs.class);
                startActivity(intent);
            }

        }
        else if(id==R.id.privacy_policy)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,PrivacyPolicy.class);
                startActivity(intent);

            }

        }
        else if(id==R.id.nav_profile)
        {
        }
        else if(id==R.id.nav_feedback)
        {
            if(!type.equals("Admins"))
            {
                Intent intent = new Intent(HomeActivity.this,feedback.class);
                startActivity(intent);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}