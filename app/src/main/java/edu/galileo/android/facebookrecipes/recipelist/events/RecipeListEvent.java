package edu.galileo.android.facebookrecipes.recipelist.events;

import java.util.List;

import edu.galileo.android.facebookrecipes.entities.Recipe;

/**
 * Created by ykro.
 */
public class RecipeListEvent {
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
