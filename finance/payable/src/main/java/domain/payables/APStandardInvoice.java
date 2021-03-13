package domain.payables;


import lombok.Getter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ap_std_invoices_all")
@DiscriminatorValue(value = "STANDARD")
public class APStandardInvoice extends APInvoiceEntity{
  public APStandardInvoice() {
  }



}
