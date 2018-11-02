package com.rdelgatte.hexagonal.client.schemaorg;

import com.google.gson.JsonIOException;
import com.google.schemaorg.*;
import com.google.schemaorg.core.*;
import com.google.schemaorg.core.datatype.Text;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.List;

@Log4j2
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
            log.debug(name + "/" + customPropertyValue);
        } catch (JsonLdSyntaxException e) {
            // Errors related to JSON-LD format and schema.org terms in JSON-LD
        } catch (JsonIOException e) {
            // Errors related to JSON format
        }
    }

    @Test
    void Serialize() {
        JsonLdSerializer serializer = new JsonLdSerializer(true /* setPrettyPrinting */);
        DataFeed object =
                CoreFactory.newDataFeedBuilder()
                        .addJsonLdContext(
                                JsonLdFactory.newContextBuilder()
                                        .setBase("http://example.com/")
                                        .build())
                        .addDataFeedElement(
                                CoreFactory.newRecipeBuilder()
                                        .setJsonLdId("123456")
                                        .addName("recipe name")
                                        .addAuthor(CoreFactory.newPersonBuilder().addName("John Smith").build())
                                        .addIsFamilyFriendly(BooleanEnum.TRUE)
                                        .setJsonLdReverse(
                                                CoreConstants.PROPERTY_RECIPE,
                                                CoreFactory.newCookActionBuilder().setJsonLdId("54321").build())
                                        .build())
                        .build();
        try {
            String jsonLdStr = serializer.serialize(object);
            log.debug(jsonLdStr);
        } catch (JsonLdSyntaxException e) {
            // Errors related to JSON-LD format and schema.org terms in JSON-LD
        } catch (JsonIOException e) {
            // Errors related to JSON format
        }
    }

    @Test
    void Deserialize() {
        JsonLdSerializer serializer = new JsonLdSerializer(true /* setPrettyPrinting */);
        String jsonLd =
                "{\n"
                        + "  \"@context\": [\n"
                        + "    \"http://schema.org/\",\n"
                        + "    {\n"
                        + "      \"@base\": \"http://example.com/\"\n"
                        + "    }\n"
                        + "  ],\n"
                        + "  \"@type\": \"DataFeed\",\n"
                        + "  \"dataFeedElement\": {\n"
                        + "    \"@type\": \"Recipe\",\n"
                        + "    \"@id\": \"123456\",\n"
                        + "    \"name\": \"recipe name\",\n"
                        + "    \"author\": {\n"
                        + "      \"@type\": \"Person\",\n"
                        + "      \"name\": \"ABC DEF\"\n"
                        + "    },\n"
                        + "    \"isFamilyFriendly\": \"http://schema.org/True\",\n"
                        + "    \"@reverse\": {\n"
                        + "      \"recipe\": {\n"
                        + "        \"@type\": \"CookAction\",\n"
                        + "        \"@id\": \"54321\"\n"
                        + "      }\n"
                        + "    }\n"
                        + "  }\n"
                        + "}";
        try {
            List<Thing> actual = serializer.deserialize(jsonLd);
            if ("http://schema.org/DataFeed".equals(actual.get(0).getFullTypeName())) {
                DataFeed dataFeed = (DataFeed) actual.get(0);
                List<ValueType> contexts = dataFeed.getJsonLdContextList();
                String url = ((JsonLdContext) contexts.get(0)).getUrl();
                //String goog = ((JsonLdContext) contexts.get(1)).getTerm("goog");
                Recipe recipe = (Recipe) (dataFeed.getDataFeedElementList().get(0));
                String id = recipe.getJsonLdId();
                String recipeName = ((Text) recipe.getNameList().get(0)).getValue();
            }
        } catch (JsonLdSyntaxException e) {
            // Errors related to JSON-LD format and schema.org terms in JSON-LD
        } catch (JsonIOException e) {
            // Errors related to JSON format
        } catch (SchemaOrgException e) {

        }
    }
}
