package com.seawind.indicham;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.seawind.indicham.Util.Constant;

/**
 * Created by admin on 28-May-18.
 */

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences pRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pRef = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        new Handler().postDelayed(() -> {
            finish();
            if(pRef.getBoolean(Constant.KEY_ISLOGIN,false)){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return;
            }
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }, 1500);
    }
}
