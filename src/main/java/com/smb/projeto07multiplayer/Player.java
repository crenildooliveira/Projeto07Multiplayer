package com.smb.projeto07multiplayer;

public class Player {
    private String id; // ID único do jogador
    private int x; // Posição X do jogador
    private int y; // Posição Y do jogador
    private String color; // Cor do jogador (para diferenciar jogadores)

    // Construtor
    public Player(String id, String color, int x, int y) {
        this.id = id;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    // Getters e setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
