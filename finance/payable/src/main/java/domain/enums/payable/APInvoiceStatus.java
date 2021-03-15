package domain.enums.payable;

import domain.payables.APStandardInvoice;

public enum APInvoiceStatus {
  NEW {
    public InvoiceHoldType validate(APStandardInvoice invoice){
      if (!invoice.isAmountPositive()) {
        return InvoiceHoldType.HOLD_01;
      }

      if (!invoice.isSameLineAndHeaderAmount()) {
        return InvoiceHoldType.HOLD_02;
      }

      return null;
    }
  }, NEED_REVALIDATE{
    public InvoiceHoldType validate(APStandardInvoice invoice){
      if (invoice.isAmountPositive()) {
        return InvoiceHoldType.HOLD_01;
      }

      if (invoice.isSameLineAndHeaderAmount()) {
        return InvoiceHoldType.HOLD_02;
      }

      return null;
    }
  }, VALIDATED{
    public InvoiceHoldType validate(APStandardInvoice invoice){
      return null;
    }
  };

  public abstract InvoiceHoldType validate(APStandardInvoice invoice);


}
