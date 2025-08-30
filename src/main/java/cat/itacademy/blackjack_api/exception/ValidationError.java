package cat.itacademy.blackjack_api.exception;

public record ValidationError(String field, String message) {}
