package com.example.tmcronin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmcronin.Prevalent.Prevalent;
import com.example.tmcronin.ViewHolder.CartViewHolder;
import com.example.tmcronin.model.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartActivity extends AppCompatActivity
{
    //https://www.youtube.com/watch?v=l-DasTV6sSQ&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=23
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    //txtMsg1 is for displaying the final order confirmation on the cart https://www.youtube.com/watch?v=oFRxWXPD1XA&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=28
    private TextView txtTotalAmount, txtMsg1;

    //string type to store total price of orders https://www.youtube.com/watch?v=9dcDo3OyZyg&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=26
    private int overTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //https://www.youtube.com/watch?v=l-DasTV6sSQ&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=23
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);
//For displaying final order message whe confirmed https://www.youtube.com/watch?v=oFRxWXPD1XA&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=28
        txtMsg1 = (TextView) findViewById(R.id.msg1);

        NextProcessBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //To display on the top of the screen the Total Price https://www.youtube.com/watch?v=9dcDo3OyZyg&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=26
                txtTotalAmount.setText("Total Price = €" +String.valueOf(overTotalPrice));
                //for next button https://www.youtube.com/watch?v=9dcDo3OyZyg&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=26
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();

            }
        });


    }
    //Display product items added to cart https://www.youtube.com/watch?v=giHNsmE8-ug&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=24


    @Override
    protected void onStart()
    {
        super.onStart();
// must pass the message.
        CheckOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
//referencing database to get the user view by getting the current online user and products they used. https://www.youtube.com/watch?v=giHNsmE8-ug&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=24
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View")
                                .child(Prevalent.currentOnlineUser.getPhone())
                                .child("Products"), Cart.class)
                        .build();
//needs a new class creation in View holder directory to be able to access info for Cart page. https://www.youtube.com/watch?v=giHNsmE8-ug&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=24
        //Last 7 Minutes of video
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
            {

                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("Price = €" + model.getPrice());
                holder.txtProductName.setText(model.getPname());

                //get the product and quantity of each, x quantity by price through variableshttps://www.youtube.com/watch?v=9dcDo3OyZyg&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=26
                int oneTypeProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                //adding to total price
                overTotalPrice = overTotalPrice + oneTypeProductTPrice;


                //To edit and and remove details from cart https://www.youtube.com/watch?v=oiFPMhGTR2c&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=25
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //This is to edit the details in the cart
                                if (i == 0) {
                                    Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    //details from the product list activity and the cart list activity are needed and obtained using the pids minute 4 https://www.youtube.com/watch?v=oiFPMhGTR2c&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=25
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                //This is to remove details from cart list
                                if (i == 1)
                                {
                                    //reference to cart list already created
                                    cartListRef.child("User View")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(CartActivity.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                                                        //if item removed successfuly, brings back to Product display activity so they can browse more products
                                                        Intent intent = new Intent(CartActivity.this, PdisplayActivity.class);
                                                        startActivity(intent);
                                                    }

                                                }
                                            });

                                }
                            }
                        });
                        //25
                        builder.show();

                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    //need to create a method to check if final order is already placed
    private void CheckOrderState()

    {

        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

         ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    //see if orders have already been placed
                    //https://www.youtube.com/watch?v=oFRxWXPD1XA&list=PLxefhmF0pcPlqmH_VfWneUjfuqhreUz-O&index=28
                    String shippingState = dataSnapshot.child("State").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                        txtTotalAmount.setText("Dear" + userName + "\n order is shipped successfully.");
                        recyclerView.setVisibility(View.GONE);
                        //makes the message visible
                        txtMsg1.setVisibility(View.VISIBLE);
                        //if state = shipped, must pass message
                        txtMsg1.setText("Congratulations, your final order has been shipped successfully. Soon you will recieve your order at your home address. ");
                        //make button invisible so it can't be pressed.
                        NextProcessBtn.setVisibility(View.GONE);

                    }

                    else if (shippingState.equals("not shipped"))

                    {
                        txtTotalAmount.setText("Order has not been delivered");
                        recyclerView.setVisibility(View.GONE);
                        //makes the message visible
                        txtMsg1.setVisibility(View.VISIBLE);
                        //make button invisible so it can't be pressed.
                        NextProcessBtn.setVisibility(View.GONE);


                    }

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}