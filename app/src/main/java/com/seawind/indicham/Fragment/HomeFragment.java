package com.seawind.indicham.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seawind.indicham.ProductActivity;
import com.seawind.indicham.R;
import com.seawind.indicham.SliderAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewPager slideViewPager;
    private static int currentPage = 0;
    private int[] images = new int[]{R.drawable.image1,R.drawable.image2,R.drawable.image3,
                                R.drawable.image4,R.drawable.image5};
    private Handler mHandler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }
    void init(View v){
        slideViewPager = v.findViewById(R.id.slideViewPager);
        CircleIndicator slideIndicator = v.findViewById(R.id.slideIndicator);
        CardView card_pesticides = v.findViewById(R.id.card_pesticides);
        CardView card_fungicides = v.findViewById(R.id.card_fungicides);
        CardView card_weedicides = v.findViewById(R.id.card_weedicides);


        card_pesticides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductActivity.class));
            }
        });
        card_fungicides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductActivity.class));
            }
        });
        card_weedicides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductActivity.class));
            }
        });


        ArrayList<Integer> sliderImageArrayList = new ArrayList<>();
        for (int i=0;i<images.length;i++){
            sliderImageArrayList.add(images[i]);
        }

        slideViewPager.setAdapter(new SliderAdapter(getContext(), sliderImageArrayList));
        slideIndicator.setViewPager(slideViewPager);
        slideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {currentPage = position;}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(runnable,1500);
    }
    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, 1500);
            slideViewPager.setCurrentItem(currentPage,false);
            currentPage++;
            if(slideViewPager.getAdapter().getCount()==currentPage)
                currentPage=0;
        }
    };


}
