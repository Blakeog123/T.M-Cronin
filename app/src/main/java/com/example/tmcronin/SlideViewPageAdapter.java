package com.example.tmcronin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
//all code from youtube tutorial https://www.youtube.com/watch?v=27SGKu82nMQ
public class SlideViewPageAdapter extends PagerAdapter {


    Context ctx;

    public SlideViewPageAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater= (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);

        View view=layoutInflater.inflate(R.layout.slide_screen,container, false);

//All data needed to retrieve from first slide screen
        ImageView logo=view.findViewById(R.id.logo);
        ImageView ind1=view.findViewById(R.id.ind1);
        ImageView ind2=view.findViewById(R.id.ind2);
        ImageView ind3=view.findViewById(R.id.ind3);

        TextView title=view.findViewById(R.id.title);
        TextView desc=view.findViewById(R.id.desc);



        ImageView before=view.findViewById(R.id.before);
        ImageView next=view.findViewById(R.id.next);
        Button btnGetStarted=view.findViewById(R.id.btnGetStarted);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ctx,PdisplayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SizeActivity.viewPager.setCurrentItem(position+1);

            }
        });
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SizeActivity.viewPager.setCurrentItem(position-1);

            }
        });



        switch (position)
        {
            //https://www.youtube.com/watch?v=27SGKu82nMQ
            //put ind2 and ind3 the wrong way around. cases forchanging selected view on page
            case 0:
                logo.setImageResource(R.drawable.uni);
                ind1.setImageResource(R.drawable.selected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("School Uniforms");
                desc.setText("Fit: True to size. Order usual size. /n" +
                        "View our Ranges");
                before.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                break;
            case 1:
                logo.setImageResource(R.drawable.logo1);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.unselected);
                ind3.setImageResource(R.drawable.selected);

                title.setText("Macroom GAA Kits");
                desc.setText("Fit: True to size. Order usual size. /n" +
                        "Kids Sizes Available.");
                before.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 2:
                logo.setImageResource(R.drawable.casual);
                ind1.setImageResource(R.drawable.unselected);
                ind2.setImageResource(R.drawable.selected);
                ind3.setImageResource(R.drawable.unselected);

                title.setText("Casual Clothing");
                desc.setText("Fit: True to size. Order usual size.");
                before.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                break;
        }

//access everything created in slide screen
        container.addView(view);
        return view;



    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
