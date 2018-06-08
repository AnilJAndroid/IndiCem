package com.seawind.indicham;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.seawind.indicham.Fragment.HomeFragment;
import com.seawind.indicham.Util.Constant;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    SharedPreferences sPref;
    View header_view;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rsz_1logo_shadow));
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header_view = navigationView.getHeaderView(0);

        header_view.setOnClickListener(v -> {
            drawer.closeDrawer(GravityCompat.START);
            if(sPref.getBoolean(Constant.KEY_ISLOGIN,false)){
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }else {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        DefaultHomeMethod();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ic_changepassword:
                startActivity(new Intent(getApplicationContext(),ChangePasswordActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_cart:
                startActivity(new Intent(getApplicationContext(),CartAcitivty.class));
                break;
            case R.id.nav_contactus:
                startActivity(new Intent(getApplicationContext(),ContactUsActivity.class));
                break;
            case R.id.nav_aboutus:
                startActivity(new Intent(getApplicationContext(),AboutUsActivity.class));
                break;
            case R.id.nav_logout:
                if(sPref.getBoolean(Constant.KEY_ISLOGIN,false)){
                    sPref.edit().clear().apply();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    drawer.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void DefaultHomeMethod() {
        Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sPref.getBoolean(Constant.KEY_ISLOGIN,false)){
            String emailID = sPref.getString(Constant.KEY_USER_EMAILID,"");
            TextView navUsername = header_view.findViewById(R.id.txt_username);
            navUsername.setText(emailID);
            Menu menu = navigationView.getMenu();
            MenuItem nav_login_Status = menu.findItem(R.id.nav_logout);
            nav_login_Status.setTitle("Logout");

        }
    }
}
