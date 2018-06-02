package com.seawind.indicham.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seawind.indicham.ProductModel;
import com.seawind.indicham.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 28-May-18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<ProductModel> list;

    public CartAdapter(Context context, ArrayList<ProductModel> models){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = models;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itetview = mInflater.inflate(R.layout.cart_row,parent,false);
        return new ViewHolder(itetview);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.prod_title.setText(model.getProd_name());
        holder.iv_prod.setImageDrawable(ContextCompat.getDrawable(context,model.getProd_image()));
    }
    public void swipable(int pos){
        notifyItemChanged(pos);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_prod;
        private TextView prod_title;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_prod = itemView.findViewById(R.id.iv_prod);
            prod_title = itemView.findViewById(R.id.prod_title);
        }
    }

}
