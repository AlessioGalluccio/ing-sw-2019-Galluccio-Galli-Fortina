package it.polimi.se2019.cloneable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Used by Gson during serialization and deserialization.
 * An object can be serialized without some field or class.
 * This class help Gson to understand which field and class should be skipped
 * @author Galli
 */
public class SkinnyObjectExclusionStrategy implements ExclusionStrategy {

    /**
     * Valuate if a class is to skip during serialization or not, base on the notation @SkinnyObject
     * @param clazz the class to skip
     * @return true if class is marked with @SkinnyObject
     */
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(SkinnyObject.class) != null;
    }

    /**
     * Valuate if a field is to skip during serialization or not, base on the notation @SkinnyObject
     * @param f the field to skip
     * @return true if filed is marked with @SkinnyObject
     */
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(SkinnyObject.class) != null;
    }
}