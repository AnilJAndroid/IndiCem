package com.seawind.indicham;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seawind.Database.Product;
import com.seawind.indicham.Adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class CartAcitivty extends AppCompatActivity {
    RecyclerView recyclewview;
    private Button btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_acitivty);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shopping cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_checkout = findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),CheckoutActivity.class)));
        recyclewview = findViewById(R.id.recyclewview);
        recyclewview.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclewview.setLayoutManager(llm);
        recyclewview.addItemDecoration(new com.seawind.indicham.Util.DividerItemDecoration(getResources()));

        final CartAdapter cartAdapter = new CartAdapter(getApplicationContext(), getProducts());
        recyclewview.setAdapter(cartAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                cartAdapter.swipable(viewHolder.getAdapterPosition());
            }
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {return false;}
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
    ArrayList<ProductModel> getProducts(){
        ArrayList<ProductModel> models = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ProductModel> productModels = realm.where(ProductModel.class).sort("timestamp", Sort.DESCENDING).findAllAsync();
        models.addAll(productModels);
        return models;

    }
}
