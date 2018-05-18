package com.alticast.voiceable.clientsample;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.ArrayList;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public class MainGrammar {
    private final static String GLOBAL_SCENE = "_global";

    public final static String PATTERN_TV_ON = "tv on";

    private MultiValuedMap<String, String> patternsMap = new ArrayListValuedHashMap<String, String>();
    private MultiValuedMap<String, String> exampleTextMap = new ArrayListValuedHashMap<String, String>();

    public MainGrammar() {
        registerPattern(PATTERN_TV_ON, new String[]{MainActivity.class.getSimpleName()});
    }

    private void registerPattern(String pattern, String[] screenNames) {
        for (String screenName : screenNames) {
            patternsMap.put(screenName, pattern);
        }
    }

    public String[] getPatterns(String screenName) {
        ArrayList result = new ArrayList(patternsMap.get(GLOBAL_SCENE));
        result.addAll(patternsMap.get(screenName));
        String[] resultStr = new String[result.size()];
        result.toArray(resultStr);
        return resultStr;
    }
}
