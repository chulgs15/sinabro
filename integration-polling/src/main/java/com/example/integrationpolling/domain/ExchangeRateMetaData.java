package com.example.integrationpolling.domain;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "exchange_rate_meta")
public class ExchangeRateMetaData {

  @Id
  @Column(name = "exchange_date")
  private LocalDate date;

  private Integer count;

  private String base;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "exchange_rate", joinColumns = @JoinColumn(name = "exchange_date"))
  private List<ExchangeRate> exchangeRates;

  public ExchangeRateMetaData() {
  }

  public ExchangeRateMetaData(ExchangeRateDto dto) {
    this.date = dto.getDate();
    this.count = dto.getRates().keySet().size();
    this.base = dto.getBase();
    this.exchangeRates = dto.getRates().keySet().stream()
        .map(e -> new ExchangeRate(e, (Double) dto.getRates().get(e)))
        .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return "ExchangeRateMetaData{" +
        "date=" + date +
        ", count=" + count +
        ", base='" + base + '\'' +
        '}';
  }
}

