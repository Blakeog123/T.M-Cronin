package com.example.tmcronin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tmcronin.ViewHolder.ProductViewHolder;
import com.example.tmcronin.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PdisplayActivity extends AppCompatActivity
{
//variable string types
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

//displaying products https://www.youtube.com/watch?v=enyPmr6XhlQ&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=21


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdisplay);


//Recycler view declaration and Database reference for product information. https://www.youtube.com/watch?v=enyPmr6XhlQ&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=21
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();
//information carried over from product information to singular quantity selection page
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);


                        //code needed for displaying product details to cart. From youtube tutorials https://www.youtube.com/watch?v=enyPmr6XhlQ&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=21.
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            //getting product Id
                            public void onClick(View view) {
                                Intent intent = new Intent(PdisplayActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                        });
                        //floating button - 11th minute on https://www.youtube.com/watch?v=giHNsmE8-ug&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=24
                        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //11.25 video 24 on how to get to the cart page using a floating button

                                Intent intent = new Intent(PdisplayActivity.this, CartActivity.class);
                                startActivity(intent);
                            }
                        });


                        FloatingActionButton set = (FloatingActionButton) findViewById(R.id.set);
                        set.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //11.25 video 24 on how to get to the cart page using a floating button

                                Intent intent = new Intent(PdisplayActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        });
                    }






                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }





    }
