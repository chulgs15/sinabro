package domain.embed;

import domain.payables.exception.PayableErrors;
import domain.payables.exception.PayableException;
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

  @Column(name = "currency_code", nullable = false)
  private String currency;

  @Column(name = "exchange_rate", nullable = false)
  private BigDecimal exchangeRate;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Column(name = "functional_currency", nullable = false)
  private String functionalCurrency;

  @Column(name = "functional_amount", nullable = false)
  private BigDecimal convertedAmount;

  @Column(name = "rounding_amount")
  private BigDecimal roundingAmount;


  public CurrencyAmount() {
  }

  public CurrencyAmount(String currency, BigDecimal exchangeRate, BigDecimal amount) {
    this.currency = currency;
    this.exchangeRate = exchangeRate;
    this.amount = amount;

    isProperCurrencyFormat(currency, amount);

    this.functionalCurrency = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
    NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
    int roundPoint = currencyInstance.getMaximumFractionDigits();
    RoundingMode roundingMode = currencyInstance.getRoundingMode();

    this.convertedAmount = amount.multiply(exchangeRate)
        .setScale(roundPoint, roundingMode);
  }

  private CurrencyAmount(String currency, BigDecimal exchangeRate, BigDecimal amount,
                         String functionalCurrency, BigDecimal convertedAmount, BigDecimal roundingAmount) {
    this.currency = currency;
    this.exchangeRate = exchangeRate;
    this.amount = amount;
    this.functionalCurrency = functionalCurrency;
    this.convertedAmount = convertedAmount;
    this.roundingAmount = roundingAmount;
  }

  public void adjustDiff(BigDecimal roundingAmount) {
    this.roundingAmount = roundingAmount;
    this.convertedAmount = this.convertedAmount.add(roundingAmount);
  }

  public CurrencyAmount createReverseAmount() {
    return new CurrencyAmount(this.currency,
        this.exchangeRate,
        this.amount.negate(),
        this.functionalCurrency,
        this.convertedAmount.negate(),
        this.roundingAmount
    );
  }

  public static void isProperCurrencyFormat(String currency, BigDecimal amount) {
    int defaultFractionDigits = Currency.getInstance(currency).getDefaultFractionDigits();
    int compareTo = amount.setScale(defaultFractionDigits, RoundingMode.UP)
        .subtract(amount).compareTo(BigDecimal.ZERO);
    if (compareTo != 0) {
      throw new PayableException(PayableErrors.PAYABLE_00001);
    }
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
