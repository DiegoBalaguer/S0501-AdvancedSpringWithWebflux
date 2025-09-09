package cat.itacademy.blackjack_api.entities.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreatePlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerValidations playerValidations;
    private final CreatePlayerDtoMapper createPlayerDtoMapper;

    public Mono<Player> execute(CreatePlayerDto createPlayerDto) {
        return validatePlayerCreation(createPlayerDto)
                .flatMap(this::createAndSavePlayer);
    }

    private Mono<CreatePlayerDto> validatePlayerCreation(CreatePlayerDto createPlayerDto) {
        return playerValidations.validateName(createPlayerDto.name())
                .then(playerValidations.validateEmail(createPlayerDto.email()))
                .then(playerValidations.validateEmailNotExists(createPlayerDto.email()))
                .then(playerValidations.validateBalance(createPlayerDto.balance()))
                .thenReturn(createPlayerDto);
    }

    private Mono<Player> createAndSavePlayer(CreatePlayerDto createPlayerDto) {
        Player newPlayer = createPlayerDtoMapper.toPlayer(createPlayerDto);
        return playerRepository.save(newPlayer);
    }
}
