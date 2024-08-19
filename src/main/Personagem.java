package main;

import java.awt.Image; // Importa a superclasse Image, usada para armazenar a imagem do personagem

// Classe base para representar um personagem no jogo
public class Personagem {
    // Coordenadas (x, y) que determinam a posição do personagem no jogo
    protected int x, y;
    // Variável para armazenar a imagem do personagem
    protected Image image;
    // Construtor da classe Personagem que define a posição inicial do personagem
    public Personagem(int x, int y) {
        this.x = x; // Define a coordenada x
        this.y = y; // Define a coordenada y
    }
    // Método para alterar a posição do personagem
    public void setPosition(int x, int y) {
        this.x = x; // Atualiza a coordenada x
        this.y = y; // Atualiza a coordenada y
    }
    // Método para obter a coordenada x do personagem
    public int getX() {
        return x; // Retorna a coordenada x
    }
    // Método para obter a coordenada y do personagem
    public int getY() {
        return y; // Retorna a coordenada y
    }
    // Método para obter a imagem do personagem
    public Image getImage() {
        return image; // Retorna a imagem do personagem
    }
}
