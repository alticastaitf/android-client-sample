package com.alticast.voiceable.clientsample;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.alticast.mmuxclient.ClientAPI;
import com.alticast.mmuxclient.Entity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public class BaseActivity extends AppCompatActivity implements CommandListener {
    @Override
    public boolean receiveCommand(String pattern, String response, Map<String, Entity> entities) {
        return false;
    }
}
