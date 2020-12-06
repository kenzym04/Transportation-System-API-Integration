 
**Engineering Interview Take-Home Coding Exercise**
---------------------------------------------------

**Problem description**
-----------------------

This is a coding exercise where you integrate with the Boston transportation system website https://mbta.com and use the data it provides to answer questions. 

Boston's transportation system, the MBTA (https://mbta.com/), has a website with APIs
https://api-v3.mbta.com/docs/swagger/index.html.
You will not need an API key, but you might get rate-limited without one.

The MBTA's documentation 
https://api-v3.mbta.com/docs/swagger/index.html is written using OpenAPI/Swagger.
   
**Java version:  1.8.0_151.**
-----------------------------
The above Java version was used to implement and compile this exercise.

The IDE used for this project is IntelliJ v 2020.2.3 Ultimate edition, and this git repository contains all of the project libraries and artifacts needed to compile and execute the project.

**Project Structure**
------------------------

This maven project organised in a three-tier structure, where each module corresponds to a layer of the application.  This layering depicts a conventional hierarchical enterprise package format, such that a specific functionality is performed by a distict layer of concern. This design approach tries to maintain the independence of each layer so that libraries and functionality can easily be swapped in or out. Each layer has its own pom.xml configuration file, but the overall project has a root level pom.xml file as the parent.

The layers include:

- client (/client/src/main/java/org/andela/mbta/client)
    - a client layer responsible for creating REST clients.  Note: SpringBoot has the older RestTemplate client implementation and a more recent WebClient rest. So, isolating rest-client functionality in this layer would allow one to use either type of rest-client
    
- service (/service/src/main/java/org/andela/mbta/service)
    - a service layer which is responsible for retrieving a specific type of MBTA information such as mbta Routes or Stops 
    
- core (/core/src/main/java/org/andela/mbta/core)
    - The business objects or POJOs are placed here. There is basically a mapping between JSON entities and their corresponding Java object. The mapping of JSON entities to Java objects occurs through deserialization.

**Evaluation of MBTA V3 API via Postman**
----------------------------------------------

There is a communication platform for API development known as Postman(https://www.postman.com/). Postmn users can quickly and easily send REST and SOAP  requests directly within Postman. I used postman to evaluate JSON information retrieved from MBTA site to determine how it can be leveraged for business use.

As a result I found the below two URL commands to be significant as entry points.
- For Routes. **GET** `https://api-v3.mbta.com/routes?filter[type]=0,1`
- For Stops. **GET** `https://api-v3.mbta.com/stops?filter[route_type]=0,1&include=route,parent_station`


**Code Exercise**
-----------------

***QUE: 1***
------------

``` 
Write a program that retrieves data representing all, what we'll call "subway" routes: "Light Rail" (type 0) and “Heavy Rail” (type 1). The program should list their “long names” on the console. Partial example of long name output: Red Line, Blue Line, Orange Line...
There are two ways to filter results for subway-only routes. Think about the two options below and choose:
	1. Download all results from https://api-v3.mbta.com/routes then filter locally
	2. Rely on the server API (i.e., https://api-v3.mbta.com/routes?filter[type]=0,1) to filter before results are received
	Please document your decision and your reasons for it.
``` 
**Answer: Second Option** - Utilizing the server to filter the results is more efficient since the returned data set is reduced in size. The file holding the subway route data is large; it will require an additional operation to parse the subway route file to get the information we are interested in. Downloading the file locally will take enormous time. It will involve parsing the entire MBTA file and then selectively filtering to get the information we are interested in.
The use of server-side filtering saves bandwidth and computation.

***QUE: 2***
------------

```
Extend your program so it displays the following additional information.
1. The name of the subway route with the most stops as well as a count of its stops.
2. The name of the subway route with the fewest stops as well as a count of its stops.
3. A list of the stops that connect two or more subway routes along with the relevant route names for each of those stops.
```
**Answer** :  With a list of subway stops (i.e. filter[route_type]=0,1) and the routes associated with each stop, do a frequency count using Java Streams. Stream the filtered list, create a map where (key = route name, value = frequency count) and then sort the  map by value and sorting in reverse from the highest to the lowest.  The resulting map will have the first map entry being the stop with the highest number of connections, decreasing to the last map entry that will have the stop with the lowest number of connections.

To get the stops with more than 2 connections, filter the resulting map so that it only has stops with a route count >= 2.

***QUE: 3***
------------

```
Extend your program again such that the user can provide any two stops on the subway routes you listed for question 1.
List a rail route you could travel to get from one stop to the other. We will not evaluate your solution based upon the efficiency or cleverness of your route-finding solution. Pick a simple solution that answers the question. We will want you to understand and be able to explain how your algorithm performs.
Examples:
	1. Davis to Kendall -> Redline
	2. Ashmont to Arlington -> Redline, Greenline
```

***Tests***
-----------

1.**Route Test**
----------------

#### test_ReadingOfRoutesFile() 

	- Performs a simple test for successful reading of file, routes.json

#### test_DeserializeRoutesFromFile()

	- Reads from the file, routes.json, used as a test of parsing individual tokens of routes and transform them into Route objects. 
	- The Jackson stream parsing library parses json tokens into Route objects, using the custom RouteDeserializer.
	- @throws IOException - error is thrown if Jackson stream token parsing fails.
     

2.**Stop Test**
----------------
#### testReadingOfStopsFile()

	- Performs a simple test for successful reading of file, stops.json

#### test_DeserializeStopsFromFile()

	- Reads from the file, stops.json, used as a test of parsing individual tokens of stops and transform them into Stop objects. 
	- The Jackson stream parsing library parses json tokens into Stop objects, using the custom StopDeserializer
	- @throws IOException - error is thrown if Jackson stream token parsing fails
     

