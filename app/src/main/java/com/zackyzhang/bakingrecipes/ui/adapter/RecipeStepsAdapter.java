package com.zackyzhang.bakingrecipes.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zackyzhang.bakingrecipes.R;
import com.zackyzhang.bakingrecipes.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lei on 7/13/17.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {

    private static final String TAG = "RecipeStepsAdapter";

    private Context mContext;
    private RecipeStepsOnClickListener mClickListener;
    private List<Step> mSteps;

    public interface RecipeStepsOnClickListener {
        void onItemClick(int stepNumber);
    }

    public RecipeStepsAdapter(Context context, RecipeStepsOnClickListener listener) {
        mContext = context;
        mClickListener = listener;
    }

    public void setData(List<Step> stepList) {
        mSteps = stepList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = mSteps.get(position);
        String shortDescription;
        if (position == 0) {
            shortDescription = step.getShortDescription();
        } else {
            shortDescription = position + ". " + step.getShortDescription();
        }
        holder.shortDescription.setText(shortDescription);
    }

    @Override
    public int getItemCount() {
        if (mSteps == null) return 0;
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_step)
        TextView shortDescription;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_step)
        public void onItemClick() {
            int adapterPosition = getAdapterPosition();
            Step step = mSteps.get(adapterPosition);
            mClickListener.onItemClick(step.getId());
        }
    }

}
