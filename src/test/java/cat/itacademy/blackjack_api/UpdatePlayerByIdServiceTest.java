package cat.itacademy.blackjack_api;

import cat.itacademy.blackjack_api.entities.player.*;

import cat.itacademy.blackjack_api.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdatePlayerByIdServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerValidations playerValidations;

    @InjectMocks
    private UpdatePlayerByIdService updatePlayerByIdService;

    private Player existingPlayer;

    @BeforeEach
    void setUp() {
        existingPlayer = Player.builder()
                .id(1L)
                .name("OldName")
                .email("old@example.com")
                .balance(500L)
                .rankingScore(50)
                .registrationDate(LocalDateTime.now())
                .build();
    }

    @Test
    public void updatePlayerName_shouldUpdateName_whenPlayerExists() {
        // Given: A player exists and a new name is provided
        String newName = "NewName";
        when(playerRepository.findById(1L)).thenReturn(Mono.just(existingPlayer));
        when(playerValidations.validateName(newName)).thenReturn(Mono.just(newName));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(existingPlayer));

        // When: The updatePlayerName method is called
        Mono<Player> result = updatePlayerByIdService.updatePlayerName(1L, newName);

        // Then: The player's name should be updated
        StepVerifier.create(result)
                .assertNext(player -> {
                    assertEquals(newName, player.getName());
                    assertEquals(existingPlayer.getEmail(), player.getEmail());
                })
                .verifyComplete();

        verify(playerRepository).findById(1L);
        verify(playerRepository).save(existingPlayer);
    }

    @Test
    public void updatePlayerBalance_shouldUpdateBalance_whenPlayerExists() {
        // Given: A player exists and a new balance is provided
        Long newBalance = 1000L;
        when(playerRepository.findById(1L)).thenReturn(Mono.just(existingPlayer));
        when(playerValidations.validateBalance(newBalance)).thenReturn(Mono.just(newBalance));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(existingPlayer));

        // When: The updatePlayerBalance method is called
        Mono<Player> result = updatePlayerByIdService.updatePlayerBalance(1L, newBalance);

        // Then: The player's balance should be updated
        StepVerifier.create(result)
                .assertNext(player -> {
                    assertEquals(newBalance, player.getBalance());
                })
                .verifyComplete();
    }

    @Test
    public void updatePlayer_shouldReturnError_whenPlayerNotFound() {
        // Given: A player with the given ID does not exist
        when(playerRepository.findById(10L)).thenReturn(Mono.empty());
        UpdatePlayerDto updateDto = new UpdatePlayerDto("NewName", null, null, null);

        // When: The updatePlayer method is called with a non-existent ID
        Mono<Player> result = updatePlayerByIdService.updatePlayer(10L, updateDto);

        // Then: The Mono should return a NotFoundException
        StepVerifier.create(result)
                .expectError(NotFoundException.class)
                .verify();

        verify(playerRepository, never()).save(any(Player.class));
    }
}

