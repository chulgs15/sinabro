package domain.payables;

import domain.embed.CurrencyAmount;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "invoice_type")
@SequenceGenerator(name = "ap_invoice_id_s", sequenceName = "ap_invoice_id_s", initialValue = 1, allocationSize = 1)
@Table(name = "ap_invoice_entries_all")
@Getter
public abstract class APInvoiceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ap_invoice_id_s")
  @Column(name = "invoice_id")
  private Long invoiceId;

  @Column(name = "vendor_name")
  private String vendorName;

  @Column(name = "tax_classification_code")
  private Long taxClassificationCode;

  @Embedded
  private CurrencyAmount currencyAmount;

  public APInvoiceEntity() {
  }
}
