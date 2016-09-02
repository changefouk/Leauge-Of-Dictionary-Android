package com.example.asus.leaugeofdictionarys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class VocabActivity extends AppCompatActivity {
    private static final int INTENT_CHECK_TTS = 0;

    TextToSpeech tts;
    CheckBox chk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences shared = getSharedPreferences("sound", Context.MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String a = extras.getString("dataMean");

            String test1 = getIntent().getExtras().getString("mean1");
            final TextView txtM1 = (TextView) findViewById(R.id.txtD1);
            txtM1.setText(test1);

            String test2 = getIntent().getExtras().getString("mean2");
            TextView txtM2 = (TextView) findViewById(R.id.txtD2);
            txtM2.setText(test2);

            String test3 = getIntent().getExtras().getString("mean3");
            TextView txtM3 = (TextView) findViewById(R.id.txtD3);
            txtM3.setText(test3);

            String test4 = getIntent().getExtras().getString("mean4");
            TextView txtM4 = (TextView) findViewById(R.id.txtD4);
            txtM4.setText(test4);

            Intent intent = new Intent();
            intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(intent, INTENT_CHECK_TTS);

            ImageButton imgsound = (ImageButton) findViewById(R.id.imgsound);
            imgsound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = txtM1.getText().toString();
                    float sound1 = shared.getFloat("pitchRate", 0.0f);
                    float sound2 = shared.getFloat("speechRate", 0.0f);
                    tts.setPitch(sound1);
                    tts.setSpeechRate(sound2);
                    tts.speak(str, TextToSpeech.QUEUE_ADD, null);

                }
            });
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_CHECK_TTS) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    public void onInit(int status) {
                        tts.setLanguage(Locale.US);
                    }
                });
            } else {
                Intent intent = new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(intent);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (tts != null)
            tts.shutdown();
    }
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mean_menu, menu);
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







