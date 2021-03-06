package com.seawind.indicham;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.realm.Realm;

public class ProductDetail extends AppCompatActivity {

    private TextView txt_price,pro_desc;
    private int price = 299;
    private int val=299;
    private int increase=1;
    private Realm realm;
    private ProductModel model;
    private Button btn_addtocart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        realm = Realm.getDefaultInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        model = (ProductModel) getIntent().getSerializableExtra("p_model");
        price = model.getPrice();
        val = model.getPrice();

        btn_addtocart = findViewById(R.id.btn_addtocart);
        txt_price = findViewById(R.id.txt_price);
        ImageView prod_image = findViewById(R.id.prod_image);
        final TextView txt_count = findViewById(R.id.txt_count);
        pro_desc = findViewById(R.id.pro_desc);
        pro_desc.setText(model.getP_desc());

        ImageButton btn_plus = findViewById(R.id.btn_plus);
        ImageButton btn_minus = findViewById(R.id.btn_minus);
        btn_plus.setOnClickListener(v -> {
            if(increase<model.getQty()){
                increase = increase+1;
                val = val+price;
                txt_price.setText(val+".00 " +getString(R.string.Rs));
                txt_count.setText(String.valueOf(increase));
            }
        });
        btn_minus.setOnClickListener(v -> {
            if(increase>1){
                increase = increase-1;
                val = val-price;
                txt_price.setText(val+".00 " +getString(R.string.Rs));
                txt_count.setText(String.valueOf(increase));
            }
        });
        btn_addtocart.setOnClickListener(v -> realm.executeTransaction(realm -> realm.insert(model)));
        txt_price.setText(val+".00 " +getString(R.string.Rs));
        Glide.with(getApplicationContext()).load(model.getProd_image()).into(prod_image);
        getSupportActionBar().setTitle(model.getProd_name());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        for (int i=0;i<menu.size();i++){
            if (menu.getItem(i).getItemId()==item.getItemId())
                item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            case R.id.ic_cart:
                startActivity(new Intent(getApplicationContext(),CartAcitivty.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
