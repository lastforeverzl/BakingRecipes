package com.zackyzhang.bakingrecipes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zackyzhang.bakingrecipes.ConnectionReceiver;
import com.zackyzhang.bakingrecipes.MyApplication;
import com.zackyzhang.bakingrecipes.R;
import com.zackyzhang.bakingrecipes.data.Recipe;
import com.zackyzhang.bakingrecipes.network.AsyncTaskCompleteListener;
import com.zackyzhang.bakingrecipes.network.RecipesAsyncTask;
import com.zackyzhang.bakingrecipes.ui.adapter.RecipesAdapter;
import com.zackyzhang.bakingrecipes.utils.DisplayUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements
        RecipesAdapter.RecipeAdapterOnClickHandler,
        ConnectionReceiver.ConnectionReceiverListener {

    private static final String TAG = "MainActivity";
    private static final String BUNDLE_RECYCLER_LAYOUT = "MainActivity.recycler.layout";

    private List<Recipe> mRecipes;
    private GridLayoutManager mLayoutManager;
    private RecipesAdapter mAdapter;

    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_recipes)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_no_connection)
    TextView noConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(mToolbar);
        ButterKnife.bind(this);
        checkConnection();

        setupRecyclerView();

        new RecipesAsyncTask(new RecipesAsyncTaskCompleteListener()).execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    private void setupRecyclerView() {
        int numberOfColumns = DisplayUtils.calculateNoOfColumns(this);
        mLayoutManager = new GridLayoutManager(this,
                numberOfColumns, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecipesAdapter(MainActivity.this, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected) {
            noConnection.setVisibility(View.VISIBLE);
        }
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Timber.tag(TAG).d(recipe.getName());
        Intent intent = RecipeStepActivity.newIntent(MainActivity.this, recipe);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectionListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected) {
            //show a No Internet Alert or Dialog
            if (mRecipes == null) {
                noConnection.setVisibility(View.VISIBLE);
            } else {
                noConnection.setVisibility(View.INVISIBLE);
            }
        } else {
            // dismiss the dialog or refresh the activity
            finish();
            startActivity(getIntent());
        }
    }

    public class RecipesAsyncTaskCompleteListener implements
            AsyncTaskCompleteListener<List<Recipe>> {

        @Override
        public void onTaskComplete(List<Recipe> result) {
            mRecipes = result;
            mAdapter.setData(result);
            hideProgressBar();
        }
    }

}
