package com.example.integrationpolling.config;

import com.example.integrationpolling.domain.ExchangeRateDto;
import com.example.integrationpolling.domain.ExchangeRateMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.jpa.core.JpaExecutor;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.integration.jpa.outbound.JpaOutboundGateway;
import org.springframework.integration.jpa.support.PersistMode;
import org.springframework.messaging.support.GenericMessage;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

  private final EntityManager entityManager;

  @Bean
  public IntegrationFlow integrationFlow() {
    return IntegrationFlows.from(() -> new GenericMessage<>(""),
        e -> e.poller(p -> p.fixedDelay(30000)))
        .handle(httpHandler())
        .log()
        .transform(Transformers.fromJson(ExchangeRateDto.class))
        .log()
        .transform(e -> new ExchangeRateMetaData((ExchangeRateDto) e))
        .log()
        .handle(test(), e->e.transactional())
        .log()
        .get();
  }

  @Bean
  public HttpRequestExecutingMessageHandler httpHandler() {
    return Http.outboundGateway("https://api.exchangeratesapi.io/latest")
        .httpMethod(HttpMethod.GET)
        .expectedResponseType(String.class)
        .get();
  }

  @Bean
  public JpaOutboundGateway test() {
    return Jpa.outboundAdapter(entityManager)
        .entityClass(ExchangeRateMetaData.class)
        .persistMode(PersistMode.MERGE)
        .get();
  }

}
