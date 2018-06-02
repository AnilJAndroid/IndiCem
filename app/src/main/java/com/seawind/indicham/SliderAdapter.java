package com.seawind.indicham;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;

/**
 * Created by admin on 28-May-18.
 */

public class SliderAdapter extends PagerAdapter {

    private ArrayList<Integer> integerArrayList;
    private LayoutInflater layoutInflater;
    private Context context;

    public SliderAdapter(Context context, ArrayList<Integer> integerArrayList) {
        this.context = context;
        this.integerArrayList = integerArrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return integerArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayoutView = layoutInflater.inflate(R.layout.layout_slider_item, view, false);
        imageLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProductActivity.class));
            }
        });
        ImageView imageView = (ImageView) imageLayoutView.findViewById(R.id.sliderImageView);
        imageView.setImageResource(integerArrayList.get(position));
        view.addView(imageLayoutView, 0);
        return imageLayoutView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
