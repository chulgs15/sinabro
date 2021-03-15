package domain.payables.abstracts;

import domain.embed.CurrencyAmount;
import domain.enums.payable.APInvoiceStatus;
import domain.enums.payable.InvoiceHoldType;
import domain.payables.APHold;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "invoice_type")
@SequenceGenerator(name = "ap_invoice_id_s", sequenceName = "ap_invoice_id_s", initialValue = 1, allocationSize = 1)
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
  private CurrencyAmount currencyAmount;

  @Column(name="description")
  private String description;

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

  public void addHold(InvoiceHoldType invoiceHoldType) {
    APHold hold = new APHold(invoiceHoldType, this);
    holds.add(hold);
    this.status = APInvoiceStatus.NEED_REVALIDATE;
  }

  public void validateOK() {
    this.status = APInvoiceStatus.VALIDATED;
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
