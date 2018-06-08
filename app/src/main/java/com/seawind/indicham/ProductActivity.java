package com.seawind.indicham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.seawind.indicham.Adapter.ProductAdapter;
import com.seawind.indicham.Util.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    RecyclerView recyclewview;
    ProductAdapter adapter;
    private int[] image = new int[]{R.drawable.image1,R.drawable.image2,R.drawable.image3,
                                    R.drawable.image4,R.drawable.image5,R.drawable.image5};

    private String[] product_name = new String[]{"Imidacloprid","Cypermethrin","Thiamethoxam",
                                                "Malathion","Glyphosate","Thiamethoxam"};

    private String category_id="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodict);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Products");
        
        recyclewview = findViewById(R.id.recyclewview);
        recyclewview.setHasFixedSize(true);

        recyclewview.setItemViewCacheSize(20);
        recyclewview.setDrawingCacheEnabled(true);
        recyclewview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclewview.setNestedScrollingEnabled(false);

        int numberOfColumns = 2;
        recyclewview.setHasFixedSize(true);
        recyclewview.setLayoutManager(new GridLayoutManager(this,numberOfColumns));
        /*adapter = new ProductAdapter(getApplicationContext(),models);
        recyclewview.setAdapter(adapter);*/
        recyclewview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclewview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onLongClick(View child, int childPosition) {}
            @Override
            public void onClick(View child, int childPosition) {
                Intent intent = new Intent(getApplicationContext(),ProductDetail.class);
                ProductModel model = adapter.getItem(childPosition);
                intent.putExtra("p_model",model);
                startActivity(intent);
            }
        }){
        });

        category_id = getIntent().getStringExtra("category_id");
        getProduct(category_id);
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
    void getProduct(String category_id){
        ProgressDialog pd = ProgressDialog.show(ProductActivity.this,"","Loading...!");
        Call<ResponseBody> product_list =  WebAccess.getService().Product_list("product_list",category_id);
        product_list.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String strRes = response.body().string();
                        ArrayList<ProductModel> list = new ArrayList<>();
                        JSONObject obj = new JSONObject(strRes);
                        JSONArray array = obj.getJSONArray("mainCategory");
                        for(int i=0;i<array.length();i++){
                            JSONObject jsonObject = array.getJSONObject(i);
                            ProductModel model = new ProductModel();
                            model.setProd_name(jsonObject.getString("product_name"));
                            model.setPrice(Integer.parseInt(jsonObject.getString("print_price")));
                            model.setP_desc(jsonObject.getString("product_description"));
                            model.setProd_image(jsonObject.getString("product_image"));
                            model.setQty(Integer.parseInt(jsonObject.getString("quantity")));
                            list.add(model);
                        }
                        adapter = new ProductAdapter(getApplicationContext(),list);
                        recyclewview.setAdapter(adapter);
                    } catch (IOException |JSONException e) {
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

}
