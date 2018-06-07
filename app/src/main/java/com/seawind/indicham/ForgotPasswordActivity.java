package com.seawind.indicham;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button btn_forgot_password;
    EditText edt_email;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edt_email = findViewById(R.id.edt_email);
        btn_forgot_password = findViewById(R.id.btn_forgot_password);
        btn_forgot_password.setOnClickListener(v -> {
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
                public void onFailure(Call<ResponseBody> call, Throwable t) {pd.dismiss();}
            });
        });
    }
}
