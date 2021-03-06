package com.zackyzhang.bakingrecipes.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zackyzhang.bakingrecipes.R;
import com.zackyzhang.bakingrecipes.data.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lei on 7/12/17.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private static final String TAG = "RecipesAdapter";

    public interface RecipeAdapterOnClickHandler {
        void onItemClick(Recipe recipe);
    }

    private Context mContext;
    private List<Recipe> mRecipeList;
    private RecipeAdapterOnClickHandler mClickHandler;

    public RecipesAdapter(Context context, RecipeAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    public void setData(List<Recipe> recipes) {
        mRecipeList = recipes;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        if (!recipe.getImage().equals("")) {
            Picasso.with(mContext).load(recipe.getImage()).centerCrop().into(holder.recipeImage);
        } else {
            holder.recipeImage.setVisibility(View.INVISIBLE);
            holder.recipeName.setBackgroundResource(R.color.brown_light);
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.info_text)
        TextView recipeName;
        @BindView(R.id.info_image)
        ImageView recipeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card_view)
        public void onClick() {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeList.get(adapterPosition);
            mClickHandler.onItemClick(recipe);
        }
    }
}
