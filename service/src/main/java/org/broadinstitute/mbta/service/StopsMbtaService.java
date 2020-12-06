package org.broadinstitute.mbta.service;

import org.broadinstitute.mbta.core.Stop;

import java.io.IOException;
import java.util.List;
/**
 * @author Colin Kegler
 * @verion The Service method specification to retrieve MBTA Stops
 */
public interface StopsMbtaService {

    final String BASE_URL_STOPS = "https://api-v3.mbta.com/stops?filter[route_type]=0,1&include=route,parent_station";

    List<Stop> getStops();

}
