package edu.galileo.android.facebookrecipes.recipelist;

import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.lib.EventBus;
import edu.galileo.android.facebookrecipes.recipelist.events.RecipeListEvent;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipesListView;

/**
 * Created by ykro.
 */
public class RecipeListPresenterImpl implements RecipeListPresenter {
    private EventBus eventBus;
    private RecipesListView view;
    private RecipeListInteractor interactor;

    public RecipeListPresenterImpl(EventBus eventBus, RecipesListView view, RecipeListInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void getRecipes() {
        interactor.execute();
    }

    @Override
    public void setFavorite(Recipe recipe) {

    }

    @Override
    public void delteRecipe(Recipe recipe) {

    }

    @Override
    public void onEvent(RecipeListEvent event) {
        if (this.view != null) {
            view.setRecipes(event.getRecipes());
        }
    }
}
