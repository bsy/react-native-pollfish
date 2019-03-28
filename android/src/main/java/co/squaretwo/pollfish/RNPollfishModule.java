
package co.squaretwo.pollfish;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;


import com.pollfish.interfaces.PollfishClosedListener;
import com.pollfish.interfaces.PollfishOpenedListener;
import com.pollfish.interfaces.PollfishSurveyCompletedListener;
import com.pollfish.interfaces.PollfishSurveyNotAvailableListener;
import com.pollfish.interfaces.PollfishSurveyReceivedListener;
import com.pollfish.interfaces.PollfishUserNotEligibleListener;
import com.pollfish.main.PollFish;
import com.pollfish.main.PollFish.ParamsBuilder;

import java.util.HashMap;


public class RNPollfishModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNPollfish";
    private EventManager eventManager;

    private ReactApplicationContext mContext;
    private Intent mSurveyIntent;
    private Callback requestSurveyCallback;

    private boolean isInitializing = false;

    public RNPollfishModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
        this.eventManager = new EventManager(reactContext);
    }

    @Override
    public String getName() {
        return "RNPollfish";
    }

    @ReactMethod
    public void initialize(final String apiKey,
        final boolean debugMode,
        final boolean autoMode,
        final String uuid) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
            Log.d(TAG, ">> Initializing Pollfish");

            PollFish.initWith( getCurrentActivity(),
                new ParamsBuilder(apiKey)
                    .releaseMode(!debugMode) // Due to inconsitency with iOS, we negate whatever is passed in for production
                    .customMode(autoMode) // Set true to avoid auto-popup behavior
                    .requestUUID(uuid) // Unique user identifier, passed back in the callback
                    .pollfishSurveyReceivedListener(new PollfishSurveyReceivedListener() {
                        @Override
                        public void onPollfishSurveyReceived(final boolean playfulSurvey, final int surveyPrice) {
                            WritableMap map = new WritableNativeMap();
                            map.putBoolean("playfulSurvey", playfulSurvey);
                            map.putInt("surveyPrice", surveyPrice);
                            eventManager.send("surveyReceived", map);
                        }
                    })
                    .pollfishSurveyNotAvailableListener(new PollfishSurveyNotAvailableListener() {
                        @Override
                        public void onPollfishSurveyNotAvailable() {
                            eventManager.send("surveyNotAvailable");
                        }
                    })
                    .pollfishSurveyCompletedListener(new PollfishSurveyCompletedListener() {
                        @Override
                        public void onPollfishSurveyCompleted(final boolean playfulSurvey, final int surveyPrice) {
                            WritableMap map = new WritableNativeMap();
                            map.putBoolean("playfulSurvey", playfulSurvey);
                            map.putInt("surveyPrice", surveyPrice);
                            eventManager.send("surveyCompleted", map);
                        }
                    })
                    .pollfishUserNotEligibleListener(new PollfishUserNotEligibleListener() {
                        @Override
                        public void onUserNotEligible() {
                            eventManager.send("userNotEligible");
                        }
                    })
                    .pollfishOpenedListener(new PollfishOpenedListener() {
                        @Override
                        public void onPollfishOpened() {
                            eventManager.send("surveyOpened");
                        }
                    })
                    .pollfishClosedListener(new PollfishClosedListener() {
                        @Override
                        public void onPollfishClosed() {
                            WritableMap map = new WritableNativeMap();
                            map.putBoolean("wasClosedByInitialize", isInitializing);
                            eventManager.send("surveyClosed", map);
                        }
                    })
                    .build());
            if (autoMode) {
                isInitializing = true;
                PollFish.hide();
                isInitializing = false;
            }
          }
        });



    }

    @ReactMethod
    public void destroy() {
      // no-op on Android
    }

    @ReactMethod
    public void show() {
        PollFish.show();
    }

    @ReactMethod
    public void hide() {
        PollFish.hide();
    }

    @ReactMethod
    public void surveyAvailable() {
        PollFish.isPollfishPresent();
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }




}
