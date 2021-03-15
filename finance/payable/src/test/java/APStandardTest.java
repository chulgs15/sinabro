import domain.embed.CurrencyAmount;
import domain.enums.payable.InvoiceHoldType;
import domain.payables.APHold;
import domain.payables.APItemLine;
import domain.payables.APStandardInvoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
    public void AP인보이스_생성_테스트() {
        transaction.begin();

        CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1000"));

        APStandardInvoice invoice = new APStandardInvoice.Builder()
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

        APStandardInvoice invoice = new APStandardInvoice.Builder()
                .vendorName("거래처 A")
                .taxClassificationCode(12312123456L)
                .currencyAmount(amount)
                .build();


        APItemLine item1 = new APItemLine(
                new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("300")), "");
        APItemLine item2 = new APItemLine(
                new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("700")), "가장 큰 라인");


        invoice.addLine(item1).addLine(item2);

        entityManager.persist(invoice);
        transaction.commit();
        System.out.println(invoice);

        Assertions.assertEquals(invoice.getItemLines().size(), 2);
    }

    @Test
    public void AP인보이스_검증_테스트() {
        transaction.begin();

        CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("-1000"));

        APStandardInvoice invoice = new APStandardInvoice.Builder()
                .vendorName("거래처 A")
                .taxClassificationCode(12312123456L)
                .currencyAmount(amount)
                .build();


        APItemLine item1 = new APItemLine(
                new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("300")), "");
        APItemLine item2 = new APItemLine(
                new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("700")), "가장 큰 라인");


        invoice.addLine(item1).addLine(item2);

        invoice.validate();
        System.out.println(invoice);

        entityManager.persist(invoice);
        entityManager.flush();
        transaction.commit();

        entityManager.clear();

        invoice = entityManager.find(APStandardInvoice.class, invoice.getInvoiceId());

        List<APHold> holds = invoice.getHolds();

        Assertions.assertEquals(holds.size(), 1);
        Assertions.assertEquals(holds.get(0).getCode(), InvoiceHoldType.HOLD_01.getHoldCode());
    }


    @Test
    public void AP인보이스_차대변_검증_테스트() {
        transaction.begin();

        CurrencyAmount amount = new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("1000"));

        APStandardInvoice invoice = new APStandardInvoice.Builder()
                .vendorName("거래처 A")
                .taxClassificationCode(12312123456L)
                .currencyAmount(amount)
                .build();

        APItemLine item1 = new APItemLine(
                new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("300")), "");
        APItemLine item2 = new APItemLine(
                new CurrencyAmount("KRW", new BigDecimal("1"), new BigDecimal("300")), "가장 큰 라인");

        invoice.addLine(item1).addLine(item2);

        System.out.println(invoice);

        entityManager.persist(invoice);
        entityManager.flush();
        entityManager.clear();

        invoice = entityManager.find(APStandardInvoice.class, invoice.getInvoiceId());

        invoice.validate();

        // entityManager.persist(invoice);
        transaction.commit();


        List<APHold> holds = invoice.getHolds();

        Assertions.assertEquals(holds.size(), 1);
        Assertions.assertEquals(holds.get(0).getCode(), InvoiceHoldType.HOLD_02.getHoldCode());
    }



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
