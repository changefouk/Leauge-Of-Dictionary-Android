package com.example.asus.leaugeofdictionarys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listViewContacts;
    DBHelper mydb;
    EditText edtsearch;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydb = new DBHelper(this);
        arrayList = mydb.getAllcontacts_2();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listViewContacts = (ListView) findViewById(R.id.listView1);
        listViewContacts.setAdapter(arrayAdapter);
        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String itemdata=(String)arg0.getItemAtPosition(arg2).toString();
                String dataItem = (String) arg0.getItemAtPosition(arg2).toString();

                Bundle dataBundle = new Bundle();
                dataBundle.putString("itemdata", itemdata);
                dataBundle.putString("item", dataItem);

                Intent intent = new Intent(MainActivity.this, DetailVocab.class);
                intent.putExtras(dataBundle);
                startActivity(intent);

            }
        });
        edtsearch = (EditText) findViewById(R.id.edtsearch);
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                final ArrayList<String> src_list = new ArrayList<String>();
                int textlength = edtsearch.getText().length();
                for (int i = 0; i < arrayList.size(); i++) {
                    try {
                        if (edtsearch.getText().toString()
                                .equalsIgnoreCase(arrayList.get(i)
                                        .subSequence(0, textlength)
                                        .toString())) {
                            src_list.add(arrayList.get(i));
                        }
                    } catch (Exception e) {
                    }
                    listViewContacts.setAdapter(new ArrayAdapter(MainActivity.this
                            , android.R.layout.simple_list_item_1
                            , src_list));
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id == R.id.search) {

        } else if (id == R.id.aboutus) {
            Intent a = new Intent(MainActivity.this, AboutUs.class);
            startActivity(a);


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
