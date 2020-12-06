package org.andela.mbta.client;

import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;

/**
 * @author Kennedy-Owiro
 * @version The WebClient is a modern, alternative HTTP client to RestTemplate.
 * Not only does it provide a traditional synchronous API, but it also supports
 * an efficient non-blocking and asynchronous approach.
 * RestTemplate will be deprecated in future versions.
 */
public class WebClientFactory extends MbtaFactory {
    @Override
    public WebClient create() {
        return WebClient
                .builder()
                .baseUrl(BASE_URL_MBTA_V3)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("apiKey", API_KEY);
                    httpHeaders.add("", "");
                })
                .defaultUriVariables(Collections.singletonMap("url", BASE_URL_MBTA_V3))
                .build();
    }
}
