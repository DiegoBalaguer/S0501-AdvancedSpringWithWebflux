package cat.itacademy.blackjack_api.exception;

import java.util.List;

public record ValidationResponseError(List<ValidationError> message) {}
