package com.alticast.voiceable.clientsample;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.alticast.mmuxclient.ClientAPI;
import com.alticast.mmuxclient.ClientCallback;
import com.alticast.mmuxclient.Entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dy.yoon on 2018-05-18.
 */

public class ASREventController implements ClientCallback<ClientAPI.ASRResult> {
    private ClientAPI.ASRContext asrContext = null;
    private CommandListener listener = null;

    private Context context = null;
    private MainGrammar grammar = null;

    private static class SingletonHolder {
        private static final ASREventController instance = new ASREventController();
    }

    public static ASREventController getInstance() {
        return SingletonHolder.instance;
    }

    public void initialize(Context context){
        this.context = context;
        ClientAPI.initialize(context);
    }

    public static void register(LifecycleOwner owner){
        ClientAPI.ASRContext asrContext = getInstance().createASRContext(owner, false);

        //V0 compatibility
        if(owner instanceof CommandListener){
            getInstance().setCommandListener((CommandListener) owner, owner.getClass().getSimpleName());
        }
        owner.getLifecycle().addObserver(asrContext);

    }

    public ClientAPI.ASRContext createASRContext(final LifecycleOwner owner, boolean isLowerVersion){
        if(isLowerVersion)
            asrContext = ClientAPI.createASRContext(owner, context.getPackageName(), ASREventController.this);
        else
            asrContext = ClientAPI.createASRContext(owner, this);

        getInstance().openEventPipe();
        grammar = new MainGrammar();

        return asrContext;
    }


    @Override
    public void callback(int msgType, ClientAPI.ASRResult asrResult) {
        switch(msgType){
            case ClientAPI.MSG_CREATE_ASR_CONTEXT:
            case ClientAPI.MSG_SET_ASR_CONTEXT:
                grammar = new MainGrammar();
                resetGrammar(grammar, MainActivity.class.getSimpleName());
                openEventPipe();
                break;
            case ClientAPI.MSG_ADD_ASR_PATTERN:
                String response = asrResult.getSpokenResponse();
                String pattern = asrResult.getMatchedPattern();
                String actionName = asrResult.getActionName();
                Map<String, Entity> entities = asrResult.getMatchedEntities();
                //if it has annotation @VoiceableCallback, it will invoke action
                //if it does not have annotation, it will call receiveCommand
                if(!asrContext.invokeAction(actionName, pattern, response, entities)){
                    if (listener != null) {
                        listener.receiveCommand(pattern, response, entities);
                    }
                }

                break;
        }
    }

    public void setCommandListener(CommandListener listener, String sceneName) {
        if(grammar!=null)
            resetGrammar(grammar, sceneName);
        this.listener = listener;
    }

    public void removeCommandListener(CommandListener listener) {
        if (this.listener != null && this.listener.equals(listener))
            this.listener = null;
    }



    public void destroyASRContext() {
        if (asrContext != null) {
            asrContext.enableScreenContext();
            ClientAPI.destroyASRContext(asrContext);
        }
        if (context != null) {
            ClientAPI.unbind(context);
        }
    }

    public void resetGrammar(final MainGrammar grammar, final String sceneName) {
        clearASRPatterns();

        for( String pattern : grammar.getPatterns(sceneName) ) {
            asrContext.addASRPattern(pattern, ASREventController.this);
        }
        asrContext.setExampleTexts( grammar.getExampleTexts(sceneName) );
    }

    public void clearASRPatterns() {
        if(asrContext==null) return;
        asrContext.clearASRPatterns(this);
    }

    public void showVoicePrompt() {
        if(asrContext == null) return;
        asrContext.showVoicePrompt();

    }

    public void hideVoicePrompt() {
        if(asrContext == null) return;
        asrContext.hideVoicePrompt();
    }

    public void openEventPipe(){
        if(asrContext == null) return;
        asrContext.openEventPipe();
        registerAPIEvents();
    }

    public void registerAPIEvents(){
        ClientCallback<ClientAPI.ClientEvent> callback = new ClientCallback<ClientAPI.ClientEvent>() {
            @Override
            public void callback(int msgType, ClientAPI.ClientEvent event) {
                event.performAction();
            }
        };
        asrContext.registerHandledEvents("scrollDown", callback);
        asrContext.registerHandledEvents("scrollLeft", callback);
        asrContext.registerHandledEvents("scrollRight", callback);
        asrContext.registerHandledEvents("scrollUp", callback);
    }
}