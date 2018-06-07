package com.seawind.indicham;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText edt_username,edt_email,edt_contact_no,edt_address;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);
        edt_contact_no = findViewById(R.id.edt_contact_no);
        edt_address = findViewById(R.id.edt_address);

        btn_update = findViewById(R.id.btn_signup);
        btn_update.setOnClickListener(v -> updateProfile(Session.getInstance().getUser_id()));

    }

    void updateProfile(String userid){
        Call<ResponseBody> strRes = WebAccess.getService().EditProfile("update_user_data",userid,edt_username.getText().toString(),
                edt_email.getText().toString(),edt_contact_no.getText().toString(),edt_address.getText().toString());
        strRes.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String strRes = response.body().string();
                        JSONObject object = new JSONObject(strRes);
                        String message = object.getString("message");
                        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    } catch (IOException |JSONException e) {e.printStackTrace();}
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });

    }
}
