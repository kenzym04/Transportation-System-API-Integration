package org.andela.mbta;
/**
 * @author Kennedy-Owiro
 *
 *
 */
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.andela.mbta.core.Stop;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StopTest {

    private static File file;
    private static final ObjectMapper mapper = new ObjectMapper();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        file = getFileFromResources();
    }

    // get file from classpath, resources folder
    private File getFileFromResources() {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource("stops.json");
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    private static void printFile() throws IOException {

        if (file == null) return;

        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    /**
     * A simple test for successful reading of file, stop.json
     */
    @Test
    public void testReadingOfStopsFile() {

        try {
            printFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert true;
    }

    /**
     * Reading from the file, stops.json, use as a test of parsing individual
     * tokens of stops and transform them into Stop objects. The Jackson stream parsing
     * library parses json tokens into Stop objects, using the custom StopDeserializer
     * @throws IOException - error is thrown if Jackson stream token parsing fails
     */
    @Test
    public void test_DeserializeStopsFromFile() throws IOException {
        if (file == null) return;

        List<Stop> stopsList = new ArrayList<>();

        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createJsonParser(file);
        JsonToken token = parser.nextToken();

        // Try find at least one object or array.
        while (!JsonToken.START_ARRAY.equals(token) && token != null ) {
            token = parser.nextToken();
        }
        // No content found
        if (token == null) {
            return;
        }

        boolean scanMore = false;

        while (true) {
            // If the first token is the start of obejct ->
            // the response contains only one object (no array)
            // do not try to get the first object from array.
            try {
                if (!JsonToken.START_OBJECT.equals(token) || scanMore) {
                    token = parser.nextToken();
                }
                if (!JsonToken.START_OBJECT.equals(token)) {
                    break;
                }

                // Read token to parse one complete route, add route to list of routes.
                Stop aStop = mapper.readValue(parser, Stop.class);
                stopsList.add(aStop);

                scanMore = true;
            } catch (JsonParseException e) {
                // log exception
                break;
            }
        } // end while true

        assertEquals(2, stopsList.size());
        assertEquals("Alewife", stopsList.get(0).getName());
        assertEquals("Davis", stopsList.get(1).getName());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

}