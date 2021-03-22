import domain.embed.CurrencyAmount;
import domain.ledger.JournalEntry;
import domain.payables.APHold;
import domain.payables.APInvoiceStatus;
import domain.payables.InvoiceHoldType;
import domain.payables.StandardInvoice;
import domain.payables.exception.PayableException;
import domain.xla.payable.StandardInvoiceAccounting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class APStandardTest {

  private static EntityManager entityManager;
  private static EntityTransaction transaction;

  @BeforeAll
  public static void initialize() {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("hello");
    entityManager = entityManagerFactory.createEntityManager();
    transaction = entityManager.getTransaction();
  }

  @Test
  public void AP인보이스_금액_소수점_오류() {
    Assertions.assertThrows(PayableException.class, () -> {
      CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1000.1"));

      StandardInvoice invoice = new StandardInvoice.Builder()
          .vendorName("거래처 A")
          .taxClassificationCode(12312123456L)
          .currencyAmount(amount)
          .build();

      invoice.addItemLine(new BigDecimal("100.11"), "");
    });
  }

  @Test
  public void AP인보이스_생성_테스트() {
    transaction.begin();

    CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1000"));

    StandardInvoice invoice = new StandardInvoice.Builder()
        .vendorName("거래처 A")
        .taxClassificationCode(12312123456L)
        .currencyAmount(amount)
        .build();

    entityManager.persist(invoice);
    transaction.commit();
    System.out.println(invoice);

    Assertions.assertNotNull(invoice.getInvoiceId());
  }


  @Test
  public void AP인보이스_전표차변_생성_테스트() {
    transaction.begin();

    CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1000"));

    StandardInvoice invoice = new StandardInvoice.Builder()
        .vendorName("거래처 A")
        .taxClassificationCode(12312123456L)
        .currencyAmount(amount)
        .build()
        .addItemLine(new BigDecimal("300"), "")
        .addItemLine(new BigDecimal("700"), "가장 큰 라인");

    entityManager.persist(invoice);
    transaction.commit();
    System.out.println(invoice);

    Assertions.assertEquals(invoice.getItemLines().size(), 2);
  }

  @Test
  public void AP인보이스_검증_테스트() {
    transaction.begin();

    CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("-1000"));

    StandardInvoice invoice = new StandardInvoice.Builder()
        .vendorName("거래처 A")
        .taxClassificationCode(12312123456L)
        .currencyAmount(amount)
        .build()
        .addItemLine(new BigDecimal("300"), "")
        .addItemLine(new BigDecimal("700"), "가장 큰 라인");

    invoice.validate();
    System.out.println(invoice);

    entityManager.persist(invoice);
    entityManager.flush();
    entityManager.clear();
    transaction.commit();


    invoice = entityManager.find(StandardInvoice.class, invoice.getInvoiceId());

    List<APHold> holds = invoice.getHolds();

    Assertions.assertEquals(holds.size(), 1);
    Assertions.assertEquals(holds.get(0).getCode(), InvoiceHoldType.HOLD_01.getHoldCode());
  }


  @Test
  public void AP인보이스_차대변_검증_테스트() {
    transaction.begin();

    CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1000"));

    StandardInvoice invoice = new StandardInvoice.Builder()
        .vendorName("거래처 A")
        .taxClassificationCode(12312123456L)
        .currencyAmount(amount)
        .build()
        .addItemLine(new BigDecimal("300"), "")
        .addItemLine(new BigDecimal("300"), "");

    entityManager.persist(invoice);
    entityManager.flush();
    entityManager.clear();

    invoice = entityManager.find(StandardInvoice.class, invoice.getInvoiceId());

    invoice.validate();

    transaction.commit();


    List<APHold> holds = invoice.getHolds();

    Assertions.assertEquals(holds.size(), 1);
    Assertions.assertEquals(holds.get(0).getCode(), InvoiceHoldType.HOLD_02.getHoldCode());
  }

  @Test
  public void AP인보이스_세금계산_기능확인() {
    transaction.begin();

    CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1100"));

    StandardInvoice invoice = new StandardInvoice.Builder()
        .vendorName("거래처 A")
        .taxClassificationCode(12312123456L)
        .currencyAmount(amount)
        .taxRate(new BigDecimal("0.1"))
        .build()
        .addItemLine(new BigDecimal("300"), "")
        .addItemLine(new BigDecimal("700"), "");

    invoice.calculateTax();
    invoice.validate();

    entityManager.persist(invoice);
    entityManager.flush();
    entityManager.clear();
    transaction.commit();

    invoice = entityManager.find(StandardInvoice.class, invoice.getInvoiceId());

    List<APHold> holds = invoice.getHolds();

    Assertions.assertEquals(holds.size(), 0);
  }

  @Test
  public void AP인보이스_단수차이() {
    transaction.begin();

    CurrencyAmount amount = new CurrencyAmount("USD", new BigDecimal("1103.9"), new BigDecimal("2018552.00"));

    StandardInvoice invoice = new StandardInvoice.Builder()
        .vendorName("거래처 A")
        .taxClassificationCode(12312123456L)
        .currencyAmount(amount)
        .taxRate(new BigDecimal("0.1"))
        .build()
        .addItemLine(new BigDecimal("1835047.28"), "");

    invoice.calculateTax();
    invoice.validate();

    System.out.println(invoice);

    entityManager.persist(invoice);
    entityManager.flush();
    entityManager.clear();
    transaction.commit();

    invoice = entityManager.find(StandardInvoice.class, invoice.getInvoiceId());


    List<APHold> holds = invoice.getHolds();

    Assertions.assertEquals(holds.size(), 0);
  }

  @Test
  public void AP인보이스_취소() {
    transaction.begin();

    CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1100"));

    StandardInvoice invoice = new StandardInvoice.Builder()
        .vendorName("거래처 A")
        .taxClassificationCode(12312123456L)
        .currencyAmount(amount)
        .taxRate(new BigDecimal("0.1"))
        .build()
        .addItemLine(new BigDecimal("300"), "")
        .addItemLine(new BigDecimal("700"), "가장 큰 라인");


    invoice.calculateTax();
    invoice.validate();
    invoice.cancel();

    entityManager.persist(invoice);
    entityManager.flush();
    entityManager.clear();
    transaction.commit();

    invoice = entityManager.find(StandardInvoice.class, invoice.getInvoiceId());

    Assertions.assertEquals(invoice.getTotalLineEnteredAmount(), BigDecimal.ZERO);
    Assertions.assertEquals(invoice.getStatus(), APInvoiceStatus.CANCEL);
  }

  @Test
  public void AP인보이스_CA() {
    transaction.begin();

    for (int i = 0; i < 100; i++) {

      CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1100"));

      StandardInvoice invoice = new StandardInvoice.Builder()
          .vendorName("거래처 A")
          .taxClassificationCode(12312123456L)
          .currencyAmount(amount)
          .taxRate(new BigDecimal("0.1"))
          .build()
          .addItemLine(new BigDecimal("300"), "")
          .addItemLine(new BigDecimal("700"), "가장 큰 라인");


      invoice.calculateTax();
      invoice.validate();
      invoice.cancel();

      entityManager.persist(invoice);
      entityManager.flush();
      entityManager.clear();

      invoice = entityManager.find(StandardInvoice.class, invoice.getInvoiceId());

      StandardInvoiceAccounting standardInvoiceAccounting = new StandardInvoiceAccounting(invoice);
      JournalEntry accounting = standardInvoiceAccounting.createAccounting();

      entityManager.persist(accounting);
    }

    entityManager.flush();
    entityManager.clear();

    transaction.commit();
  }
}
