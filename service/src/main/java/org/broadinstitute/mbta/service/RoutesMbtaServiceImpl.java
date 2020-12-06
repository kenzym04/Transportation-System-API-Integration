package org.broadinstitute.mbta.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.broadinstitute.mbta.client.RestTemplateClientFactory;
import org.broadinstitute.mbta.core.Route;
import org.broadinstitute.mbta.core.Stop;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Colin Kegler
 * The Service method implementation to retrieve MBTA Routes
 */
public class RoutesMbtaServiceImpl implements RoutesMbtaService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    /*private final WebClient webClient;

    public RoutesMbtaServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }
    */
    public RoutesMbtaServiceImpl() {
        RestTemplateClientFactory rtcf = new RestTemplateClientFactory();
        this.restTemplate = rtcf.create();
    }

    /**
     * Retrieve a list of the routes on the MBTA subway
     *
     * @return a List<Route> or an empty list of size 0
     */
    public List<Route> getRoutes() {
        // use Optional to prevent returning a null list.
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String response =
                restTemplate.exchange(BASE_URL_ROUTES, HttpMethod.GET, entity, String.class).getBody();

        List<Route> results = new ArrayList<>();
        try {
            results = parse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final List<Route> finalResults = Optional.ofNullable(results).orElseGet(ArrayList::new);
        return finalResults;
    }

    public static List<Route> parse(String input) throws IOException {
        if (input == null) return null;

        List<Route> routeList = new ArrayList<>();

        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(input);
        JsonToken token = parser.nextToken();

        // Try find at least one object or array.
        while (!JsonToken.START_ARRAY.equals(token) && token != null) {
            token = parser.nextToken();
        }
        // No content found
        if (token == null) {
            return null;
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
                Route aRoute = mapper.readValue(parser, Route.class);
                routeList.add(aRoute);

                scanMore = true;
            } catch (JsonParseException e) {
                // log exception
                break;
            }
        } // end while true

        return routeList;
    }
}
