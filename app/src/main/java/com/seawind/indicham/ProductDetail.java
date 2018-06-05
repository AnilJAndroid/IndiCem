package com.seawind.indicham;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetail extends AppCompatActivity {

    private TextView txt_price;
    private int price = 299;
    private int val=299;
    private int increase=1;
    private ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_price = findViewById(R.id.txt_price);
        ImageView prod_image = findViewById(R.id.prod_image);
        final TextView txt_count = findViewById(R.id.txt_count);

        ImageButton btn_plus = findViewById(R.id.btn_plus);
        ImageButton btn_minus = findViewById(R.id.btn_minus);
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase = increase+1;
                val = val+price;
                txt_price.setText(val+".00 " +getString(R.string.Rs));
                txt_count.setText(String.valueOf(increase));
            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(increase>1){
                    increase = increase-1;
                    val = val-price;
                    txt_price.setText(val+".00 " +getString(R.string.Rs));
                    txt_count.setText(String.valueOf(increase));
                }
            }
        });

        txt_price.setText(val+".00 " +getString(R.string.Rs));

        Intent i = getIntent();

        prod_image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),i.getIntExtra("prod_image",R.drawable.image1)));
//        prod_title.setText(i.getStringExtra("prod_name"));
        getSupportActionBar().setTitle(i.getStringExtra("prod_name"));

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
