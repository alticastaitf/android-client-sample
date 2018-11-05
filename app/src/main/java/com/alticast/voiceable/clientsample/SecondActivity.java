package com.alticast.voiceable.clientsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alticast.mmuxclient.annotations.VoiceableContext;

/**
 * Created by dy.yoon on 2018-05-24.
 */

@VoiceableContext("SECOND")
public class SecondActivity extends BaseActivity {
    Button mainActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ASREventController.register(this);
        mainActivityButton = findViewById(R.id.mainactivity);
        mainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
