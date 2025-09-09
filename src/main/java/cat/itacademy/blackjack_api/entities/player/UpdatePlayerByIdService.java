package cat.itacademy.blackjack_api.entities.player;

import cat.itacademy.blackjack_api.exception.InvalidInputException;
import cat.itacademy.blackjack_api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UpdatePlayerByIdService {

    private final PlayerRepository playerRepository;
    private final PlayerValidations playerValidations;

    public Mono<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Mono<Player> updatePlayer(Long id, UpdatePlayerDto updateDto) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(player -> {
                    if (updateDto.name() != null) {
                        return playerValidations.validateName(updateDto.name())
                                .map(validatedName -> {
                                    player.setName(validatedName);
                                    return player;
                                });
                    }
                    return Mono.just(player);
                })
                .flatMap(player -> {
                    if (updateDto.balance() != null) {
                        return playerValidations.validateBalance(updateDto.balance())
                                .map(validatedBalance -> {
                                    player.setBalance(validatedBalance);
                                    return player;
                                });
                    }
                    return Mono.just(player);
                })
                .flatMap(player -> {
                    if (updateDto.rankingScore() != null) {
                        return playerValidations.validateRankingScore(updateDto.rankingScore())
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
                .flatMap(playerRepository::save);
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

        return playerValidations.validateEmail(email)
                .then(playerValidations.validateEmailNotExists(email))
                .map(validatedEmail -> {
                    player.setEmail(validatedEmail);
                    return player;
                });
    }

    public Mono<Player> addPlayerBalance(long id, long amount) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(player -> {
                    long newBalance = player.getBalance() + amount;
                    player.setBalance(newBalance);
                    return playerRepository.save(player);
                });
    }

    public Mono<Player> subtractPlayerBalance(long id, long amount) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(player -> {
                    long newBalance = player.getBalance() - amount;
                    if (newBalance < 0) {
                        return Mono.error(new InvalidInputException("Balance cannot be negative"));
                    }
                    player.setBalance(newBalance);
                    return playerRepository.save(player);
                });
    }

    public Mono<Player> addPlayerRankingScore(long id, Integer ranking) {
        return playerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Player not found with id: " + id)))
                .flatMap(player -> {
                    Integer newRanking = player.getRankingScore() + ranking;
                    player.setRankingScore(newRanking);
                    return playerRepository.save(player);
                });
    }

}