package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

// Classe para gerenciar áreas de colisão
public class Colisoes {

    private List<Rectangle> areasDeColisao; // Lista para armazenar as áreas de colisão

    // Construtor da classe Colisoes
    public Colisoes() {
        areasDeColisao = new ArrayList<>();
        inicializarAreasDeColisao(); // Método para inicializar as áreas de colisão
    }

    // Método para definir as áreas de colisão
    private void inicializarAreasDeColisao() {
    
        areasDeColisao.add(new Rectangle(0, 0, 5000, 10 * 15)); // Área 1 (Prédio)
        areasDeColisao.add(new Rectangle(0, 590, 1000, 300)); // Área 2 (Lago)
        areasDeColisao.add(new Rectangle(1150, 550, 440, 300)); // Área 3 (Ônibus)
        areasDeColisao.add(new Rectangle(1000, 520, 28, 28)); // Área 4 (Placa)
        areasDeColisao.add(new Rectangle(0, 280, 80, 600)); // Área 5 (Mato Esquerda)
        areasDeColisao.add(new Rectangle(1470, 270, 80, 600)); // Área 6 (Mato Direito)
    }

    // Método para verificar se a posição do jogador colide com alguma área
    public boolean verificarColisao(int xJogador, int yJogador, int tamanhoTile) {
        Rectangle limitesJogador = new Rectangle(xJogador, yJogador, tamanhoTile, tamanhoTile);
        for (Rectangle area : areasDeColisao) {
            if (limitesJogador.intersects(area)) {
                return true; // Colisão detectada
            }
        }
        return false; // Nenhuma colisão detectada
    }
}
