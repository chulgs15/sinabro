package domain.xla.payable.exception;

public enum XlaErrors {
  XLA_00001("This Transaction cannot CA", "Change status");

  private String message;
  private String action;

  XlaErrors(String message, String action) {
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
