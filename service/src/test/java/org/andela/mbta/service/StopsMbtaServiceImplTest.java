package org.andela.mbta.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.andela.mbta.client.RestTemplateClientFactory;
import org.andela.mbta.client.WebClientFactory;
import org.andela.mbta.core.Stop;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StopsMbtaServiceImplTest {
    private WebClient webClient;
    private RestTemplate restTemplate;

    private Map<String, Long> stopsByLine;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        WebClientFactory wcf = new WebClientFactory();
        webClient = wcf.create();

        RestTemplateClientFactory rtcf = new RestTemplateClientFactory();
        restTemplate = rtcf.create();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        webClient = null;
        restTemplate = null;
    }

    @Test
    void test_getStops() {
        StopsMbtaServiceImpl stopsService = new StopsMbtaServiceImpl();
        final List<Stop> stopList = stopsService.getStops();
        // Determine that the number of subway MBTA stops is approximately 254 +/- 10
        assertEquals(254, stopList.size(), 10);
    }


    @Test
    void test_RestTemplateValidConnection() {

        final String baseUrl = "https://api-v3.mbta.com/stops?filter[route_type]=0,1&include=route,parent_station";
        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void findMaxStops(){
        StopsMbtaServiceImpl stopsService = new StopsMbtaServiceImpl();
        final List<Stop> stopList = stopsService.getStops();

        stopsByLine = stopList
                .stream()
                .collect(Collectors
                        .groupingBy(e -> e.getName(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


    }
}