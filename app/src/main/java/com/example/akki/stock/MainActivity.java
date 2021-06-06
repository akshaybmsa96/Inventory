package com.example.akki.stock;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    Toolbar tb;
    ActionBarDrawerToggle toggle;
    MenuItem prevItem;
    DrawerLayout drawer;
    FragmentTransaction fragmentTransaction;
    SearchView searchView;
    TextView toolbarText;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        homeFragment = new HomeFragment();
        tb = (Toolbar) findViewById(R.id.toolbar);
        searchView = (SearchView) findViewById(R.id.searchView);
        toolbarText = (TextView) findViewById(R.id.toolbarText);


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigator);
        toolbarText.setText("Stock");

        tb.setTitleTextColor(0XFFFFFFFF);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (prevItem != null)
                    prevItem.setChecked(false);

                item.setCheckable(true);
                item.setChecked(true);

                prevItem = item;


                switch (item.getItemId()) {

                    case R.id.dashboard:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, homeFragment);
                        fragmentTransaction.commit();
                        toolbarText.setText("Stock");
                        searchView.setVisibility(View.VISIBLE);
                        drawer.closeDrawers();
                        break;

                    case R.id.add_item:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new FragmentAddItem());
                        fragmentTransaction.commit();
                        toolbarText.setText("Add Item");
                        searchView.setVisibility(View.GONE);
                        drawer.closeDrawers();
                        break;

                    case R.id.activity_log:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new LogFragment());
                        fragmentTransaction.commit();
                        toolbarText.setText("Activity Log");
                        searchView.setVisibility(View.GONE);
                        drawer.closeDrawers();
                        break;

                }

                return false;


            }

        });

        MenuItem item = navigationView.getMenu().findItem(R.id.dashboard);
        item.setCheckable(true);
        item.setChecked(true);
        prevItem = item;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.home_layout_id, homeFragment);
        fragmentTransaction.commit();
        toggle = new ActionBarDrawerToggle(
                this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.closeDrawers();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarText.setVisibility(View.GONE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbarText.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeFragment.onSearch(newText);
                return false;
            }
        });

    }


}
