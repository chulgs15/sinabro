package domain.payables;

public enum APInvoiceStatus {
  NEW {
    public InvoiceHoldType validate(StandardInvoice invoice) {
      if (!invoice.isAmountPositive()) {
        return InvoiceHoldType.HOLD_01;
      }

      if (!invoice.isSameLineAndHeaderAmount()) {
        return InvoiceHoldType.HOLD_02;
      }

      return null;
    }

    public void cancel(StandardInvoice invoice) {
    }
  }, NEED_REVALIDATE {
    public InvoiceHoldType validate(StandardInvoice invoice) {
      if (invoice.isAmountPositive()) {
        return InvoiceHoldType.HOLD_01;
      }

      if (invoice.isSameLineAndHeaderAmount()) {
        return InvoiceHoldType.HOLD_02;
      }

      return null;
    }

    public void cancel(StandardInvoice invoice) {

    }
  }, VALIDATED {
    public InvoiceHoldType validate(StandardInvoice invoice) {
      return null;
    }

    public void cancel(StandardInvoice invoice) {
      invoice.createReverseLine();
      invoice.changeInvoiceAmountToZero();
      invoice.changeStatusToCancel();
    }

  }, CANCEL {
    public InvoiceHoldType validate(StandardInvoice invoice) {
      return null;
    }

    public void cancel(StandardInvoice invoice) {

    }
  };

  public abstract InvoiceHoldType validate(StandardInvoice invoice);

  public abstract void cancel(StandardInvoice invoice);


}
