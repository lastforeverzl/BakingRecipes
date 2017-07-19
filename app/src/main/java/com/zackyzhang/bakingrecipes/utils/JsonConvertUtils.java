package com.zackyzhang.bakingrecipes.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zackyzhang.bakingrecipes.data.Ingredient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 7/17/17.
 */

public class JsonConvertUtils {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static List<Ingredient> getIngredientsFromJson(String string) {
        Type listType = new TypeToken<ArrayList<Ingredient>>(){}.getType();
        return new Gson().fromJson(string, listType);
    }
}
