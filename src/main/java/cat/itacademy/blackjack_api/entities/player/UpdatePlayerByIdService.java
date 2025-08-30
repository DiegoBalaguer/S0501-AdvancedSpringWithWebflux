package cat.itacademy.blackjack_api.entities.player;

import cat.itacademy.blackjack_api.exception.InvalidInputException;
import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class


UpdatePlayerByIdService {

    private final PlayerRepository PLAYER_REPOSITORY;
    private final PlayerValidations PLAYER_VALIDATIONS;

    public UpdatePlayerByIdService(PlayerRepository playerRepository, PlayerValidations playerValidations) {
        this.PLAYER_REPOSITORY = playerRepository;
        this.PLAYER_VALIDATIONS = playerValidations;
    }

    public Mono<Player> findById(Long id) {
        return PLAYER_REPOSITORY.findById(id);
    }

    public Mono<Player> updatePlayer(Long id, UpdatePlayerDto updateDto) {
        return PLAYER_REPOSITORY.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(player -> {
                    if (updateDto.name() != null) {
                        return PLAYER_VALIDATIONS.validateName(updateDto.name())
                                .map(validatedName -> {
                                    player.setName(validatedName);
                                    return player;
                                });
                    }
                    return Mono.just(player);
                })
                .flatMap(player -> {
                    if (updateDto.balance() != null) {
                        return PLAYER_VALIDATIONS.validateBalance(updateDto.balance())
                                .map(validatedBalance -> {
                                    player.setBalance(validatedBalance);
                                    return player;
                                });
                    }
                    return Mono.just(player);
                })
                .flatMap(player -> {
                    if (updateDto.rankingScore() != null) {
                        return PLAYER_VALIDATIONS.validateRankingScore(updateDto.rankingScore())
                                .map(validatedRankingScore -> {
                                    player.setRankingScore(validatedRankingScore);
                                    return player;
                                });
                    }
                    return Mono.just(player);
                })
                .flatMap(player -> {
                    if (updateDto.email() != null) {
                        return updatePlayerEmailLogic(player, updateDto.email());
                    }
                    return Mono.just(player);
                })
                .flatMap(PLAYER_REPOSITORY::save);
    }

    public Mono<Player> updatePlayerName(Long id, String name) {
        UpdatePlayerDto dto = new UpdatePlayerDto(name, null, null, null);
        return updatePlayer(id, dto);
    }

    public Mono<Player> updatePlayerBalance(Long id, Long balance) {
        UpdatePlayerDto dto = new UpdatePlayerDto(null, balance, null, null);
        return updatePlayer(id, dto);
    }

    public Mono<Player> updatePlayerRankingScore(Long id, int rankingScore) {
        UpdatePlayerDto dto = new UpdatePlayerDto(null, null, rankingScore, null);
        return updatePlayer(id, dto);
    }

    public Mono<Player> updatePlayerEmail(Long id, String email) {
        UpdatePlayerDto dto = new UpdatePlayerDto(null, null, null, email);
        return updatePlayer(id, dto);
    }

    private Mono<Player> updatePlayerEmailLogic(Player player, String email) {
        if (email.equals(player.getEmail())) {
            return Mono.just(player);
        }

        return PLAYER_VALIDATIONS.validateEmail(email)
                .then(PLAYER_VALIDATIONS.validateEmailNotExists(email))
                .map(validatedEmail -> {
                    player.setEmail(validatedEmail);
                    return player;
                });
    }

    public Mono<Player> addPlayerBalance(long id, long amount) {
        return PLAYER_REPOSITORY.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(player -> {
                    long newBalance = player.getBalance() + amount;
                    player.setBalance(newBalance);
                    return PLAYER_REPOSITORY.save(player);
                });
    }

    public Mono<Player> subtractPlayerBalance(long id, long amount) {
        return PLAYER_REPOSITORY.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(player -> {
                    long newBalance = player.getBalance() - amount;
                    if (newBalance < 0) {
                        return Mono.error(new InvalidInputException("Balance cannot be negative"));
                    }
                    player.setBalance(newBalance);
                    return PLAYER_REPOSITORY.save(player);
                });
    }

    public Mono<Player> addPlayerRankingScore(long id, Integer ranking) {
        return PLAYER_REPOSITORY.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(player -> {
                    Integer newRanking = player.getRankingScore() + ranking;
                    player.setRankingScore(newRanking);
                    return PLAYER_REPOSITORY.save(player);
                });
    }

}