package cat.itacademy.blackjack_api.entities.player;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("players")
public class Player {
    @Id
    @Schema(description = "Identifier of the player linked to this game.", example = "1001")
    private Long id;
    @Schema(description = "Name of the player.", example = "Manuel")
    private String name;
    @Schema(description = "Email of the player.", example = "player@email.com")
    private String email;
    @Schema(description = "Balance of the player.", example = "100")
    private Long balance;
    @Schema(description = "Ranking Score of the player.", example = "100")
    private int rankingScore;
    @Schema(description = "Is player is active.", example = "true")
    private Boolean isActive;
    @Schema(description = "Date when the player was created.", example = "2025-07-15")
    private LocalDateTime registrationDate;
}
