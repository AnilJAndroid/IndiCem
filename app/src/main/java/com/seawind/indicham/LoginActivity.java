package com.seawind.indicham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 28-May-18.
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_login) Button btn_login;
    @BindViews({R.id.edt_username,R.id.edt_password}) List<EditText> nameViews;
    @BindView(R.id.txt_signup) TextView txt_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_login)
    public void submit() {
        String username = nameViews.get(0).getText().toString();
        String password = nameViews.get(1).getText().toString();
        doLogin(username,password);
    }
    @OnClick(R.id.txt_signup)
    public void signup(){
        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
    }
    void doLogin(String username,String password){
        ProgressDialog pd = ProgressDialog.show(LoginActivity.this,"","Loading...!");
        pd.show();
        retrofit2.Call<ResponseBody> user_reg = WebAccess.getService().UserLogin("login",username,password);
        user_reg.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();
                if(response.isSuccessful()){
                    try {
                        String strRes = response.body().string();
                        JSONObject jsonObject = new JSONObject(strRes);
                        if(jsonObject.getString("success").equals("true")){
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException|JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
