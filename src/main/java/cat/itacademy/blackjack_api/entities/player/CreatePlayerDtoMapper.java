package cat.itacademy.blackjack_api.entities.player;

import cat.itacademy.blackjack_api.config.properties.AppProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreatePlayerDtoMapper {

    private final AppProperties appProperties;

    public CreatePlayerDtoMapper(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public Player toPlayer(CreatePlayerDto createPlayerDto) {
        return Player.builder()
                .name(createPlayerDto.name())
                .email(createPlayerDto.email())
                .balance(createPlayerDto.balance() != null ? createPlayerDto.balance() : appProperties.getInitBalance())
                .rankingScore(0)
                .registrationDate(LocalDateTime.now())
                .build();
    }
}