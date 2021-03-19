package domain.payables.exception;

public enum PayableErrors {
  PAYABLE_00001("The amount is over Currency Code's FractionCode ", "Change Amount");

  private String message;
  private String action;

  PayableErrors(String message, String action) {
    this.message = message;
    this.action = action;
  }

  public String getMessage() {
    return message;
  }

  public String getAction() {
    return action;
  }
}
