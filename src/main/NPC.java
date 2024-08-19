package main;

import java.awt.Image; // Importa a classe Image para manipulação de imagens
import java.awt.Rectangle; // Importa a classe Rectangle para definir áreas retangulares (como colisões)
import java.io.IOException; // Importa a classe IOException para tratamento de erros ao carregar a imagem
import javax.imageio.ImageIO; // Importa a classe ImageIO para ler a imagem a partir de um arquivo

// A classe NPC herda da classe Personagem, representando um NPC (Non-Player Character) no jogo
public class NPC extends Personagem {
    private Image image; // Variável para armazenar a imagem do NPC

    // Construtor da classe NPC que recebe a posição inicial (x, y) e o caminho da imagem
    public NPC(int x, int y, String imagePath) {
        super(x, y); // Chama o construtor da classe pai (Personagem) para definir as coordenadas do NPC
        try {
            // Carrega a imagem do NPC a partir do caminho fornecido
            this.image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace(); // Imprime o erro no console se ocorrer um problema ao carregar a imagem
        }
    }

    // Método para obter a imagem do NPC
    public Image getImage() {
        return image; // Retorna a imagem do NPC
    }

    // Método para obter as coordenadas e o tamanho do NPC como um retângulo
    // Isso é útil para verificar colisões ou determinar a área ocupada pelo NPC
    public Rectangle getBounds(int tileSize) {
        // Cria e retorna um retângulo com a posição (x, y) e o tamanho especificado (tileSize)
        return new Rectangle(getX(), getY(), tileSize, tileSize);
    }
}
