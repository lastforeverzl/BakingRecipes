package com.zackyzhang.bakingrecipes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.zackyzhang.bakingrecipes.data.Ingredient;
import com.zackyzhang.bakingrecipes.utils.JsonConvertUtils;

import java.util.List;

import timber.log.Timber;

/**
 * Created by lei on 7/18/17.
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "ListRemoteViewsFactory";

    Context mContext;
    SharedPreferences mSharedPreferences;
    List<Ingredient> mIngredientList;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {
        Timber.tag(TAG).d("onDataSetChanged");
        mSharedPreferences = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String ingredients = mSharedPreferences.getString(
                mContext.getString(R.string.preference_ingredients_key), "");
        mIngredientList = JsonConvertUtils.getIngredientsFromJson(ingredients);

    }

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        if (mIngredientList == null) return 0;
        return mIngredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = mIngredientList.get(position);
        String quantityMeasure = ingredient.getQuantity() + " " + ingredient.getMeasure();
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget);
        views.setTextViewText(R.id.widget_ingredient, ingredient.getIngredient());
        views.setTextViewText(R.id.widget_quantity_measure, quantityMeasure);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}