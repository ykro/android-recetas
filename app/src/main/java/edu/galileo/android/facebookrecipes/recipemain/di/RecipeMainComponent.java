package edu.galileo.android.facebookrecipes.recipemain.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.facebookrecipes.lib.di.LibsModule;
import edu.galileo.android.facebookrecipes.recipemain.ui.RecipeMainActivity;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {RecipeMainModule.class, LibsModule.class})
public interface RecipeMainComponent {
    void inject(RecipeMainActivity activity);
}
