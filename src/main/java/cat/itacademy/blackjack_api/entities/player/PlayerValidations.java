package cat.itacademy.blackjack_api.entities.player;

import cat.itacademy.blackjack_api.exception.AlreadyExistsException;
import cat.itacademy.blackjack_api.exception.InvalidInputException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PlayerValidations {

    private final PlayerRepository PLAYER_REPOSITORY;

    public PlayerValidations(PlayerRepository playerRepository) {
        this.PLAYER_REPOSITORY = playerRepository;
    }

    public Mono<String> validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Mono.error(new InvalidInputException("Player name cannot be empty."));
        }
        return Mono.just(name);
    }

    public Mono<Long> validateBalance(Long balance) {
        if (balance < 0) {
            return Mono.error(new InvalidInputException("Balance cannot be negative."));
        }
        return Mono.just(balance);
    }

    public Mono<Integer> validateRankingScore(int rankingScore) {
        if (rankingScore < 0) {
            return Mono.error(new InvalidInputException("Ranking score cannot be negative."));
        }
        return Mono.just(rankingScore);
    }

    public Mono<String> validateEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (email == null || !email.matches(regex)) {
            return Mono.error(new InvalidInputException("Invalid email format."));
        }
        return Mono.just(email);
    }

    public Mono<String> validateEmailNotExists(String email) {
        return PLAYER_REPOSITORY.findByEmail(email)
                .flatMap(player -> Mono.error(new AlreadyExistsException("Email already exists: " + email)))
                .thenReturn(email);
    }
}