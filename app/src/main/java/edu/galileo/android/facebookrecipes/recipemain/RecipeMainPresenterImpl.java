package edu.galileo.android.facebookrecipes.recipemain;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.lib.EventBus;
import edu.galileo.android.facebookrecipes.recipemain.events.RecipeMainEvent;
import edu.galileo.android.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by ykro.
 */
public class RecipeMainPresenterImpl implements RecipeMainPresenter {
    EventBus eventBus;
    RecipeMainView view;
    SaveRecipeInteractor saveRecipe;
    GetNextRecipeInteractor getNextRecipe;

    public RecipeMainPresenterImpl(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveRecipe, GetNextRecipeInteractor getNextRecipe) {
        this.view = view;
        this.eventBus = eventBus;
        this.saveRecipe = saveRecipe;
        this.getNextRecipe = getNextRecipe;
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
    public RecipeMainView getView() {
        return this.view;
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        if (this.view != null){
            view.saveAnimation();
            view.hideUIElements();
            view.showProgress();
        }
        saveRecipe.execute(recipe);
    }

    @Override
    public void dismissRecipe() {
        if (this.view != null) {
            view.dismissAnimation();
        }
        getNextRecipe();
    }

    @Override
    public void getNextRecipe(){
        if (this.view != null) {
            view.showProgress();
            view.hideUIElements();
        }
        getNextRecipe.execute();

    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeMainEvent event) {
        if (this.view != null){
            String error = event.getError();
            if (error == null) {
                if (event.getType() == RecipeMainEvent.NEXT_EVENT) {
                    view.setRecipe(event.getRecipe());
                } else if (event.getType() == RecipeMainEvent.SAVE_EVENT) {
                    view.onRecipeSaved();
                    getNextRecipe.execute();
                }

            } else {
                view.hideProgress();
                view.onGetRecipeError(error);
            }
        }
    }
}
