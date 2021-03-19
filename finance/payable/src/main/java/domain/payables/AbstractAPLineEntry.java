package domain.payables;


import domain.embed.CurrencyAmount;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "invoice_line_type")
@SequenceGenerator(name = "ap_invoice_line_id_s", sequenceName = "ap_invoice_line_id_s", initialValue = 1, allocationSize = 50)
@Table(name = "ap_invoice_entries_lines_all")
@Getter
public abstract class AbstractAPLineEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ap_invoice_line_id_s")
    @Column(name = "invoice_line_id")
    private Long lineId;

    @Embedded
    private CurrencyAmount lineAmount;

    @Column(name = "description")
    private String description;

    @Column(name = "posted_flag")
    @Enumerated(EnumType.STRING)
    private PostedFlag postedYn;

    @Column(name = "gl_date")
    private LocalDate accountingDate;

    public AbstractAPLineEntry(CurrencyAmount lineAmount, String description) {
        this.lineAmount = lineAmount;
        this.description = description;
        this.postedYn = PostedFlag.NEW;
        this.accountingDate = LocalDate.now();
    }

    public AbstractAPLineEntry(CurrencyAmount lineAmount, String description, LocalDate accountingDate) {
        this.lineAmount = lineAmount;
        this.description = description;
        this.postedYn = PostedFlag.NEW;
        this.accountingDate = accountingDate;
    }


    public void markPostTarget() {
        postedYn = PostedFlag.PROCESSING;
    }

    public void markPostComplete() {
        postedYn = PostedFlag.COMPLETE;
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
