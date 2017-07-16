package com.zackyzhang.bakingrecipes.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zackyzhang.bakingrecipes.R;
import com.zackyzhang.bakingrecipes.data.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei on 7/13/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private static final String TAG = "IngredientsAdapter";

    private Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientsAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.ingredientText.setText(ingredient.getIngredient());
        String quantityMeasure = ingredient.getQuantity() + " " + ingredient.getMeasure();
        holder.quantityMeasureText.setText(quantityMeasure);
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null)   return 0;
        return mIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingredient)
        TextView ingredientText;
        @BindView(R.id.tv_quantity_measure)
        TextView quantityMeasureText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
