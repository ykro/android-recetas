package edu.galileo.android.facebookrecipes.recipemain;

/**
 * Created by ykro.
 */
public class GetNextRecipeInteractorImpl implements GetNextRecipeInteractor {
    RecipeMainRepository repository;

    public GetNextRecipeInteractorImpl(RecipeMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getNextRecipe();
    }

}
