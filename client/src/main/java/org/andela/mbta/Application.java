package org.andela.mbta;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class                                                                    {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            log.info("==== RESTful API Response using Spring RESTTemplate START =======");
//            Quote quote = restTemplate.getForObject(
//                    "https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
//            log.info(quote.toString());
            log.info("==== RESTful API Response using Spring RESTTemplate END =======");
        };
    }

  /*
    @Override
    public void run(RestTemplate restTemplate) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Quote response = restTemplate.getForObject(
                "https://gturnquist-quoters.cfapps.io/api/random",
                Quote.class);
        log.info("==== RESTful API Response using Spring RESTTemplate START =======");
        log.info(response.toString());
        log.info("==== RESTful API Response using Spring RESTTemplate END =======");
    }

   */
}
