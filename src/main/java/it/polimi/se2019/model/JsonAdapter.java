package it.polimi.se2019.model;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Used by Gson to serialize/deserialize concrete class that are saved through an interface or an abstract class
 * @param <T> abstract class or interface extended/implemented by the concrete class
 * @author Galli
 */
public class JsonAdapter<T>
        implements JsonSerializer<T>, JsonDeserializer<T> {

    /**
     * Create a serializer for Gson for object saved thought the interface they implement.
     * Works whit abstract classes too.
     * @param object The object which implements the interface
     * @param interfaceType the interface implemented
     * @param context
     * @return The serializer for that object
     */
    @Override
    public final JsonElement serialize(final T object, final Type interfaceType, final JsonSerializationContext context) {
        final JsonObject member = new JsonObject();

        member.addProperty("type", object.getClass().getName());

        member.add("data", context.serialize(object));

        return member;
    }

    /**
     * Create a deserializer for Gson for object saved thought the interface they implement.
     * Works whit abstract classes too.
     * @param elem The Json element of the object which implements the interface
     * @param interfaceType the interface implemented
     * @param context
     * @return The serializer for that object
     */
    @Override
    public final T deserialize(final JsonElement elem, final Type interfaceType, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject member = (JsonObject) elem;
        final JsonElement typeString = get(member, "type");
        final JsonElement data = get(member, "data");
        final Type actualType = typeForName(typeString);

        return context.deserialize(data, actualType);
    }

    private Type typeForName(final JsonElement typeElem) {
        try
        {
            return Class.forName(typeElem.getAsString());
        }
        catch (ClassNotFoundException e)
        {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(final JsonObject wrapper, final String memberName) {
        final JsonElement elem = wrapper.get(memberName);

        if (elem == null)
        {
            throw new JsonParseException(
                    "no '" + memberName + "' member found in json file.");
        }
        return elem;
    }

}