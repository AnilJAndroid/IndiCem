package com.seawind.indicham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindViews;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Us");

        TextView txt_aboutus = findViewById(R.id.txt_aboutus);
        txt_aboutus.setMovementMethod(new ScrollingMovementMethod());

        pd = ProgressDialog.show(AboutUsActivity.this,"","Loading...!");
        pd.show();

        Call<ResponseBody> data = WebAccess.getService().getAboutus("about");
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();
                String P_CONTENT = null;
                if(response.isSuccessful()){
                    try {
                        String strRes = response.body().string();
                        JSONObject jsonObject = new JSONObject(strRes);
                        JSONArray array = jsonObject.getJSONArray("mainCategory");
                        for (int i=0;i<array.length();i++){
                            JSONObject object = array.getJSONObject(i);
                            P_CONTENT=object.getString("P_CONTENT");
                        }
                        txt_aboutus.setText(Html.fromHtml(P_CONTENT));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(AboutUsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
