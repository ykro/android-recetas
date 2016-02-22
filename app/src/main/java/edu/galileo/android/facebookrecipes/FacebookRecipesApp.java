package edu.galileo.android.facebookrecipes;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.raizlabs.android.dbflow.config.FlowManager;

import edu.galileo.android.facebookrecipes.lib.di.LibsModule;
import edu.galileo.android.facebookrecipes.recipelist.di.DaggerRecipeListComponent;
import edu.galileo.android.facebookrecipes.recipelist.di.RecipeListComponent;
import edu.galileo.android.facebookrecipes.recipelist.di.RecipeListModule;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipeListActivity;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipeListView;
import edu.galileo.android.facebookrecipes.recipelist.ui.adapters.OnItemClickListener;
import edu.galileo.android.facebookrecipes.recipemain.di.DaggerRecipeMainComponent;
import edu.galileo.android.facebookrecipes.recipemain.di.RecipeMainComponent;
import edu.galileo.android.facebookrecipes.recipemain.di.RecipeMainModule;
import edu.galileo.android.facebookrecipes.recipemain.ui.RecipeMainActivity;
import edu.galileo.android.facebookrecipes.recipemain.ui.RecipeMainView;

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

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBTearDown();
    }

    private void initDB() {
        FlowManager.init(this);
    }

    private void DBTearDown() {
        FlowManager.destroy();
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public RecipeMainComponent getRecipeMainComponent(RecipeMainActivity activity, RecipeMainView view) {
        return DaggerRecipeMainComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeMainModule(new RecipeMainModule(view))
                .build();
    }

    public RecipeListComponent getRecipeListComponent(RecipeListActivity activity, RecipeListView view, OnItemClickListener onItemClickListener) {
        return DaggerRecipeListComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeListModule(new RecipeListModule(view, onItemClickListener))
                .build();
    }
}
