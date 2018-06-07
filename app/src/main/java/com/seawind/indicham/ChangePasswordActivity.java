package com.seawind.indicham;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    Button btn_changeepassword;
    EditText edt_oldpassword,edt_usernewpassword;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btn_changeepassword = findViewById(R.id.btn_changeepassword);
        edt_oldpassword = findViewById(R.id.edt_oldpassword);
        edt_usernewpassword = findViewById(R.id.edt_usernewpassword);

        btn_changeepassword.setOnClickListener((View v) -> {

            pd = ProgressDialog.show(ChangePasswordActivity.this,"","Loading...!");
            String old_pass = edt_oldpassword.getText().toString();
            String new_pass = edt_usernewpassword.getText().toString();

            Call<ResponseBody> strRes = WebAccess.getService().Changepassword("change_password",Session.getInstance().getUser_id(),old_pass,new_pass);
            strRes.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pd.dismiss();
                    try {
                        String strRes = response.body().string();
                        Toast.makeText(getApplicationContext(), strRes, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {e.printStackTrace();}
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pd.dismiss();
                }
            });
        });
    }
}
