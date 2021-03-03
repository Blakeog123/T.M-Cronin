package com.example.tmcronin;

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
import com.example.tmcronin.Prevalent.Prevalent;
import com.example.tmcronin.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription, productName;
    //From Pdisplay to carry over product details to cart
    private String productID = "", state = "Normal";

//from youtube tutorial https://www.youtube.com/watch?v=enyPmr6XhlQ&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=21

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

//Adding product to cart
        addToCartButton = (Button) findViewById(R.id.pd_add_to_cart_button);



        //needed to retrieve product details
        productID = getIntent().getStringExtra("pid");

//        addToCartBtn = (FloatingActionButton) findViewById(R.id.add_product_to_cart_btn);

        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);

        //Retrieving product details using the same Id as above
        getProductDetails(productID);

//code from https://www.youtube.com/watch?v=pyQ_MJ5iI54&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=22
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                //need to add order state info https://www.youtube.com/watch?v=oFRxWXPD1XA&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=28

                if (state.equals("Order Placed") || state.equals("Order Shipped"))
                {
                    Toast.makeText(ProductDetailsActivity.this, "You can order more products, once your order is shipped or confirmed.", Toast.LENGTH_LONG).show();

                }
                else
                    {
                        addingToCartList();
                    }
            }
        });
    }
//METHOD for vid 28 : trying to fix error in app crashing https://www.youtube.com/watch?v=oFRxWXPD1XA&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=28


    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();
    }

    private void addingToCartList()
    {
        //using dates and times to recieve product info
        //get the time when theyre purchasing product https://www.youtube.com/watch?v=pyQ_MJ5iI54&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=22
String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        //use of hashmaps to store the date through the pids . https://www.youtube.com/watch?v=pyQ_MJ5iI54&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=22
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productName.getText().toString());
        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount", "");
//so user can pass products
        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    //so admin can identify users and products https://www.youtube.com/watch?v=pyQ_MJ5iI54&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=22
                    cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                            .child("Products").child(productID)
                            .updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful());
                                    Toast.makeText(ProductDetailsActivity.this, "Added to Cart List." , Toast.LENGTH_SHORT).show();
                                    //check cart list on product page
                                    Intent intent = new Intent(ProductDetailsActivity.this, PdisplayActivity.class);
                                    startActivity(intent);


                                }
                            });

            }
        }
    });


    }

    //method created to use a database reference to retrieve data
    private void getProductDetails(String productID)
    {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
//getting specific product through product ID
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Products products = dataSnapshot.getValue(Products.class);
//setting the details we want to retrieve
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CheckOrderState()

    {

        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    //see if orders have already been placed
                    //https://www.youtube.com/watch?v=oFRxWXPD1XA&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=28
                    String shippingState = dataSnapshot.child("state").getValue().toString();


                    if (shippingState.equals("shipped"))
                    {
                        state = "Orders Shipped";

                    }

                    else if (shippingState.equals("not shipped"))

                    {
                        state = "Orders Placed";

                    }

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}


