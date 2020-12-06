package org.andela.mbta.core;
/**
 * @author Kennedy Owiro
 * @version
 * @see https://api-v3.mbta.com/docs/swagger/index.html#/Stop
 * @link Stop.java
 *
 *  StopDeserializer is a custom deserializer transform JSON representations in a Stop object, which
 *  models an MBTA stop.
 *  Note - the deserialiation is based on stream parsing with Jackson. The array of data in a StopRoute json file
 *  is broken down into tokens of individual "Stops" and transformed into Stop objects.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

public class StopDeserializer extends StdDeserializer<Stop> {
//    private static ObjectMapper routeMapper = new ObjectMapper();

    public StopDeserializer() {
        this(null);
    }

    protected StopDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Stop deserialize(JsonParser jp, DeserializationContext deserializationContext)
            throws IOException {

        final JsonNode dataNode = jp.getCodec().readTree(jp);

        return new Stop(
                dataNode.get("id").asText(),
                dataNode.get("attributes").get("municipality").asText(),
                dataNode.get("attributes").get("name").asText(),
                dataNode.get("attributes").get("address").asText(),
                dataNode.get("attributes").get("latitude").asDouble(),
                dataNode.get("attributes").get("longitude").asDouble(),
                dataNode.get("attributes").get("location_type").asText()
             //   dataNode.get("relationships").get("route").get("data").get("id").asText()
        );
    }

}
