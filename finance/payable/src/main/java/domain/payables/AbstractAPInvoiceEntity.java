package domain.payables;

import domain.embed.CurrencyAmount;
import lombok.Getter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "invoice_type")
@SequenceGenerator(name = "ap_invoice_id_s", sequenceName = "ap_invoice_id_s", initialValue = 1, allocationSize = 50)
@Table(name = "ap_invoice_entries_all")
@Getter
public abstract class AbstractAPInvoiceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ap_invoice_id_s")
  @Column(name = "invoice_id")
  private Long invoiceId;

  @Column(name = "vendor_name", nullable = false)
  private String vendorName;

  @Column(name = "tax_classification_code", nullable = false)
  private Long taxClassificationCode;

  @Column(name = "invoice_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private APInvoiceStatus status;

  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "invoice_amount"))
  @AttributeOverride(name = "convertedAmount", column = @Column(name = "invoice_func_amount"))
  private CurrencyAmount currencyAmount;

  @Column(name = "description")
  private String description;

  @Column(name = "tax_rate")
  private BigDecimal taxRate;

  @Column(name = "gl_date")
  private LocalDate AccountingDate;

  @OneToMany(mappedBy = "invoiceEntity", cascade = CascadeType.PERSIST)
  private List<APHold> holds = new ArrayList<>();

  public AbstractAPInvoiceEntity() {
  }

  public AbstractAPInvoiceEntity(String vendorName, Long taxClassificationCode, CurrencyAmount currencyAmount) {
    this.status = APInvoiceStatus.NEW;
    this.vendorName = vendorName;
    this.taxClassificationCode = taxClassificationCode;
    this.currencyAmount = currencyAmount;
  }

  public AbstractAPInvoiceEntity(String vendorName, Long taxClassificationCode, CurrencyAmount currencyAmount,
                                 BigDecimal taxRate, LocalDate AccountingDate) {
    this(vendorName, taxClassificationCode, currencyAmount);
    this.taxRate = taxRate;
    this.AccountingDate = AccountingDate;
  }


  public void addHold(InvoiceHoldType invoiceHoldType) {
    APHold hold = new APHold(invoiceHoldType, this);
    holds.add(hold);
    this.status = APInvoiceStatus.NEED_REVALIDATE;
  }

  void changeStatusToValidated() {
    this.status = APInvoiceStatus.VALIDATED;
  }

  void changeStatusToCancel() {
    this.status = APInvoiceStatus.CANCEL;
  }

  void changeInvoiceAmountZero() {
    this.currencyAmount = new CurrencyAmount(this.currencyAmount.getCurrency(),
        this.currencyAmount.getExchangeRate(), BigDecimal.ZERO);
  }

  @Override
  public String toString() {
    return "APInvoiceEntity{" +
        "invoiceId=" + invoiceId +
        ", vendorName='" + vendorName + '\'' +
        ", taxClassificationCode=" + taxClassificationCode +
        ", status=" + status +
        ", currencyAmount=" + currencyAmount +
        '}';
  }


}
