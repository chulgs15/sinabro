package domain.ledger;

import domain.enums.FinancialAccount;
import domain.xla.payable.JournalLink;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "gl_journal_lines")
@Getter
@SequenceGenerator(name = "gl_journal_line_s", sequenceName = "gl_journal_line_s", initialValue = 1, allocationSize = 50)
@ToString
public class JournalLine {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gl_journal_line_s")
  @Column(name = "journal_line_id")
  private Long journalLineId;

  @Column(name = "gl_date")
  private LocalDate accoutingDate;

  @Column(name = "account")
  @Enumerated(EnumType.STRING)
  private FinancialAccount financialAccount;

  @Column(name = "entered_dr")
  private BigDecimal enteredDebitAmount;

  @Column(name = "entered_cr")
  private BigDecimal enteredCreditAmount;

  @Column(name = "accounted_dr")
  private BigDecimal accountedDebitAmount;

  @Column(name = "accounted_cr")
  private BigDecimal accountedCreditAmount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "journal_header_id")
  private JournalEntry journalEntry;

  @OneToOne(mappedBy = "journalLine", cascade = CascadeType.ALL)
  private JournalLink journalLink;

  public void addJournalLink(JournalLink journalLink) {
    this.journalLink = journalLink;
  }

  public void addJournalEntry(JournalEntry journalEntry) {
    this.journalEntry = journalEntry;
  }

  public JournalLine() {
  }

  @Builder
  public JournalLine(LocalDate accoutingDate, FinancialAccount financialAccount, BigDecimal enteredDebitAmount,
                     BigDecimal enteredCreditAmount, BigDecimal accountedDebitAmount, BigDecimal accountedCreditAmount) {
    this.accoutingDate = accoutingDate;
    this.financialAccount = financialAccount;
    this.enteredDebitAmount = enteredDebitAmount;
    this.enteredCreditAmount = enteredCreditAmount;
    this.accountedDebitAmount = accountedDebitAmount;
    this.accountedCreditAmount = accountedCreditAmount;
  }
}
