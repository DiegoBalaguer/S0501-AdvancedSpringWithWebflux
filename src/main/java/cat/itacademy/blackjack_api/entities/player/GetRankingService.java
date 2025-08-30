package cat.itacademy.blackjack_api.entities.player;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.util.Comparator;

@Service
public class GetRankingService {

    private final PlayerRepository PLAYER_REPOSITORY;

    public GetRankingService(PlayerRepository playerRepository) {
        this.PLAYER_REPOSITORY = playerRepository;
    }

    public Flux<GetPlayerRankingDto> execute() {
        return PLAYER_REPOSITORY.findAll()
                .sort(Comparator.comparingInt(Player::getRankingScore).reversed())
                .index()
                .map(this::mapToPlayerRankingDto);
    }

    private GetPlayerRankingDto mapToPlayerRankingDto(reactor.util.function.Tuple2<Long, Player> tuple) {
        long position = tuple.getT1() + 1;
        Player player = tuple.getT2();
        return new GetPlayerRankingDto(position, player.getName(), player.getRankingScore());
    }
}