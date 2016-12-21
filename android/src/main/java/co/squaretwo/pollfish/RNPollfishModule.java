
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
import com.facebook.react.modules.core.DeviceEventManagerModule;


import com.pollfish.main.PollFish;
import com.pollfish.main.PollFish.ParamsBuilder;

public class RNPollfishModule extends ReactContextBaseJavaModule {
  private static final String TAG = "RNPollfish";

  private ReactApplicationContext mContext;
  private Intent mSurveyIntent;
  private Callback requestSurveyCallback;

  public RNPollfishModule(ReactApplicationContext reactContext) {
    super(reactContext);
    mContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNPollfish";
  }

  @ReactMethod
  public void initialize(final String apiKey, final boolean releaseMode) {
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        Log.d(TAG, ">> Initializing Pollfish");

        PollFish.initWith( getCurrentActivity(), new ParamsBuilder(apiKey)
                .customMode(true) // Override the default, more obtrusive behavoir
                .releaseMode(releaseMode)
                .build());

      }
    });
  }

  private void sendEvent(String eventName, @Nullable WritableMap params) {
    getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
  }




}
