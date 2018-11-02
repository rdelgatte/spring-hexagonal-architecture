package com.rdelgatte.hexagonal.client.schemaorg;

import com.google.gson.JsonIOException;
import com.google.schemaorg.JsonLdSerializer;
import com.google.schemaorg.JsonLdSyntaxException;
import com.google.schemaorg.core.CoreFactory;
import com.google.schemaorg.core.Thing;
import com.google.schemaorg.core.datatype.Text;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ThingTest {

    @Test
    void SerializeThing() {
        JsonLdSerializer serializer = new JsonLdSerializer(true /* setPrettyPrinting */);
        Thing object =
                CoreFactory.newThingBuilder()
                        .addUrl("http://example.com")
                        .addName("John")
                        .addProperty("customPropertyName", "customPropertyValue")
                        .build();
        try {
            String jsonLdStr = serializer.serialize(object);
            List<Thing> list = serializer.deserialize(jsonLdStr);
            Thing thing = list.get(0);
            String name = ((Text) thing.getNameList().get(0)).getValue();
            String customPropertyValue =
                    ((Text) thing.getProperty("customPropertyName").get(0)).getValue();
        } catch (JsonLdSyntaxException e) {
            // Errors related to JSON-LD format and schema.org terms in JSON-LD
        } catch (JsonIOException e) {
            // Errors related to JSON format
        }
    }
}
