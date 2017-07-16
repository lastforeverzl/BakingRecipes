package com.zackyzhang.bakingrecipes.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by lei on 7/12/17.
 */

public class DisplayUtils {

    private static final String TAG = "DisplayUtils";

    private DisplayUtils() {}

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        Log.d(TAG, "dpWidth = " + displayMetrics.widthPixels + "/" + displayMetrics.density +
                " = " + dpWidth);
        int scalingFactor = 300;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }
}