package domain.enums;

import domain.payables.APInvoiceEntity;
import domain.payables.APStandardInvoice;

import java.math.BigDecimal;

public enum APInvoiceStatus {
  NEW {
    APInvoiceStatus validate(APStandardInvoice invoice){
      int index = invoice.getCurrencyAmount().getAmount().compareTo(BigDecimal.ONE);
      if ( index < 0 ) {
        return NEED_REVALIDATE;
      }
      return null;
    }
  }, NEED_REVALIDATE{
    APInvoiceStatus validate(APInvoiceEntity invoice){
      return null;
    }
  }, VALIDATED{
    APInvoiceStatus validate(APInvoiceEntity invoice){
      return null;
    }
  };


}
