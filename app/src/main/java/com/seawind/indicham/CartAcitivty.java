package com.seawind.indicham;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.seawind.indicham.Adapter.CartAdapter;

import java.util.ArrayList;

public class CartAcitivty extends AppCompatActivity {
    RecyclerView recyclewview;
    private int[] image = new int[]{R.drawable.image1,R.drawable.image2,R.drawable.image3,
            R.drawable.image4,R.drawable.image5,R.drawable.image5};

    private String[] product_name = new String[]{"Imidacloprid","Cypermethrin","Thiamethoxam",
            "Malathion","Glyphosate","Thiamethoxam"};

    private Button btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_acitivty);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CART");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_checkout = findViewById(R.id.btn_checkout);
        recyclewview = findViewById(R.id.recyclewview);
        recyclewview.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclewview.setLayoutManager(llm);
        recyclewview.addItemDecoration(new com.seawind.indicham.Util.DividerItemDecoration(getResources()));
        ArrayList<ProductModel> models = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            ProductModel model = new ProductModel();
            model.setProd_name(product_name[i]);
            model.setProd_image(image[i]);
            models.add(model);
        }

        final CartAdapter cartAdapter = new CartAdapter(getApplicationContext(), models);
        recyclewview.setAdapter(cartAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                cartAdapter.swipable(viewHolder.getAdapterPosition());
            }
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclewview);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
