import domain.embed.CurrencyAmount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class ApInvoiceTest {
  @Test
  public void currentCurrency_and_Format() {
    System.out.println(NumberFormat.getCurrencyInstance(Locale.US).getMaximumFractionDigits());
  }

  @Test
  public void embedAmount_check() {
    CurrencyAmount amount = new CurrencyAmount(
        "USD", new BigDecimal("1234.1"), new BigDecimal("10")
    );
 }
}
