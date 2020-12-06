package org.broadinstitute.mbta.service;

import org.broadinstitute.mbta.core.Route;

import java.util.List;
/**
 * @author Colin Kegler
 * The Service method specification to retrieve MBTA Routes
 */
public interface RoutesMbtaService {
    final String BASE_URL_ROUTES = "https://api-v3.mbta.com/routes?filter[type]=0,1";

    List<Route> getRoutes();
}
