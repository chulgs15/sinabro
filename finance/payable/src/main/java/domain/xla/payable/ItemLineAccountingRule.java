package domain.xla.payable;


import domain.embed.CurrencyAmount;
import domain.enums.FinancialAccount;
import domain.ledger.JournalLine;
import domain.payables.ItemLine;

import java.util.List;

public class ItemLineAccountingRule {
  private final ItemLine itemLine;

  public ItemLineAccountingRule(ItemLine itemLine) {
    this.itemLine = itemLine;
    itemLine.markPostTarget();
  }

  public List<JournalLine> generateJournalLines() {
    CurrencyAmount lineAmount = this.itemLine.getLineAmount();

    JournalLine itemJournalLine = JournalLine.builder()
        .enteredDebitAmount(lineAmount.getAmount())
        .accountedDebitAmount(lineAmount.getAmount())
        .accoutingDate(this.itemLine.getInvoiceEntity().getAccountingDate())
        .financialAccount(FinancialAccount.EXPENSE)
        .build();


    JournalLine liabJournalLine = JournalLine.builder()
        .enteredCreditAmount(lineAmount.getAmount())
        .accountedCreditAmount(lineAmount.getAmount())
        .accoutingDate(this.itemLine.getInvoiceEntity().getAccountingDate())
        .financialAccount(FinancialAccount.LIABILITY)
        .build();

    return List.of(itemJournalLine, liabJournalLine);
  }
}


