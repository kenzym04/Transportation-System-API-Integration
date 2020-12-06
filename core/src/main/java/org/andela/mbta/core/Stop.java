package org.andela.mbta.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ken Owiro
 * @see https://api-v3.mbta.com/docs/swagger/index.html#/Route
 * @link Route.java
 * A Stop object models an MBTA stop
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = StopDeserializer.class)
public class Stop {

    private String id;
    private String municipality; // "Cambridge"
    private String name; //"Alewife"
    private String address;
    //	"Alewife Brook Pkwy and Cambridge Park Dr, Cambridge, MA 02140"
    private double latitude; //	42.395428
    private double longitude; // -71.142483
    private String location_type; // 1
    //   private String route; // e.g. Red, Blue

    public Stop() {
    }

    @JsonCreator
    public Stop(@JsonProperty("id") String id,
                @JsonProperty("municipality") String municipality,
                @JsonProperty("name") String name,
                @JsonProperty("address") String address,
                @JsonProperty("latitude") double latitude,
                @JsonProperty("longitude") double longitude,
                @JsonProperty("location_type") String location_type
                //@JsonProperty("route") String route)
    ) {
        this.id = id;
        this.municipality = municipality;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location_type = location_type;
        //     this.route = route;
    }

    public String getId() {
        return id;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocation_type() {
        return location_type;
    }

//    public String getRoute() {
//        return route;
//    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stop{");
        sb.append("id='").append(id).append('\'');
        sb.append(", municipality='").append(municipality).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", location_type='").append(location_type).append('\'');
        //   sb.append(", route='").append(route).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
