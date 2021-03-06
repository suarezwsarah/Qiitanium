package com.ogaclejapan.qiitanium;

import android.graphics.Typeface;
import android.support.annotation.StringRes;

import com.crashlytics.android.Crashlytics;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.ogaclejapan.qiitanium.util.CrashlyticsTree;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class AppLifecycleCallbacks implements Qiitanium.LifecycleCallbacks {

  protected final Qiitanium app;

  public AppLifecycleCallbacks(Qiitanium app) {
    this.app = app;
  }

  @Override
  public void onCreate() {
    setupLeakCanary();
    setupFabric();
    setupLogger();
    setupTypeFace();
  }

  @Override
  public void onTerminate() {
    // Do nothing.
  }

  protected void setupLeakCanary() {
    LeakCanary.install(app);
  }

  protected void setupFabric() {
    Fabric.with(app, new Crashlytics());
  }

  protected void setupLogger() {
    Timber.plant(new CrashlyticsTree());
  }

  protected void setupTypeFace() {
    TypefaceHelper.init(new TypefaceCollection.Builder()
        .set(Typeface.NORMAL, createTypefaceFromAsset(R.string.font_ubuntu_regular))
        .set(Typeface.BOLD, createTypefaceFromAsset(R.string.font_ubuntu_bold))
        .set(Typeface.ITALIC, createTypefaceFromAsset(R.string.font_ubuntu_regular_italic))
        .set(Typeface.BOLD_ITALIC,
            createTypefaceFromAsset(R.string.font_ubuntu_bold_italic))
        .create());
  }

  protected Typeface createTypefaceFromAsset(@StringRes int resId) {
    return Typeface.createFromAsset(app.getAssets(), app.getString(resId));
  }

}
