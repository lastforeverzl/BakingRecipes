package com.zackyzhang.bakingrecipes.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zackyzhang.bakingrecipes.R;
import com.zackyzhang.bakingrecipes.data.Recipe;
import com.zackyzhang.bakingrecipes.ui.adapter.IngredientsAdapter;
import com.zackyzhang.bakingrecipes.ui.adapter.RecipeStepsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by lei on 7/12/17.
 */

public class RecipeFragment extends Fragment {

    private static final String TAG = "RecipeFragment";
    private static final String BUNDLE_RECIPE = "BundleRecipe";

    private Unbinder unbinder;
    private StepClickListener mStepClickListener;
    private Recipe mRecipe;
    private IngredientsAdapter mIngredientsAdapter;
    private RecipeStepsAdapter mStepsAdapter;

    @BindView(R.id.rv_ingredients)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.rv_steps)
    RecyclerView stepsRecyclerView;

    public interface StepClickListener {
        void stepClicked(int stepNumber);
    }

    public static RecipeFragment newInstance(Recipe recipe) {
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_RECIPE, recipe);
        recipeFragment.setArguments(args);
        return recipeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepClickListener) {
            mStepClickListener = (StepClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRecipe = getArguments().getParcelable(BUNDLE_RECIPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Timber.tag(TAG).d(mRecipe.getName());

        setupRecyclerView();
        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    private void setupRecyclerView() {
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mIngredientsAdapter = new IngredientsAdapter(getContext());
        mIngredientsAdapter.setData(mRecipe.getIngredients());
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);

        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mStepsAdapter = new RecipeStepsAdapter(getContext(), onItemClickListener);
        mStepsAdapter.setData(mRecipe.getSteps());
        stepsRecyclerView.setAdapter(mStepsAdapter);
    }

    private RecipeStepsAdapter.RecipeStepsOnClickListener onItemClickListener =
            new RecipeStepsAdapter.RecipeStepsOnClickListener() {
                @Override
                public void onItemClick(int stepNumber) {
                    mStepClickListener.stepClicked(stepNumber);
                }
            };

}
