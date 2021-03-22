package domain.payables;

import domain.embed.CurrencyAmount;
import lombok.Getter;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "ITEM")
@DiscriminatorOptions(force=true)
@Getter
public class ItemLine extends AbstractAPLineEntry {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private AbstractAPInvoiceEntity invoiceEntity;

    @OneToMany(mappedBy = "itemLine")
    private List<TaxLine> taxLines = new ArrayList<>();

    public ItemLine() {

    }



    void addTaxline(TaxLine taxLine) {
        taxLines.add(taxLine);
    }

    public ItemLine(CurrencyAmount lineAmount, String description) {
        super(lineAmount, description);
    }

    public ItemLine(CurrencyAmount lineAmount, String description, AbstractAPInvoiceEntity invoiceEntity) {
        super(lineAmount, description, invoiceEntity.getAccountingDate());
        this.invoiceEntity = invoiceEntity;

    }

    void addInvoiceEntry(AbstractAPInvoiceEntity invoiceEntity) {
        this.invoiceEntity = invoiceEntity;
    }
}
