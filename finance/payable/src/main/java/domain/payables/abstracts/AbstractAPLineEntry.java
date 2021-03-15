package domain.payables.abstracts;


import domain.embed.CurrencyAmount;
import domain.payables.APStandardInvoice;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "invoice_line_type")
@SequenceGenerator(name = "ap_invoice_line_id_s", sequenceName = "ap_invoice_line_id_s", initialValue = 1, allocationSize = 1)
@Table(name = "ap_invoice_entries_lines_all")
@Getter
public class AbstractAPLineEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ap_invoice_line_id_s")
    @Column(name = "invoice_line_id")
    private Long lineId;

    @Embedded
    private CurrencyAmount lineAmount;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private APStandardInvoice invoiceEntity;

    public AbstractAPLineEntry(CurrencyAmount lineAmount, String description) {
        this.lineAmount = lineAmount;
        this.description = description;
    }

    public AbstractAPLineEntry() {
    }

    @Override
    public String toString() {
        return "AbstractAPLineEntry{" +
                "lineId=" + lineId +
                ", lineAmount=" + lineAmount +
                ", description='" + description + '\'' +
                '}';
    }
}
