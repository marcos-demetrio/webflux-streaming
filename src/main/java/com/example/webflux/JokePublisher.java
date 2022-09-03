package com.example.webflux;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Sinks;

@Service
public class JokePublisher {
  private static final String JOKE_API_ENDPOINT = "https://joke.deno.dev/";

  private WebClient webClient;

  @Autowired private Sinks.Many<Joke> sink;

  @PostConstruct
  private void init() {
    this.webClient = WebClient.builder().baseUrl(JOKE_API_ENDPOINT).build();
  }

  @Scheduled(fixedRate = 3000)
  public void publish() {
    this.webClient.get().retrieve().bodyToMono(Joke.class).subscribe(this.sink::tryEmitNext);
  }
}
