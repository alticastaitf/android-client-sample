package com.alticast.voiceable.clientsample;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ASREventController.getInstance().createASRContext(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ASREventController.getInstance().destroyASRContext();
    }

    @Override
    public boolean receiveCommand(String pattern, String response, JSONArray entities){
        if (pattern.equalsIgnoreCase(MainGrammar.PATTERN_TV_ON)) {//TV ON
            Toast.makeText(this, "TV on", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
