package com.seawind.indicham.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seawind.indicham.CategoryModel;
import com.seawind.indicham.ProductActivity;
import com.seawind.indicham.ProductDetail;
import com.seawind.indicham.ProductModel;
import com.seawind.indicham.R;
import com.seawind.indicham.SliderAdapter;
import com.seawind.indicham.Util.RecyclerTouchListener;
import com.seawind.indicham.WebAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView category_list;
    private ViewPager slideViewPager;
    private static int currentPage = 0;
    private int[] images = new int[]{R.drawable.image1,R.drawable.image2,R.drawable.image3,
                                R.drawable.image4,R.drawable.image5};
    private Handler mHandler = new Handler();
    private Category_Adpter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }
    void init(View v){
        slideViewPager = v.findViewById(R.id.slideViewPager);
        CircleIndicator slideIndicator = v.findViewById(R.id.slideIndicator);
        category_list = v.findViewById(R.id.recyclewview);
        category_list.setHasFixedSize(true);
        category_list.setNestedScrollingEnabled(false);
        int numberOfColumns = 2;
        category_list.setHasFixedSize(true);
        category_list.setLayoutManager(new GridLayoutManager(getActivity(),numberOfColumns));
        category_list.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), category_list, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onLongClick(View child, int childPosition) {}
            @Override
            public void onClick(View child, int childPosition) {
                Intent intent = new Intent(getActivity(),ProductActivity.class);
                CategoryModel model = adapter.getItem(childPosition);
                intent.putExtra("category_id",model.getCategory_id());
                startActivity(intent);
            }
        }));
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

        getCategory();

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

    private void getCategory(){
        ProgressDialog pd = ProgressDialog.show(getActivity(),"","Loading...!");
        Call<ResponseBody> list = WebAccess.getService().Category_list("category_list");
        list.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String strRes = response.body().string();
                        ArrayList<CategoryModel> list = new ArrayList<>();
                        JSONObject obj = new JSONObject(strRes);
                        JSONArray array = obj.getJSONArray("mainCategory");
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            CategoryModel model = new CategoryModel();
                            model.setCategory_id(jsonObject.getString("category_id"));
                            model.setCategory_name(jsonObject.getString("category_name"));
                            model.setCategory_image(jsonObject.getString("category_image"));
                            list.add(model);
                        }
                        adapter = new Category_Adpter(getActivity(),list);
                        category_list.setAdapter(adapter);
                    } catch (IOException|JSONException e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }
                }
                pd.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
    public class Category_Adpter extends RecyclerView.Adapter<Category_Adpter.ViewHolder> {
        private Context context;
        private LayoutInflater mInflater;
        private ArrayList<CategoryModel> list;
        public Category_Adpter(Context context, ArrayList<CategoryModel> models){
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
            this.list = models;
        }
        public CategoryModel getItem(int pos){
            return this.list.get(pos);
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itetview = mInflater.inflate(R.layout.category_listrow,parent,false);
            return new ViewHolder(itetview);
        }
        @Override
        public void onBindViewHolder(Category_Adpter.ViewHolder holder, int position) {
            CategoryModel model = getItem(position);
            Glide.with(context).load(model.getCategory_image()).into(holder.cat_img);
            holder.txt_c_name.setText(model.getCategory_name());
        }
        @Override
        public int getItemCount() {
            return this.list.size();
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView cat_img;
            private TextView txt_c_name;
            public ViewHolder(View itemView) {
                super(itemView);
                cat_img = itemView.findViewById(R.id.cat_img);
                txt_c_name = itemView.findViewById(R.id.txt_c_name);
            }
        }

    }
}
