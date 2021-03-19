package domain.xla.payable;

import domain.ledger.JournalLine;
import domain.payables.AbstractAPLineEntry;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "xla_ap_links_all")
@DiscriminatorValue("PAYABLE")
@Getter
public class PayableJournalLink extends JournalLink {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_line_id")
  private AbstractAPLineEntry lineEntry;

  public PayableJournalLink(AbstractAPLineEntry lineEntry, JournalLine journalLine) {
    super(journalLine);
    this.lineEntry = lineEntry;
  }

  public PayableJournalLink() {

  }
}
