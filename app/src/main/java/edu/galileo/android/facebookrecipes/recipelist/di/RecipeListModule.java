package edu.galileo.android.facebookrecipes.recipelist.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.lib.EventBus;
import edu.galileo.android.facebookrecipes.lib.ImageLoader;
import edu.galileo.android.facebookrecipes.recipelist.RecipeListInteractor;
import edu.galileo.android.facebookrecipes.recipelist.RecipeListInteractorImpl;
import edu.galileo.android.facebookrecipes.recipelist.RecipeListPresenter;
import edu.galileo.android.facebookrecipes.recipelist.RecipeListPresenterImpl;
import edu.galileo.android.facebookrecipes.recipelist.RecipeListRepository;
import edu.galileo.android.facebookrecipes.recipelist.RecipeListRepositoryImpl;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipesListView;
import edu.galileo.android.facebookrecipes.recipelist.ui.adapters.OnItemClickListener;
import edu.galileo.android.facebookrecipes.recipelist.ui.adapters.RecipesAdapter;

/**
 * Created by ykro.
 */
@Module
public class RecipeListModule {
    RecipesListView view;
    OnItemClickListener onItemClickListener;

    public RecipeListModule(RecipesListView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides @Singleton
    RecipesListView provideRecipeListView() {
        return this.view;
    }

    @Provides @Singleton
    RecipeListPresenter provideRecipeListPresenter(EventBus eventBus, RecipesListView view, RecipeListInteractor interactor) {
        return new RecipeListPresenterImpl(eventBus, view, interactor);
    }

    @Provides @Singleton
    RecipeListInteractor provideRecipeListInteractor(RecipeListRepository repository) {
        return new RecipeListInteractorImpl(repository);
    }

    @Provides @Singleton
    RecipeListRepository provideRecipeListRepository(EventBus eventBus) {
        return new RecipeListRepositoryImpl(eventBus);
    }

    @Provides @Singleton
    RecipesAdapter provideRecipesAdapter(List<Recipe> recipes, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        return new RecipesAdapter(recipes, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListener provideOnItemClickListener() {
        return this.onItemClickListener;
    }

    @Provides @Singleton
    List<Recipe> provideRecipesList() {
        return new ArrayList<Recipe>();
    }

}
