package domain.xla.payable;


import domain.embed.CurrencyAmount;
import domain.enums.FinancialAccount;
import domain.ledger.JournalEntry;
import domain.ledger.JournalLine;
import domain.payables.AbstractAPInvoiceEntity;
import domain.payables.ItemLine;

import java.util.List;

public class InvoiceAccountingRule {
  private final ItemLine itemLines;
  private JournalEntry journalEntry;

  public JournalEntry createAccouting() {
    AbstractAPInvoiceEntity invoiceEntity = this.itemLines.getInvoiceEntity();
    CurrencyAmount invoiceCurrAmount = invoiceEntity.getCurrencyAmount();

    this.journalEntry = JournalEntry.builder()
        .currencyCode(invoiceCurrAmount.getCurrency())
        .journalName(invoiceEntity.getInvoiceId() + " Create Account")
        .build()
        .addJournalLine(generateJournalLines());

    return journalEntry;
  }

  public InvoiceAccountingRule(ItemLine itemLines) {
    this.itemLines = itemLines;
  }

  private List<JournalLine> generateJournalLines() {
    CurrencyAmount lineAmount = this.itemLines.getLineAmount();

    JournalLine itemJournalLine = JournalLine.builder()
        .enteredDebitAmount(lineAmount.getAmount())
        .accountedDebitAmount(lineAmount.getAmount())
        .accoutingDate(this.itemLines.getInvoiceEntity().getAccountingDate())
        .financialAccount(FinancialAccount.EXPENSE)
        .build();


    JournalLine liabJournalLine = JournalLine.builder()
        .enteredCreditAmount(lineAmount.getAmount())
        .accountedCreditAmount(lineAmount.getAmount())
        .accoutingDate(this.itemLines.getInvoiceEntity().getAccountingDate())
        .financialAccount(FinancialAccount.LIABILITY)
        .build();

    return List.of(itemJournalLine, liabJournalLine);


  }
}
