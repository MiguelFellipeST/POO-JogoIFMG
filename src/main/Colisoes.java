package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

// Classe para gerenciar áreas de colisão
class Colisoes {

    // Lista para armazenar as áreas de colisão
    private List<Rectangle> areasDeColisao;

    // Construtor que inicializa as áreas de colisão
    public Colisoes() {
        areasDeColisao = new ArrayList<>();
        inicializarAreasDeColisao();
    }

    // Método para definir as áreas de colisão
    private void inicializarAreasDeColisao() {
        // Adiciona várias áreas de colisão como retângulos
        areasDeColisao.add(new Rectangle(0, 0, 200, 40 * 16)); // Área 1
        areasDeColisao.add(new Rectangle(300, 100, 150, 100)); // Área 2
        areasDeColisao.add(new Rectangle(600, 400, 100, 100)); // Área 3
        areasDeColisao.add(new Rectangle(1000, 520, 28, 28)); // Área 4 (Placa)
        areasDeColisao.add(new Rectangle(0, 400, 80, 600)); // Área 5 (Mato Esquerda)
        areasDeColisao.add(new Rectangle(1470, 270, 80, 600)); // Área 6 (Mato Direito)
    }

    // Verifica se houve colisão com alguma área de colisão
    public boolean verificarColisao(int jogadorX, int jogadorY, int tamanhoDoTile) {
        // Cria um retângulo representando a posição atual do jogador
        Rectangle jogadorBounds = new Rectangle(jogadorX, jogadorY, tamanhoDoTile, tamanhoDoTile);
        // Verifica se o retângulo do jogador colide com alguma das áreas de colisão
        for (Rectangle area : areasDeColisao) {
            if (jogadorBounds.intersects(area)) {
                return true; // Colisão detectada
            }
        }
        return false; // Nenhuma colisão detectada
    }
}

