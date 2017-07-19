package com.zackyzhang.bakingrecipes;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zackyzhang.bakingrecipes.data.Ingredient;
import com.zackyzhang.bakingrecipes.data.Recipe;
import com.zackyzhang.bakingrecipes.data.Step;
import com.zackyzhang.bakingrecipes.ui.activity.RecipeStepActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by lei on 7/19/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeStepActivityTest {

    private static final String EXTRA_RECIPE_DETAIL =
            "com.zackyzhang.bakingrecipes.ui.activity.recipe_detail";
    private String snackBarMessage = "Ingredient list is saved!";
    private String recipeName = "Test";
    private String ingredientName = "Eggs";
    private String measure = "Unit";
    private String shortDescription = "short description";

    @Rule
    public ActivityTestRule<RecipeStepActivity> mActivityTestRule =
            new ActivityTestRule<RecipeStepActivity>(RecipeStepActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent(InstrumentationRegistry.getContext(),RecipeStepActivity.class);
                    Recipe recipe = new Recipe();

                    Ingredient ingredient = new Ingredient();
                    ingredient.setIngredient(ingredientName);
                    ingredient.setMeasure(measure);
                    ingredient.setQuantity(5);
                    List<Ingredient> ingredients = new ArrayList<>();
                    ingredients.add(ingredient);
                    Step step = new Step();
                    step.setId(0);
                    step.setShortDescription(shortDescription);
                    step.setDescription("description");
                    step.setThumbnailURL("");
                    step.setVideoURL("");
                    List<Step> steps = new ArrayList<>();
                    steps.add(step);

                    recipe.setId(0);
                    recipe.setImage("");
                    recipe.setName(recipeName);
                    recipe.setServings(8);
                    recipe.setIngredients(ingredients);
                    recipe.setSteps(steps);
                    intent.putExtra(EXTRA_RECIPE_DETAIL, recipe);
                    return intent;
                }
            };


    @Test
    public void testFab(){
        onView(withId(R.id.fab)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(snackBarMessage)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testFragmentVisible() {
        onView(withId(R.id.recipes_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickRecyclerView() {
        onView(withId(R.id.rv_ingredients)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
