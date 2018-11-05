package com.alticast.voiceable.clientsample;

import com.alticast.mmuxclient.ClientAPI;
import com.alticast.mmuxclient.Entity;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public interface CommandListener {
    boolean receiveCommand(String pattern, String response, Map<String, Entity> entities);
}
