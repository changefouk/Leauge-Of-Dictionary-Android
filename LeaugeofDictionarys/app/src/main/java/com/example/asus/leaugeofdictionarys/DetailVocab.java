package com.example.asus.leaugeofdictionarys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DetailVocab extends AppCompatActivity {
    private DBHelper mydb;
    SQLiteDatabase mysq;
    Cursor cur;
    ListView listV;
    TextToSpeech tts;
    private static final int INTENT_CHECK_TTS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vocab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listV = (ListView) findViewById(R.id.listView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String a = extras.getString("item");

            mydb = new DBHelper(this);
            mysq = mydb.getReadableDatabase();

            cur = mysq.rawQuery("select * from eng2thai where eentry=" + '"' + a + '"', null);
            ArrayList<String> arrayList = new ArrayList<String>();

            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                arrayList.add(cur.getString(cur.getColumnIndex(DBHelper.CONTACTS_COLUMN_EENTRY)) + " "
                        + '(' + cur.getString(cur.getColumnIndex(DBHelper.CONTACTS_COLUMN_ECAT)) + ')');
                cur.moveToNext();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            listV.setAdapter(arrayAdapter);
            listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        cur.moveToPosition(arg2);
                    String mean1 = cur.getString(cur.getColumnIndex(DBHelper.CONTACTS_COLUMN_EENTRY));
                    String mean2 = cur.getString(cur.getColumnIndex(DBHelper.CONTACTS_COLUMN_TENTRY));
                    String mean3 = cur.getString(cur.getColumnIndex(DBHelper.CONTACTS_COLUMN_ETHAI));
                    String mean4 = cur.getString(cur.getColumnIndex(DBHelper.CONTACTS_COLUMN_ESYN));
                    String dataMean = (String) arg0.getItemAtPosition(arg2).toString();

                    Bundle datamean = new Bundle();
                    datamean.putString("mean1", mean1);
                    datamean.putString("mean2", mean2);
                    datamean.putString("mean3", mean3);
                    datamean.putString("mean4", mean4);
                    datamean.putString("dataMean", dataMean);


                    Intent intentmean = new Intent(DetailVocab.this, VocabActivity.class);
                    intentmean.putExtras(datamean);
                    startActivity(intentmean);
                }
            });
        }
    }
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.back) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    }

