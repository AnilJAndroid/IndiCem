package com.seawind.indicham;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.seawind.indicham.Adapter.ProductAdapter;
import com.seawind.indicham.Util.RecyclerTouchListener;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    RecyclerView recyclewview;
    private int[] image = new int[]{R.drawable.image1,R.drawable.image2,R.drawable.image3,
                                    R.drawable.image4,R.drawable.image5,R.drawable.image5};

    private String[] product_name = new String[]{"Imidacloprid","Cypermethrin","Thiamethoxam",
                                                "Malathion","Glyphosate","Thiamethoxam"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodict);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("PRODUCTS");
        
        recyclewview = findViewById(R.id.recyclewview);
        recyclewview.setHasFixedSize(true);

        recyclewview.setItemViewCacheSize(20);
        recyclewview.setDrawingCacheEnabled(true);
        recyclewview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclewview.setNestedScrollingEnabled(false);


        final ArrayList<ProductModel> models = new ArrayList<>();
        for (int i=0;i<image.length;i++){
            ProductModel model = new ProductModel();
            model.setProd_name(product_name[i]);
            model.setProd_image(image[i]);
            models.add(model);
        }

        int numberOfColumns = 2;
        recyclewview.setHasFixedSize(true);
        recyclewview.setLayoutManager(new GridLayoutManager(this,numberOfColumns));
        final ProductAdapter adapter = new ProductAdapter(getApplicationContext(),models);
        recyclewview.setAdapter(adapter);
        recyclewview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclewview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onLongClick(View child, int childPosition) {}
            @Override
            public void onClick(View child, int childPosition) {
                Intent intent = new Intent(getApplicationContext(),ProductDetail.class);
                ProductModel model = adapter.getItem(childPosition);
                intent.putExtra("prod_image",model.getProd_image());
                intent.putExtra("prod_name",model.getProd_name());
                startActivity(intent);
            }
        }){
        });
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
