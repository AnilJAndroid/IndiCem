package com.seawind.indicham;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetail extends AppCompatActivity {

    private TextView txt_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_price = findViewById(R.id.txt_price);
        ImageView prod_image = findViewById(R.id.prod_image);
        TextView prod_title = findViewById(R.id.prod_title);

        txt_price.setText("499.00 "+getString(R.string.Rs));

        Intent i = getIntent();

        prod_image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),i.getIntExtra("prod_image",R.drawable.image1)));
//        prod_title.setText(i.getStringExtra("prod_name"));
        getSupportActionBar().setTitle(i.getStringExtra("prod_name"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_menu, menu);
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
