package edu.galileo.android.facebookrecipes.recipelist;

import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.recipelist.events.RecipeListEvent;

/**
 * Created by ykro.
 */
public interface RecipeListPresenter {
    void onCreate();
    void onDestroy();
    void getRecipes();
    void setFavorite(Recipe recipe);
    void delteRecipe(Recipe recipe);
    void onEvent(RecipeListEvent event);
}
