package domain.xla.payable.exception;

public class XlaException extends RuntimeException {

  private String code;
  private String message;
  private String action;

  public XlaException() {
  }

  public XlaException(XlaErrors xlaErrors) {
    this.code = xlaErrors.name();
    this.message = xlaErrors.getMessage();
    this.action = xlaErrors.getAction();
  }

}
