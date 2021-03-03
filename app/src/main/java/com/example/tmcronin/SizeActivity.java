package com.example.tmcronin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class SizeActivity extends AppCompatActivity {
//https://www.youtube.com/watch?v=27SGKu82nMQ

    // for onboarding page
    public static ViewPager viewPager;
    SlideViewPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);

        viewPager=findViewById(R.id.viewpager);
        adapter=new SlideViewPageAdapter(this);
        viewPager.setAdapter(adapter);

    }
}