package cat.itacademy.blackjack_api.exception;

public class EmptyFieldException extends RuntimeException {
  public EmptyFieldException(String message) {
    super(message);
  }
}
