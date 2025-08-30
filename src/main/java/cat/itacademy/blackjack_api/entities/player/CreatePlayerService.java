package cat.itacademy.blackjack_api.entities.player;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreatePlayerService {

    private final PlayerRepository PLAYER_REPOSITORY;
    private final PlayerValidations PLAYER_VALIDATIONS;
    private final CreatePlayerDtoMapper CREATE_PLAYER_DTO_MAPPER;

    public CreatePlayerService(PlayerRepository playerRepository, PlayerValidations playerValidations, CreatePlayerDtoMapper createPlayerDtoMapper) {
        this.PLAYER_REPOSITORY = playerRepository;
        this.PLAYER_VALIDATIONS = playerValidations;
        this.CREATE_PLAYER_DTO_MAPPER = createPlayerDtoMapper;
    }

    public Mono<Player> execute(CreatePlayerDto createPlayerDto) {
        return validatePlayerCreation(createPlayerDto)
                .flatMap(this::createAndSavePlayer);
    }

    private Mono<CreatePlayerDto> validatePlayerCreation(CreatePlayerDto createPlayerDto) {
        return PLAYER_VALIDATIONS.validateName(createPlayerDto.name())
                .then(PLAYER_VALIDATIONS.validateEmail(createPlayerDto.email()))
                .then(PLAYER_VALIDATIONS.validateEmailNotExists(createPlayerDto.email()))
                .then(PLAYER_VALIDATIONS.validateBalance(createPlayerDto.balance()))
                .thenReturn(createPlayerDto);
    }

    private Mono<Player> createAndSavePlayer(CreatePlayerDto createPlayerDto) {
        Player newPlayer = CREATE_PLAYER_DTO_MAPPER.toPlayer(createPlayerDto);
        return PLAYER_REPOSITORY.save(newPlayer);
    }
}
