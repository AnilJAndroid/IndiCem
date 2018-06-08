package com.seawind.indicham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seawind.indicham.Util.Constant;

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
    @BindView(R.id.txt_forgot_password) TextView txt_forgot_password;
    @BindView(R.id.txt_skip) TextView txt_skip;

    private SharedPreferences sPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

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
    @OnClick(R.id.txt_forgot_password)
    public void forgot_password(){
        startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
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
                            String userid = jsonObject.getString("userid");
                            sPref.edit().putBoolean(Constant.KEY_ISLOGIN,true).
                                    putString(Constant.KEY_USER_ID,userid).apply();
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
                t.printStackTrace();
                pd.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @OnClick(R.id.txt_skip)
    public void skip(){
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
