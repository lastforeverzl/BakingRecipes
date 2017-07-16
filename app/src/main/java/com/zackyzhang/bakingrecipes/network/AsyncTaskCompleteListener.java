package com.zackyzhang.bakingrecipes.network;

/**
 * Created by lei on 7/12/17.
 */

public interface AsyncTaskCompleteListener<T> {
    public void onTaskComplete(T result);
}
