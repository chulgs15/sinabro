package domain.xla.payable;

import domain.ledger.JournalLine;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "xla_links_all")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "application_name")
@SequenceGenerator(name = "xla_id_s", sequenceName = "xla_id_s", initialValue = 1, allocationSize = 50)
@Getter
public abstract class JournalLink {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xla_id_s")
  @Column(name = "link_id")
  private Long linkId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "journal_line_id")
  private JournalLine journalLine;

  public JournalLink(JournalLine journalLine) {
    this.journalLine = journalLine;
    journalLine.addJournalLink(this);
  }

  public JournalLink() {

  }

  void addJournalLine(JournalLine journalLine) {
    this.journalLine = journalLine;
  }

}
