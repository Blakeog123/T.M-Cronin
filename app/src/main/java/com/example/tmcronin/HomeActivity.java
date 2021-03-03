package com.example.tmcronin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }


    //Btnfeedback - is your sizes
    public void feedbackbtn(View view) {
        Toast.makeText(this, "Sizes Button Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SizeActivity.class);
        startActivity(intent);
    }



//Button to take to Product display page
    public void profilebtn(View view) {
        Toast.makeText(this, "Product Button Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PdisplayActivity.class);
        startActivity(intent);
    }
// Button to the settings page
    public void settingsbtn(View view) {
        Toast.makeText(this, "Settings Button Click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SettinsActivity.class);
        startActivity(intent);
    }
//Logout button
    public void logoutbtn(View view) {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}