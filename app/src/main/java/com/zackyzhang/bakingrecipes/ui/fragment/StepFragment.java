package com.zackyzhang.bakingrecipes.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.zackyzhang.bakingrecipes.R;
import com.zackyzhang.bakingrecipes.data.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by lei on 7/13/17.
 */

public class StepFragment extends Fragment {

    private static final String TAG = "StepFragment";
    private static final String BUNDLE_STEP = "BundleStep";

    private Unbinder unbinder;
    private SimpleExoPlayer mExoPlayer;
    private Step mStep;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.description)
    TextView mDescription;

    public static StepFragment newInstance(Step step) {
        StepFragment stepFragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_STEP, step);
        stepFragment.setArguments(args);
        return stepFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mStep = getArguments().getParcelable(BUNDLE_STEP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Timber.tag(TAG).d(mStep.toString());
        String videoUrl = mStep.getVideoURL();
        if (videoUrl.isEmpty()) {
            videoUrl = mStep.getThumbnailURL();
        }
        String description = mStep.getDescription();
        if (!videoUrl.isEmpty())
            initializePlayer(Uri.parse(videoUrl));
        else
            mPlayerView.setVisibility(View.GONE);
        mDescription.setText(description);

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        releasePlayer();
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

            mPlayerView.setPlayer(mExoPlayer);

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            String userAgent = Util.getUserAgent(getActivity(), "BakingRecipes");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                    getActivity(), userAgent, bandwidthMeter);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource videoSource = new ExtractorMediaSource(
                    mediaUri, dataSourceFactory, extractorsFactory, null, null);
            mExoPlayer.prepare(new LoopingMediaSource(videoSource));
//            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

}
