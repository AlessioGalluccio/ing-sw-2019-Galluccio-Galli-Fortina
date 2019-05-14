package it.polimi.se2019.cloneable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class SkinnyObjectExclusionStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(SkinnyObject.class) != null;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(SkinnyObject.class) != null;
    }
}