package com.zackyzhang.bakingrecipes.network;

/**
 * Created by lei on 7/12/17.
 */

public interface AsyncTaskCompleteListener<T> {
    void onTaskComplete(T result);
}
