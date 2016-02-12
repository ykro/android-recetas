package edu.galileo.android.facebookrecipes.recipelist.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.facebookrecipes.lib.di.LibsModule;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipesListActivity;

/**
 * Created by ykro.
 */
@Singleton
@Component(modules = {RecipeListModule.class, LibsModule.class})
public interface RecipeListComponent {
    void inject(RecipesListActivity activity);
}
