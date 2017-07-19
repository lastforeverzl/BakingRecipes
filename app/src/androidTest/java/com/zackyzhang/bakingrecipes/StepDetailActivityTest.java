package com.zackyzhang.bakingrecipes;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.zackyzhang.bakingrecipes.data.Step;
import com.zackyzhang.bakingrecipes.ui.activity.StepDetailActivity;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by lei on 7/19/17.
 */

public class StepDetailActivityTest {

    private static final String EXTRA_STEPS =
            "com.zackyzhang.bakingrecipes.ui.activity.steps";
    private static final String EXTRA_STEP_DETAIL =
            "com.zackyzhang.bakingrecipes.ui.activity.step_detail";

    @Rule
    public ActivityTestRule<StepDetailActivity> mActivityTestRule =
            new ActivityTestRule<StepDetailActivity>(StepDetailActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent(InstrumentationRegistry.getContext(),StepDetailActivity.class);

                    ArrayList<Step> steps = new ArrayList<>();

                    for (int i = 0; i < 3; i++) {
                        Step step = new Step();
                        step.setId(i);
                        step.setDescription("Description");
                        step.setShortDescription("Short description");
                        step.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
                        step.setThumbnailURL("");
                        steps.add(step);
                    }
                    intent.putParcelableArrayListExtra(EXTRA_STEPS, steps);
                    intent.putExtra(EXTRA_STEP_DETAIL, 1);
                    return intent;
                }
            };

    @Test
    public void testViewPager() {
        onView(withId(R.id.vp_Pager)).check(matches(isDisplayed()));
        onView(withId(R.id.vp_Pager)).perform(swipeLeft());
        onView(allOf(withId(R.id.exo_play), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.description), isDisplayed())).check(matches(withText("Description")));
    }
}
