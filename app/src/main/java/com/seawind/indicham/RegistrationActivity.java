package com.seawind.indicham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class RegistrationActivity extends AppCompatActivity {

    @BindViews({R.id.edt_username,R.id.edt_email,R.id.edt_contact_no,R.id.edt_password}) List<EditText> nameViews;
    @BindView(R.id.btn_signup) Button btn_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup)
    public void signup(){
        try {
            String username = nameViews.get(0).getText().toString();
            String email = nameViews.get(1).getText().toString();
            String conact_no = nameViews.get(2).getText().toString();
            String password = nameViews.get(3).getText().toString();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("full_name",username);
            jsonObject.put("email",email);
            jsonObject.put("mobile_num",conact_no);
            jsonObject.put("Password",password);
            JSONObject object = new JSONObject();
            object.put("data1",jsonObject);
            doReg(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    void doReg(String data){
        ProgressDialog pd = ProgressDialog.show(RegistrationActivity.this,"","Loading...!");
        pd.show();
        retrofit2.Call<ResponseBody> user_reg = WebAccess.getService().UserRegistration("regi_step",data);
        user_reg.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();
                if(response.isSuccessful()){
                    try {
                        String strRes = response.body().string();
                        JSONObject object = new JSONObject(strRes);
                        String message = object.getString("message");
                        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    } catch (IOException|JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
