package domain.payables.exception;

public class PayableException extends RuntimeException {

  private String code;
  private String message;
  private String action;

  public PayableException() {
  }

  public PayableException(PayableErrors payableErrors) {
    this.code = payableErrors.name();
    this.message = payableErrors.getMessage();
    this.action = payableErrors.getAction();
  }

}
