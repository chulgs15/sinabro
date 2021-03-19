package domain.xla.payable;

import domain.ledger.JournalEntry;
import domain.ledger.JournalLine;
import domain.payables.ItemLine;
import domain.payables.StandardInvoice;
import domain.payables.TaxLine;

import java.util.List;

public class StandardInvoiceAccounting {

  private final StandardInvoice invoiceEntity;

  public StandardInvoiceAccounting(StandardInvoice invoiceEntity) {
    this.invoiceEntity = invoiceEntity;
  }

  public JournalEntry createAccounting() {
    JournalEntry journalEntry = JournalEntry.builder()
        .currencyCode(invoiceEntity.getCurrencyAmount().getCurrency())
        .journalName("test")
        .description("")
        .build();

    for (ItemLine itemLine : invoiceEntity.getItemLines()) {
      List<JournalLine> journalLines = new ItemLineAccountingRule(itemLine).generateJournalLines();
      journalEntry.addJournalLine(journalLines);

      for (JournalLine journalLine : journalLines) {
        journalLine.addJournalLink(new PayableJournalLink(itemLine, journalLine));
      }

      itemLine.markPostComplete();
    }

    for (TaxLine taxLine : invoiceEntity.getTaxLines()) {
      List<JournalLine> journalLines = new TaxLineAccountingRule(taxLine).generateJournalLines();
      journalEntry.addJournalLine(journalLines);

      for (JournalLine journalLine : journalLines) {
        journalLine.addJournalLink(new PayableJournalLink(taxLine, journalLine));
      }

      taxLine.markPostComplete();
    }


    return journalEntry;
  }

}
