import org.example.pv.domain.LeaseContract;
import org.example.pv.domain.LeaseSchedule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.IntStream;

public class PVTest {
  @Test
  public void calculatePV_just_function() {
    double interestRate = 0.02d;
    double amount = 100000000d;
    double year = 2d;

    BigDecimal bigDecimal = BigDecimal.valueOf((amount * Math.pow((1d + interestRate), year)));
    System.out.println(bigDecimal.toBigInteger());
  }

  @Test
  public void calculateLeasePV_print() {
    double interestRate = 0.195;
    int periodCount = 12;
    long leaseTotalAmount = IntStream.rangeClosed(1, periodCount)
        .mapToObj(Long::valueOf)
        .reduce(Long::sum)
        .orElseThrow(() -> new RuntimeException("leaseTotalAmount Error"))
        * 100;

    LeaseContract contract = new LeaseContract(interestRate, periodCount, leaseTotalAmount);

    LocalDate startDate = LocalDate.of(2019, 1, 1);
    IntStream.rangeClosed(0, periodCount - 1)
        .boxed()
        .map(x -> new LeaseSchedule(startDate.plusMonths((long) x),
            (x+1) * 100))
        .forEach(contract::addSchedule);

    contract.calculatePV();

    contract.dump();
  }
}
