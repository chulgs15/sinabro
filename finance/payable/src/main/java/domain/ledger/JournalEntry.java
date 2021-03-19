package domain.ledger;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "gl_journal_headers")
@SequenceGenerator(name = "gl_journal_header_s", sequenceName = "gl_journal_header_s", initialValue = 1, allocationSize = 50)
@ToString
@Getter
public class JournalEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gl_journal_header_s")
  @Column(name = "journal_header_id")
  private Long journalHeaderId;

  @Column(name = "journal_name")
  private String journalName;

  @Column(name = "description")
  private String description;

  @Column(name = "currency_code")
  private String currencyCode;

  @OneToMany(mappedBy = "journalEntry", cascade = CascadeType.ALL)
  private List<JournalLine> journalLines = new ArrayList<>();

  public JournalEntry addJournalLine(List<JournalLine> lines) {
    for (JournalLine line : lines) {
      journalLines.add(line);
      line.addJournalEntry(this);
    }
    return this;
  }


  public JournalEntry() {
  }

  @Builder
  public JournalEntry(String journalName, String description, String currencyCode) {
    this.journalName = journalName;
    this.description = description;
    this.currencyCode = currencyCode;
  }

}
