package edu.galileo.android.facebookrecipes;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.raizlabs.android.dbflow.config.FlowManager;

import edu.galileo.android.facebookrecipes.lib.di.LibsModule;
import edu.galileo.android.facebookrecipes.recipesmain.di.DaggerRecipesMainComponent;
import edu.galileo.android.facebookrecipes.recipesmain.di.RecipesMainComponent;
import edu.galileo.android.facebookrecipes.recipesmain.di.RecipesMainModule;
import edu.galileo.android.facebookrecipes.recipesmain.ui.RecipesMainView;

/**
 * Created by ykro.
 */
public class FacebookRecipesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
        initFacebook();
    }

    private void initDB() {
        FlowManager.init(this);
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public RecipesMainComponent getRecipesMainComponent(RecipesMainView view) {
        return DaggerRecipesMainComponent
                .builder()
                .libsModule(new LibsModule())
                .recipesMainModule(new RecipesMainModule(view))
                .build();
    }
}
