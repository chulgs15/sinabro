package domain.xla.payable;


import domain.embed.CurrencyAmount;
import domain.enums.FinancialAccount;
import domain.ledger.JournalLine;
import domain.payables.TaxLine;

import java.util.List;

public class TaxLineAccountingRule {
  private final TaxLine taxLine;

  public TaxLineAccountingRule(TaxLine taxLine) {
    this.taxLine = taxLine;
    taxLine.markPostTarget();
  }

  public List<JournalLine> generateJournalLines() {
    CurrencyAmount lineAmount = this.taxLine.getLineAmount();

    JournalLine itemJournalLine = JournalLine.builder()
        .enteredDebitAmount(lineAmount.getAmount())
        .accountedDebitAmount(lineAmount.getAmount())
        .accoutingDate(this.taxLine.getInvoiceEntity().getAccountingDate())
        .financialAccount(FinancialAccount.EXPENSE)
        .build();


    JournalLine liabJournalLine = JournalLine.builder()
        .enteredCreditAmount(lineAmount.getAmount())
        .accountedCreditAmount(lineAmount.getAmount())
        .accoutingDate(this.taxLine.getInvoiceEntity().getAccountingDate())
        .financialAccount(FinancialAccount.LIABILITY)
        .build();

    return List.of(itemJournalLine, liabJournalLine);
  }
}


