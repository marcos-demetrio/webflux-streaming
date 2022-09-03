package com.example.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class AppConfig {
  @Bean
  public Sinks.Many<Joke> sink() {
    return Sinks.many().replay().latest();
  }

  @Bean
  public Flux<Joke> flux(Sinks.Many<Joke> sink) {
    return sink.asFlux().cache();
  }
}
