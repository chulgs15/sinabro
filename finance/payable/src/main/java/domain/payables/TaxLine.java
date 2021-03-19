package domain.payables;

import domain.embed.CurrencyAmount;
import lombok.Getter;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "TAX")
@DiscriminatorOptions(force=true)
@Getter
public class TaxLine extends AbstractAPLineEntry {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private AbstractAPInvoiceEntity invoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_item_line_id")
    private ItemLine itemLine;

    public TaxLine() {

    }

    public TaxLine(CurrencyAmount lineAmount, String description, AbstractAPInvoiceEntity invoiceEntity, ItemLine itemLine) {
        super(lineAmount, description, invoiceEntity.getAccountingDate());
        this.invoiceEntity = invoiceEntity;
        this.itemLine = itemLine;
    }

}
