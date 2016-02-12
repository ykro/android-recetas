package edu.galileo.android.facebookrecipes.recipelist.ui.adapters;

import edu.galileo.android.facebookrecipes.entities.Recipe;

/**
 * Created by ykro.
 */
public interface OnItemClickListener {
    void onItemClick(Recipe recipe);
    void onFavClick(Recipe recipe);
    void onShareClick(Recipe recipe);
    void onDeleteClick(Recipe recipe);
}
