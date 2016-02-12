package edu.galileo.android.facebookrecipes.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import edu.galileo.android.facebookrecipes.entities.Recipe;

/**
 * Created by ykro.
 */
public class RecipeSearchResponse {
    private int count;
    @SerializedName("recipes")
    private List<Recipe> recipes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Recipe getFirstRecipe(){
        Recipe first = null;
        if (!recipes.isEmpty()) {
            first = recipes.get(0);
        }
        return first;
    }
}
