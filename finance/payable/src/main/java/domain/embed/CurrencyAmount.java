package domain.embed;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Embeddable
@Getter
public class CurrencyAmount {

  @Column(name = "currency_code")
  private String currency;

  @Column(name = "exchange_rate")
  private BigDecimal exchangeRate;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "functional_currency")
  private String functionalCurrency;

  @Column(name = "funcional_amount")
  private BigDecimal convertedAmount;

  public CurrencyAmount() {
  }

  public CurrencyAmount(String currency, BigDecimal exchangeRate, BigDecimal amount) {
    this.currency = currency;
    this.exchangeRate = exchangeRate;
    this.amount = amount;
    this.functionalCurrency = Currency.getInstance(Locale.getDefault()).getCurrencyCode();

    int roundPoint = NumberFormat.getCurrencyInstance().getMaximumFractionDigits();
    RoundingMode roundingMode = NumberFormat.getCurrencyInstance().getRoundingMode();

    this.convertedAmount = amount.multiply(exchangeRate)
        .setScale(roundPoint, roundingMode);

  }

  @Override
  public String toString() {
    return "CurrencyAmount{" +
        "currency='" + currency + '\'' +
        ", exchangeRate=" + exchangeRate +
        ", amount=" + amount +
        ", functionalCurrency='" + functionalCurrency + '\'' +
        ", convertedAmount=" + convertedAmount +
        '}';
  }
}
