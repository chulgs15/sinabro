package com.example.integrationpolling.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class ExchangeRate {
  private String currency;
  private Double rate;

  public ExchangeRate(String currency, Double rate) {
    this.currency = currency;
    this.rate = rate;
  }

  public ExchangeRate() {
  }
}
