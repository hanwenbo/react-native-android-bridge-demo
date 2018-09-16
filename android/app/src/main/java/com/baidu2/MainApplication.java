package com.baidu2;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {
  private Activity curActivity;
  private static MainApplication instance;

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    public boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),new AnExampleReactPackage(),new BaiduLBSReactPackage(),new TTLockReactPackage()
      );
    }

    @Override
    protected String getJSMainModuleName() {
      return "index";
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
    return mReactNativeHost;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    registerActivityLifecycleCallbacks(callbacks);
    SoLoader.init(this, /* native exopackage */ false);
  }

  public static MainApplication getInstance(){
    return instance;
  }

  Application.ActivityLifecycleCallbacks callbacks = new Application.ActivityLifecycleCallbacks() {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
      Log.e("xxxx","onActivityCreated");
      curActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
      Log.e("xxxx","onActivityStarted");

      curActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
      Log.e("xxxx","onActivityResumed");
      curActivity = activity;
      Log.d("TAG","activity:" + activity.getClass());
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
      Log.d("TAG","activity:onActivityDestroyed");

      //TODO:
//      LogUtil.d("activity:" + activity.getClass(), DBG);
//            curActivity = null;
    }
  };
}
