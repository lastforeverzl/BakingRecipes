package com.zackyzhang.bakingrecipes.network;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zackyzhang.bakingrecipes.data.Recipe;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;

/**
 * Created by lei on 7/12/17.
 */

public class RecipesAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {

    private AsyncTaskCompleteListener<List<Recipe>> mAsyncTaskCompleteListener;

    public RecipesAsyncTask(AsyncTaskCompleteListener listener) {
        mAsyncTaskCompleteListener = listener;
    }

    @Override
    protected List<Recipe> doInBackground(Void... params) {
        Response response = Api.instance().execute();
        if (!response.isSuccessful()) {
            return null;
        }
        final Gson gson = new Gson();
        Type listType = new TypeToken<List<Recipe>>(){}.getType();
        List<Recipe> recipes = gson.fromJson(response.body().charStream(), listType);
        return recipes;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        super.onPostExecute(recipes);
        mAsyncTaskCompleteListener.onTaskComplete(recipes);

    }
}
