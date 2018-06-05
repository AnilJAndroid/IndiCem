package com.seawind.indicham.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.seawind.indicham.ProductModel;
import com.seawind.indicham.R;

import java.util.ArrayList;

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
        final ProductModel model = list.get(position);
        holder.prod_title.setText(model.getProd_name());
        holder.iv_prod.setImageDrawable(ContextCompat.getDrawable(context,model.getProd_image()));
        holder.txt_price.setText(model.getVal()+".00 " + context.getString(R.string.Rs));
        holder.txt_total_qty.setText(String.valueOf(model.getQty()));
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setQty(model.getQty()+1);
                model.setVal(model.getVal()+model.getPrice());
                notifyDataSetChanged();
            }
        });
        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getQty()>1){
                    model.setQty(model.getQty()-1);
                    model.setVal(model.getVal()-model.getPrice());
                    notifyDataSetChanged();
                }
            }
        });
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
        private TextView prod_title,txt_price;
        private TextView txt_total_qty;
        private ImageButton btn_minus,btn_plus;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_prod = itemView.findViewById(R.id.iv_prod);
            prod_title = itemView.findViewById(R.id.prod_title);
            txt_price = itemView.findViewById(R.id.txt_price);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            txt_total_qty = itemView.findViewById(R.id.txt_total_qty);
        }
    }

}
