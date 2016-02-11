package edu.galileo.android.facebookrecipes;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by ykro.
 */
public class FacebookRecipesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
