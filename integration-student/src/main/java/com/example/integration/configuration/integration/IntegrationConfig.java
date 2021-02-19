package com.example.integration.configuration.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;



@Configuration
public class IntegrationConfig {

  @Bean
  public MessageChannel replyChannel() {
    return new DirectChannel();
  }

}
