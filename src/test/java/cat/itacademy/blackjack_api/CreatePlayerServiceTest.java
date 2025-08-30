package cat.itacademy.blackjack_api;

import cat.itacademy.blackjack_api.entities.player.*;
import cat.itacademy.blackjack_api.exception.AlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatePlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerValidations playerValidations;

    @Mock
    private CreatePlayerDtoMapper createPlayerDtoMapper;

    @InjectMocks
    private CreatePlayerService createPlayerService;

    private CreatePlayerDto createPlayerDto;
    private Player newPlayer;

    @BeforeEach
    void setUp() {
        createPlayerDto = new CreatePlayerDto("TestName", "test@example.com", 100L);
        newPlayer = Player.builder()
                .id(1L)
                .name("TestName")
                .email("test@example.com")
                .balance(100L)
                .rankingScore(0)
                .registrationDate(LocalDateTime.now())
                .build();
    }

    @Test
    public void execute_shouldCreateAndSavePlayer_whenValidData() {
        // Given: Validations pass and the repository saves the new player
        when(playerValidations.validateName(createPlayerDto.name())).thenReturn(Mono.just(createPlayerDto.name()));
        when(playerValidations.validateEmail(createPlayerDto.email())).thenReturn(Mono.just(createPlayerDto.email()));
        when(playerValidations.validateEmailNotExists(createPlayerDto.email())).thenReturn(Mono.just(createPlayerDto.email()));
        when(playerValidations.validateBalance(createPlayerDto.balance())).thenReturn(Mono.just(createPlayerDto.balance()));
        when(createPlayerDtoMapper.toPlayer(createPlayerDto)).thenReturn(newPlayer);
        when(playerRepository.save(newPlayer)).thenReturn(Mono.just(newPlayer));

        // When: The service's execute method is called
        Mono<Player> result = createPlayerService.execute(createPlayerDto);

        // Then: The resulting Mono should contain the saved player
        StepVerifier.create(result)
                .expectNext(newPlayer)
                .verifyComplete();

        // Verify that all methods were called as expected
        verify(playerValidations).validateName(createPlayerDto.name());
        verify(playerValidations).validateEmail(createPlayerDto.email());
        verify(playerValidations).validateEmailNotExists(createPlayerDto.email());
        verify(playerValidations).validateBalance(createPlayerDto.balance());
        verify(createPlayerDtoMapper).toPlayer(createPlayerDto);
        verify(playerRepository).save(newPlayer);
    }

    @Test
    public void execute_shouldReturnError_whenEmailAlreadyExists() {
        // Given: The email validation fails
        when(playerValidations.validateName(anyString())).thenReturn(Mono.just("TestName"));
        when(playerValidations.validateEmail(anyString())).thenReturn(Mono.just("test@example.com"));
        when(playerValidations.validateEmailNotExists(anyString())).thenReturn(Mono.error(new AlreadyExistsException("Email already exists: test@example.com")));
        when(playerValidations.validateBalance(anyLong())).thenReturn(Mono.just(100L));

        // When: The service's execute method is called
        Mono<Player> result = createPlayerService.execute(createPlayerDto);

        // Then: The Mono should return an AlreadyExistsException
        StepVerifier.create(result)
                .expectError(AlreadyExistsException.class)
                .verify();

        // Verify that the save method was not called
        verify(playerRepository, never()).save(any(Player.class));
    }
}
