package com.seawind.indicham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seawind.indicham.Util.Constant;

import org.json.JSONArray;
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
    SharedPreferences mPref;
    String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        USER_ID = mPref.getString(Constant.KEY_USER_ID,"");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);
        edt_contact_no = findViewById(R.id.edt_contact_no);
        edt_address = findViewById(R.id.edt_address);

        btn_update = findViewById(R.id.btn_update);

        getuserDetails();

        btn_update.setOnClickListener((View v) ->  {
            if(edt_username.getText().toString().length()==0||edt_email.getText().toString().length()==0
                    ||edt_contact_no.getText().toString().length()==0/*||edt_address.getText().toString().length()==0*/){
                Toast.makeText(this, "Enter All Details", Toast.LENGTH_SHORT).show();
            }else {
              updateProfile(USER_ID);
            }

        });

    }

    void getuserDetails(){
        ProgressDialog pd = ProgressDialog.show(ProfileActivity.this,"","Loading...!");
        Call<ResponseBody> response = WebAccess.getService().getUserDetails("user_data",USER_ID);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strRes = response.body().string();
                    JSONObject object = new JSONObject(strRes);
                    JSONArray array = object.getJSONArray("mainCategory");
                    String user_full_name = null,user_email = null,user_address = null,user_mobile = null;
                    for (int i=0;i<array.length();i++){
                        JSONObject obj = array.getJSONObject(i);
                        user_full_name = obj.getString("user_full_name");
                        user_email = obj.getString("user_email");
                        user_address = obj.getString("address");
                        user_mobile = obj.getString("user_mobile");
                    }
                    edt_username.setText(user_full_name);
                    edt_contact_no.setText(user_mobile);
                    edt_address.setText(user_address);
                    edt_email.setText(user_email);
                } catch (IOException|JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
            }
        });
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
                    } catch (IOException |JSONException e) {e.printStackTrace();}
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });

    }
}
