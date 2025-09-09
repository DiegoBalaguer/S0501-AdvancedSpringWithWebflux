package cat.itacademy.blackjack_api.entities.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class GetRankingService {

    private final PlayerRepository playerRepository;

    public Flux<GetPlayerRankingDto> execute() {
        return playerRepository.findAll()
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