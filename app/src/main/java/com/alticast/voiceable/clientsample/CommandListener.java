package com.alticast.voiceable.clientsample;

import org.json.JSONArray;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public interface CommandListener {
    boolean receiveCommand(String pattern, String response, JSONArray entities);
}
