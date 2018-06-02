package com.seawind.indicham.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seawind.indicham.ProductModel;
import com.seawind.indicham.R;
import com.seawind.indicham.Util.OnSwipeListener;

import java.util.ArrayList;

/**
 * Created by admin on 28-May-18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<ProductModel> list;

    public ProductAdapter(Context context, ArrayList<ProductModel> models){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = models;
    }

    public ProductModel getItem(int pos){
        return this.list.get(pos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itetview = mInflater.inflate(R.layout.product_row,parent,false);
        return new ViewHolder(itetview);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.iv_prod.setImageDrawable(ContextCompat.getDrawable(context,model.getProd_image()));
        holder.txt_prod_title.setText(model.getProd_name());
    }
    @Override
    public int getItemCount() {
        return this.list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_prod;
        private TextView txt_prod_title;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_prod = itemView.findViewById(R.id.iv_prod);
            txt_prod_title = itemView.findViewById(R.id.txt_prod_title);

        /* itemView.setOnTouchListener(new OnSwipeListener(context){
                @Override
                public void onSwipeLeft() {
                    Toast.makeText(context, "onSwipeLeft", Toast.LENGTH_SHORT).show();
                    super.onSwipeLeft();
                }
            });*/
        }
    }

}
