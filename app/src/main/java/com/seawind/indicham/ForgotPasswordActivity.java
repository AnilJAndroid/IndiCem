package com.seawind.indicham;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    @BindView(R.id.btn_forgot_password) Button btn_forgot_password;
    @BindView(R.id.edt_email) EditText edt_email;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }
    @OnClick(R.id.btn_forgot_password)
    public void submit() {
        forgotpassword();
    }
    void forgotpassword(){
        pd = ProgressDialog.show(ForgotPasswordActivity.this,"","Loading...!");
        Call<ResponseBody> forgotPassword = WebAccess.getService().ForgotPassword("forgot",edt_email.getText().toString());
        forgotPassword.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();
                try {
                    String strRes = response.body().string();
                    Toast.makeText(getApplicationContext(), strRes, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
