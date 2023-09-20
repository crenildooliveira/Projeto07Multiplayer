package com.smb.projeto07multiplayer;

public class PlayerMove {
    private String playerId; // ID do jogador que está se movendo
    private int newX; // Nova posição X do jogador
    private int newY; // Nova posição Y do jogador

    // Construtor
    public PlayerMove(String playerId, int newX, int newY) {
        this.playerId = playerId;
        this.newX = newX;
        this.newY = newY;
    }

    // Getters e setters
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }
}

