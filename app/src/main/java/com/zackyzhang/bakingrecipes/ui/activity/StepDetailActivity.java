package com.zackyzhang.bakingrecipes.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zackyzhang.bakingrecipes.R;
import com.zackyzhang.bakingrecipes.data.Step;
import com.zackyzhang.bakingrecipes.ui.adapter.StepsPagerAdapter;
import com.zackyzhang.bakingrecipes.ui.fragment.StepFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei on 7/13/17.
 */

public class StepDetailActivity extends AppCompatActivity {

    private static final String TAG = "StepDetailActivity";
    private static final String EXTRA_STEPS =
            "com.zackyzhang.bakingrecipes.ui.activity.steps";
    private static final String EXTRA_STEP_DETAIL =
            "com.zackyzhang.bakingrecipes.ui.activity.step_detail";

    private int mStepNumber;
    private List<Step> mSteps;
    private StepFragment mStepFragment;
    private List<Fragment> mFragmentList;
    private StepsPagerAdapter mStepsPagerAdapter;

    @BindView(R.id.vp_Pager)
    ViewPager mViewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;

    public static Intent newIntent(Context context, int stepNumber, ArrayList<Step> steps) {
        Intent i = new Intent(context, StepDetailActivity.class);
        i.putParcelableArrayListExtra(EXTRA_STEPS, steps);
        i.putExtra(EXTRA_STEP_DETAIL, stepNumber);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.step_toolbar_title));

        mStepNumber = getIntent().getIntExtra(EXTRA_STEP_DETAIL, 0);
        mSteps = getIntent().getParcelableArrayListExtra(EXTRA_STEPS);

        initializePaging();

    }

    private void initializePaging() {
        mFragmentList = new ArrayList<>();
        for (Step step : mSteps) {
            mFragmentList.add(StepFragment.newInstance(step));
        }
        mStepsPagerAdapter = new StepsPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mStepsPagerAdapter);
        mSlidingTabs.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(mStepNumber);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
