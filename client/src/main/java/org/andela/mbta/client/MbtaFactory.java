package org.andela.mbta.client;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Kennedy-Owiro
 *
 *  MBTA Service is the root interface for the "client" classes which implement it.
 *  MBTAService is essentially the root or beginning of the Factory method for Rest clients.
 */
public abstract class MbtaFactory {

    public static String BASE_URL_MBTA_V3 = "https://api-v3.mbta.com";
    //API_KEY: 56af6893161548189748efc6c6891df3
    public static String API_KEY = "56af6893161548189748efc6c6891df3";
    

    public abstract Object create();
}
