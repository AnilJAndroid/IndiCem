package com.seawind.indicham;

import android.app.ProgressDialog;
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

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.btn_changeepassword) Button btn_changeepassword;
    @BindViews({R.id.edt_oldpassword,R.id.edt_usernewpassword}) List<EditText> editTexts;
    ProgressDialog pd;
    SharedPreferences sPref;
    String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ButterKnife.bind(this);

        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        USER_ID = sPref.getString(Constant.KEY_USER_ID,"0");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }
    @OnClick(R.id.btn_changeepassword)
    public void submit(){
        dochangePassword();
    }
    void dochangePassword(){
        pd = ProgressDialog.show(ChangePasswordActivity.this,"","Loading...!");
        String old_pass = editTexts.get(0).getText().toString();
        String new_pass = editTexts.get(1).getText().toString();

        Call<ResponseBody> strRes = WebAccess.getService().Changepassword("change_password",USER_ID,old_pass,new_pass);
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
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
