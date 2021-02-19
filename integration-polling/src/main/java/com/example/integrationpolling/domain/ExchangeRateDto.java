package com.example.integrationpolling.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Lob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Getter
public class ExchangeRateDto {

  private Map<String, Object> rates;
  private String base;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @Lob
  private byte[] lob;

  public ExchangeRateDto() {
  }

  @Override
  public String toString() {
    return "ExchangeRateDto{" +
        "rates='" + rates.keySet().size() + '\'' +
        ", base='" + base + '\'' +
        ", date=" + date +
        '}';
  }
}
