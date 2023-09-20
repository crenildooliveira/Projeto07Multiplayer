package com.smb.projeto07multiplayer;

import com.smb.projeto07multiplayer.Player;
import com.smb.projeto07multiplayer.PlayerMove;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

@Controller
public class GameController {

    private final List<Player> players = new CopyOnWriteArrayList<>(); // Lista de jogadores

    @MessageMapping("/move") // Mapeia mensagens enviadas para /app/move
    @SendTo("/topic/move") // Envia mensagens para /topic/move (para todos os jogadores)
    public PlayerMove move(PlayerMove move) throws Exception {
        // Lógica para tratar o movimento do jogador
        Player player = findPlayerById(move.getPlayerId());

        if (player != null) {
            // Valide o movimento (certificando-se de que o jogador permaneça dentro dos limites)
            int newX = move.getNewX();
            int newY = move.getNewY();

            if (newX >= 0 && newX <= 780 && newY >= 0 && newY <= 780) {
                // Atualize a posição do jogador com base no movimento
                player.setX(newX);
                player.setY(newY);

                // Envie a nova posição do jogador para todos os jogadores conectados
                return new PlayerMove(player.getId(), newX, newY);
            }
        }

        // Retorna o movimento original (sem atualizações) se o jogador não for encontrado
        return move;
    }

    // Método para encontrar um jogador pelo ID
    private Player findPlayerById(String playerId) {
        return players.stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElse(null);
    }
}
