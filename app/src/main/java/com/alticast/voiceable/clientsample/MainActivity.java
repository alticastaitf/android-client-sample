package com.alticast.voiceable.clientsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alticast.mmuxclient.ClientAPI;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public class MainActivity extends BaseActivity {
    Button showToastButton;
    Button showPromptButton;
    Button hidePromptButton;
    Button secondActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create ASR Context on Application Start
        ASREventController.getInstance().createASRContext(getApplicationContext());
        showToastButton = findViewById(R.id.showtoast);
        showToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Toast", Toast.LENGTH_SHORT).show();
            }
        });
        hidePromptButton = findViewById(R.id.hideprompt);
        hidePromptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ASREventController.getInstance().hideVoicePrompt();
            }
        });
        secondActivityButton = findViewById(R.id.secondactivity);
        secondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ASREventController.getInstance().destroyASRContext();
    }

    @Override
    public boolean receiveCommand(String pattern, String response, ArrayList<ClientAPI.Entity> entities){
        if (pattern.equalsIgnoreCase(MainGrammar.PATTERN_TV_ON)) {//TV ON
            Toast.makeText(this, "TV on", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
