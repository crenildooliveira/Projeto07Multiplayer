package com.smb.projeto07multiplayer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GameHandler extends TextWebSocketHandler {
    private static final List<WebSocketSession> sessions = new ArrayList<>();
    private static final List<Player> players = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Quando um jogador se conecta, adicione a sessão à lista
        sessions.add(session);

        // Envie a cor do jogador para identificação
        String playerColor = getRandomColor();
        session.getAttributes().put("playerColor", playerColor);
        session.sendMessage(new TextMessage("You are connected with color: " + playerColor));

        // Crie um novo jogador e adicione à lista de jogadores
        Player player = new Player(session.getId(), playerColor, 0, 0);
        players.add(player);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Verifique se a mensagem é válida e representa um movimento do jogador
        String payload = message.getPayload();
        if (payload != null && payload.startsWith("move:")) {
            // Extrai os detalhes do movimento da mensagem
            String[] parts = payload.split(":");
            if (parts.length == 4) {
                String playerId = parts[1];
                int newX = Integer.parseInt(parts[2]);
                int newY = Integer.parseInt(parts[3]);

                // Atualize a posição do jogador
                updatePlayerPosition(playerId, newX, newY);

                // Em seguida, você pode transmitir a nova posição para todos os jogadores
                for (WebSocketSession playerSession : sessions) {
                    String playerColor = (String) playerSession.getAttributes().get("playerColor");
                    String newPositionMessage = "update:" + playerId + ":" + newX + ":" + newY + ":" + playerColor;
                    playerSession.sendMessage(new TextMessage(newPositionMessage));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Quando um jogador se desconecta, remova a sessão da lista
        sessions.remove(session);

        // Remova o jogador correspondente da lista de jogadores
        String playerId = session.getId();
        Player playerToRemove = findPlayerById(playerId);
        if (playerToRemove != null) {
            players.remove(playerToRemove);
        }
    }

    private void updatePlayerPosition(String playerId, int newX, int newY) {
        // Encontre o jogador pelo ID e atualize a posição
        Player playerToUpdate = findPlayerById(playerId);
        if (playerToUpdate != null) {
            playerToUpdate.setX(newX);
            playerToUpdate.setY(newY);
        }
    }

    private String getRandomColor() {
        // Gere cores aleatórias para os jogadores
        String[] colors = {"red", "blue", "green", "yellow", "orange", "purple"};
        Random random = new Random();
        int randomIndex = random.nextInt(colors.length);
        return colors[randomIndex];
    }

    // Método para encontrar um jogador pelo ID
    private Player findPlayerById(String playerId) {
        return players.stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .orElse(null);
    }
}
