package domain.payables;

import domain.embed.CurrencyAmount;
import domain.payables.abstracts.AbstractAPInvoiceEntity;
import domain.payables.abstracts.AbstractAPLineEntry;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "STANDARD")
public class APItemLine extends AbstractAPLineEntry {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private AbstractAPInvoiceEntity invoiceEntity;

    public APItemLine() {
        super();
    }

    public APItemLine(CurrencyAmount lineAmount, String description) {
        super(lineAmount, description);
        this.invoiceEntity = invoiceEntity;
    }

    void addInvoiceEntry(AbstractAPInvoiceEntity invoiceEntity) {
        this.invoiceEntity = invoiceEntity;
    }
}
