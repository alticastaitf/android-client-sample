package com.alticast.voiceable.clientsample;

import android.app.Activity;

import org.json.JSONArray;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public class BaseActivity extends Activity implements CommandListener {
    @Override
    protected void onResume() {
        super.onResume();
        ASREventController.getInstance().setCommandListener(this, this.getClass().getSimpleName());
    }
    @Override
    protected void onPause(){
        super.onPause();
        ASREventController.getInstance().removeCommandListener(this);
    }
    @Override
    public boolean receiveCommand(String pattern, String response, JSONArray entities) {
        return false;
    }
}
