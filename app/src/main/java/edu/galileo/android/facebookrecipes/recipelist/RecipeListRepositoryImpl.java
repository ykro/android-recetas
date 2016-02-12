package edu.galileo.android.facebookrecipes.recipelist;

import android.util.Log;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.lib.EventBus;
import edu.galileo.android.facebookrecipes.recipelist.events.RecipeListEvent;

/**
 * Created by ykro.
 */
public class RecipeListRepositoryImpl implements RecipeListRepository {
    private EventBus eventBus;

    public RecipeListRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getSavedRecipes() {
        FlowCursorList<Recipe> storedRecipes = new FlowCursorList<Recipe>(true, Recipe.class);
        Log.e("ASDF",storedRecipes.getCount() + " ");

        RecipeListEvent event = new RecipeListEvent();
        event.setRecipes(storedRecipes.getAll());
        eventBus.post(event);
    }
}
