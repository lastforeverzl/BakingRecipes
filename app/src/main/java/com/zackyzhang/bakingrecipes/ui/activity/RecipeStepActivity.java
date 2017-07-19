package com.zackyzhang.bakingrecipes.ui.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zackyzhang.bakingrecipes.IngredientWidgetProvider;
import com.zackyzhang.bakingrecipes.R;
import com.zackyzhang.bakingrecipes.data.Recipe;
import com.zackyzhang.bakingrecipes.data.Step;
import com.zackyzhang.bakingrecipes.ui.fragment.RecipeFragment;
import com.zackyzhang.bakingrecipes.ui.fragment.StepFragment;
import com.zackyzhang.bakingrecipes.utils.JsonConvertUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei on 7/12/17.
 */

public class RecipeStepActivity extends AppCompatActivity implements
        RecipeFragment.StepClickListener,
        View.OnClickListener {

    private static final String TAG = "RecipeStepActivity";

    private static final String EXTRA_RECIPE_DETAIL =
            "com.zackyzhang.bakingrecipes.ui.activity.recipe_detail";

    private RecipeFragment recipeFragment;
    private StepFragment stepFragment;
    private Recipe mRecipe;
    private boolean mTwoPane;

    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    public static Intent newIntent(Context context, Recipe recipe) {
        Intent i = new Intent(context, RecipeStepActivity.class);
        i.putExtra(EXTRA_RECIPE_DETAIL, recipe);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecipe = getIntent().getParcelableExtra(EXTRA_RECIPE_DETAIL);
        getSupportActionBar().setTitle(mRecipe.getName());

        if (findViewById(R.id.step_detail_fragment) != null) {
            mTwoPane = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            recipeFragment = (RecipeFragment) fragmentManager.findFragmentById(R.id.recipes_fragment);
            stepFragment = (StepFragment) fragmentManager.findFragmentById(R.id.step_detail_fragment);

            if (recipeFragment == null) {
                recipeFragment = RecipeFragment.newInstance(mRecipe);
                fragmentManager.beginTransaction()
                        .add(R.id.recipes_fragment, recipeFragment)
                        .commit();
            }
            if (stepFragment == null) {
                stepFragment = StepFragment.newInstance(mRecipe.getSteps().get(0));
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_fragment, stepFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            recipeFragment = (RecipeFragment) fragmentManager.findFragmentById(R.id.recipes_fragment);

            if (recipeFragment == null) {
                recipeFragment = RecipeFragment.newInstance(mRecipe);
                fragmentManager.beginTransaction()
                        .add(R.id.recipes_fragment, recipeFragment)
                        .commit();
            }
        }
        fab.setOnClickListener(this);
    }

    @Override
    public void stepClicked(int stepNumber) {
        if (mTwoPane) {
            stepFragment = StepFragment.newInstance(mRecipe.getSteps().get(stepNumber));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_fragment, stepFragment)
                    .commit();
        } else {
            ArrayList<Step> steps = (ArrayList<Step>) mRecipe.getSteps();
            Intent intent = StepDetailActivity.newIntent(this, stepNumber, steps);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        saveIngredients();
        Snackbar.make(findViewById(R.id.activity_recipe), getString(R.string.desired_recipe_saved), Snackbar.LENGTH_SHORT).show();
        notifyWidget();
    }

    private void saveIngredients() {
        String ingredients = JsonConvertUtils.toJson(mRecipe.getIngredients());
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.preference_ingredients_key), ingredients);
        editor.putString(getString(R.string.preference_recipe_name_key), mRecipe.getName());
        editor.apply();
    }

    private void notifyWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

        Intent intent = new Intent(this, IngredientWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        sendBroadcast(intent);
    }
}
