package edu.galileo.android.facebookrecipes.recipemain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.login.LoginManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.facebookrecipes.FacebookRecipesApp;
import edu.galileo.android.facebookrecipes.R;
import edu.galileo.android.facebookrecipes.entities.Recipe;
import edu.galileo.android.facebookrecipes.lib.ImageLoader;
import edu.galileo.android.facebookrecipes.login.ui.LoginActivity;
import edu.galileo.android.facebookrecipes.recipelist.ui.RecipeListActivity;
import edu.galileo.android.facebookrecipes.recipemain.RecipeMainPresenter;
import edu.galileo.android.facebookrecipes.recipemain.di.RecipeMainComponent;

public class RecipeMainActivity extends AppCompatActivity implements RecipeMainView, SwipeGestureListener {
    @Bind(R.id.imgRecipe)
    ImageView imgRecipe;

    @Bind(R.id.imgKeep)
    ImageButton btnKeep;

    @Bind(R.id.imgDismiss)
    ImageButton btnDismiss;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.layoutContainer)
    RelativeLayout container;

    private Recipe currentRecipe;
    private ImageLoader imageLoader;
    private RecipeMainPresenter presenter;
    private RecipeMainComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);
        ButterKnife.bind(this);
        setupInjection();
        setupImageLoading();
        setupGestureDetection();

        presenter.onCreate();
        presenter.getNextRecipe();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipes_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_list) {
            navigateToListScreen();
            return true;
        } else if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void navigateToListScreen() {
        startActivity(new Intent(this, RecipeListActivity.class));
    }

    private void setupGestureDetection() {
        final GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        imgRecipe.setOnTouchListener(gestureListener);
    }

    private void setupImageLoading() {
        imageLoader.setOnFinishedImageLoadingListener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Snackbar.make(container, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                hideProgress();
                showUIElements();
                return false;
            }
        });
    }

    private void setupInjection() {
        FacebookRecipesApp app = (FacebookRecipesApp)getApplication();
        //app.getRecipeMainComponent(this, this).inject(this);
        component = app.getRecipeMainComponent(this, this);
        imageLoader = getImageLoader();
        presenter = getPresenter();
    }

    public ImageLoader getImageLoader() {
        return component.getImageLoader();
    }

    public RecipeMainPresenter getPresenter() {
        return component.getPresenter();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showUIElements() {
        btnKeep.setVisibility(View.VISIBLE);
        btnDismiss.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUIElements() {
        btnKeep.setVisibility(View.GONE);
        btnDismiss.setVisibility(View.GONE);
    }

    private void clearImage(){
        imgRecipe.setImageResource(0);
    }

    @Override
    public void saveAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_save);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearImage();
            }
        });
        imgRecipe.startAnimation(anim);
    }

    @Override
    public void dismissAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_dismiss);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearImage();
            }
        });
        imgRecipe.startAnimation(anim);
    }

    @Override
    @OnClick(R.id.imgKeep)
    public void onKeep() {
        if (currentRecipe != null) {
            presenter.saveRecipe(currentRecipe);
        }
    }

    @Override
    @OnClick(R.id.imgDismiss)
    public void onDismiss() {
        presenter.dismissRecipe();
    }

    @Override
    public void setRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
        imageLoader.load(imgRecipe, recipe.getImageURL());
    }

    @Override
    public void onGetRecipeError(String error) {
        String msgError = String.format(getString(R.string.recipemain_error), error);
        Snackbar.make(container, msgError, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipeSaved() {
        Snackbar.make(container, R.string.recipemain_notice_saved, Snackbar.LENGTH_SHORT).show();
    }
}
