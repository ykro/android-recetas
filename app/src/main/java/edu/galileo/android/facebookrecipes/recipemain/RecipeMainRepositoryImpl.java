package edu.galileo.android.facebookrecipes.recipemain;

import java.util.Random;

import edu.galileo.android.facebookrecipes.BuildConfig;
import edu.galileo.android.facebookrecipes.api.RecipeSearchResponse;
import edu.galileo.android.facebookrecipes.api.RecipeService;
import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.lib.EventBus;
import edu.galileo.android.facebookrecipes.recipemain.events.RecipeMainEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ykro.
 */
public class RecipeMainRepositoryImpl implements RecipeMainRepository {
    EventBus eventBus;
    RecipeService service;

    private final static int COUNT = 1;
    private final static String RECENT_SORT = "r";

    public RecipeMainRepositoryImpl(EventBus eventBus, RecipeService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getNextRecipe() {
        Random r = new Random();
        int recipePage = r.nextInt(100000);
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY, RECENT_SORT, COUNT, recipePage);
        call.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (response.isSuccess()) {
                    RecipeSearchResponse recipeSearchResponse = response.body();
                    if (recipeSearchResponse.getCount() == 0){
                        getNextRecipe();
                    } else {
                        Recipe recipe = recipeSearchResponse.getFirstRecipe();
                        if (recipe != null) {
                            post(recipe);
                        } else {
                            post(response.message(), RecipeMainEvent.NEXT_EVENT);
                        }

                    }
                } else {
                    post(response.message(), RecipeMainEvent.NEXT_EVENT);
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                post(t.getLocalizedMessage(), RecipeMainEvent.NEXT_EVENT);
            }
        });
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipe.save();
        post();
    }

    private void post() {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(RecipeMainEvent.SAVE_EVENT);
        eventBus.post(event);
    }

    private void post(String error, int type) {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(type);
        event.setError(error);
        eventBus.post(event);
    }

    private void post(Recipe recipe) {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(RecipeMainEvent.NEXT_EVENT);
        event.setRecipe(recipe);
        eventBus.post(event);
    }
}
