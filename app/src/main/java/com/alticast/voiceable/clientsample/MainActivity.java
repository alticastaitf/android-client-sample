package com.alticast.voiceable.clientsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alticast.mmuxclient.ClientAPI;
import com.alticast.mmuxclient.Entity;
import com.alticast.mmuxclient.annotations.VoiceableCallback;
import com.alticast.mmuxclient.annotations.VoiceableContext;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dy.yoon on 2018-05-18.
 */
@VoiceableContext("MAIN")
public class MainActivity extends BaseActivity {
    Button showToastButton;
    Button showPromptButton;
    Button hidePromptButton;
    Button secondActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize ClientAPI
        ASREventController.getInstance().initialize(this);

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

        //Register VoiceableContext Activity
        ASREventController.register(this);
    }


    @Override
    public boolean receiveCommand(String pattern, String response, Map<String, Entity> entities){
        if (pattern.equalsIgnoreCase(MainGrammar.PATTERN_TV_ON)) {//TV ON
            Toast.makeText(this, "TV on", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

//    @VoiceableCallback
//    public void customAction(String pattern, String response, Map<String, Entity> entities){
//
//    }
}
