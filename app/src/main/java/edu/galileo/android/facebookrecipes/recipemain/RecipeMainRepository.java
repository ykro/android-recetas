package edu.galileo.android.facebookrecipes.recipemain;

import edu.galileo.android.facebookrecipes.entities.Recipe;

/**
 * Created by ykro.
 */
public interface RecipeMainRepository {
    void getNextRecipe();
    void saveRecipe(Recipe recipe);
}
