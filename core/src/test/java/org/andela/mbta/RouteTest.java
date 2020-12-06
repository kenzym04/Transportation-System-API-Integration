package org.andela.mbta;
/**
 * @author Kennedy-Owiro
 *
 */

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import org.andela.mbta.core.Route;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteTest {

    private static File file;
    private static final ObjectMapper mapper = new ObjectMapper();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        file = getFileFromResources();
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(Route.class, new RouteDeserializer(Route.class));
//        mapper.registerModule(module);
    }

    // get file from classpath, resources folder
    private File getFileFromResources() {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource("routes.json");
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
     * A simple test for successful reading of file, routes.json
     */
    @Test
    public void test_ReadingOfRoutesFile() {

        try {
            printFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert true;
    }

    /**
     * Reading from the file, routes.json, use as a test of parsing individual
     * tokens of routes and transform them into Route objects. The Jackson stream parsing
     * library parses json tokens into Route objects, using the custom RouteDeserializer
     * @throws IOException - error is thrown if Jackson stream token parsing fails
     */
    @Test
    public void test_DeserializeRoutesFromFile() throws IOException {
        if (file == null) return;

        List<Route> routeList = new ArrayList<>();

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
                Route r = mapper.readValue(parser, Route.class);
                routeList.add(r);

                scanMore = true;
            } catch (JsonParseException e) {
                // log exception
                break;
            }
        } // end while true

        assertEquals(2, routeList.size());
        assertEquals("Red Line", routeList.get(0).getLongName());
        assertEquals("Mattapan Trolley", routeList.get(1).getLongName());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
}