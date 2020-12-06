package org.andela.mbta.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

/**
 * @author Kennedy-Owiro
 * @version
 * https://api-v3.mbta.com/docs/swagger/index.html#/Route
 * @link Route.java
 *
 * A Route object, models an MBTA route
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = RouteDeserializer.class)
public class Route {

    private String id;
    private String color;
    private String description;
    private String[] directionDestinations; // ["Forest Hills","Oak Grove"]
    private String[] directionNames; // [ "South", "North" ]
    private String fareClass; // "Rapid Transit",
    private String longName; // "Orange Line",
    private String shortName;
    private String sortOrder;
    private int type; // 0 = "light rail", 1 = "heavy rail"

    @JsonCreator
    public Route(@JsonProperty("id") String id,
                 @JsonProperty("color")
                         String color,
                 @JsonProperty("description")
                         String description,
                 @JsonProperty("direction_destinations")
                         String[] directionDestinations,
                 @JsonProperty("direction_names")
                         String[] directionNames,
                 @JsonProperty("fare_class") String fareClass,
                 @JsonProperty("long_name") String longName,
                 @JsonProperty("short_name") String shortName,
                 @JsonProperty("sort_order") String sortOrder,
                 @JsonProperty("type") int type) {
        this.id = id;
        this.color = color;
        this.description = description;
        this.directionDestinations = directionDestinations;
        this.directionNames = directionNames;
        this.fareClass = fareClass;
        this.longName = longName;
        this.shortName = shortName;
        this.sortOrder = sortOrder;
        this.type = type;
    }

    public Route() {
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String[] getDirectionDestinations() {
        return directionDestinations;
    }

    public String[] getDirectionNames() {
        return directionNames;
    }

    public String getFareClass() {
        return fareClass;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Route{");
        sb.append("id='").append(id).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", directionDestinations=").append(Arrays.toString(directionDestinations));
        sb.append(", directionNames=").append(Arrays.toString(directionNames));
        sb.append(", fareClass='").append(fareClass).append('\'');
        sb.append(", longName='").append(longName).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", sortOrder='").append(sortOrder).append('\'');
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
