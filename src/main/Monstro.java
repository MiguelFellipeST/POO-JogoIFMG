// Declaração do pacote do projeto
package main;
// Importa classes do pacote java.awt para manipulação de imagens e recursos do sistema
import java.awt.Image; // Classe para manipulação de imagens
import java.awt.Toolkit; // Classe para acesso a recursos de sistema, como imagens

// Classe que representa um monstro no jogo, com suas propriedades e animações
public class Monstro {
    private int x, y; // Coordenadas do monstro na tela
    private Image[] imagens; // Imagens padrão do monstro
    private Image[] animacaoAtaque1; // Imagens para a animação de ataque 1
    private Image[] animacaoAtaque2; // Imagens para a animação de ataque 2
    private Image[] animacaoAtaqueInimigo; // Imagens para a animação de ataque do inimigo
    private Image[] animacaoAtual; // Imagens atualmente em animação
    private int frameAtual = 0; // Índice do frame atual da animação
    private long ultimoTempoFrame = 0; // Tempo do último frame da animação
    private final long duracaoFrame = 500; // Duração de cada frame em milissegundos
    private int saude; // Pontos de saúde do monstro
    private int saudeMaxima; // Pontos de saúde máximos do monstro
    private boolean animacaoEmAndamento = false; // Indica se uma animação está em andamento
    private boolean animacaoConcluida = true; // Indica se a animação foi concluída

    // Construtor da classe Monstro
    public Monstro(int x, int y, String[] caminhosImagens, int saudeMaxima) {
        this.x = x; // Define a coordenada x do monstro
        this.y = y; // Define a coordenada y do monstro
        this.saudeMaxima = saudeMaxima; // Define a saúde máxima do monstro
        this.saude = saudeMaxima; // Inicializa a saúde atual com a saúde máxima
        this.imagens = new Image[caminhosImagens.length]; // Cria um array para armazenar as imagens do monstro
        
        Toolkit toolkit = Toolkit.getDefaultToolkit(); // Obtém o Toolkit para carregar imagens
        for (int i = 0; i < caminhosImagens.length; i++) {
            // Carrega cada imagem e armazena no array de imagens
            this.imagens[i] = toolkit.getImage(getClass().getResource(caminhosImagens[i]));
        }
    }

    // Atualiza a animação do monstro
    public void atualizarAnimacao() {
        if (animacaoEmAndamento) {
            long tempoAtual = System.currentTimeMillis(); // Obtém o tempo atual
            // Verifica se é o momento de mudar para o próximo frame
            if (tempoAtual - ultimoTempoFrame >= duracaoFrame) {
                ultimoTempoFrame = tempoAtual; // Atualiza o tempo do último frame
                frameAtual = (frameAtual + 1) % animacaoAtual.length; // Avança para o próximo frame
                // Se a animação foi concluída, marca a animação como concluída
                if (frameAtual == 0) {
                    animacaoEmAndamento = false;
                    animacaoConcluida = true;
                }
            }
        } else {
            long tempoAtual = System.currentTimeMillis(); // Obtém o tempo atual
            // Verifica se é o momento de mudar para o próximo frame das imagens padrão
            if (tempoAtual - ultimoTempoFrame >= duracaoFrame) {
                ultimoTempoFrame = tempoAtual; // Atualiza o tempo do último frame
                frameAtual = (frameAtual + 1) % imagens.length; // Avança para o próximo frame
            }
        }
    }

    // Obtém a imagem atual a ser exibida
    public Image obterImagem() {
        if (animacaoEmAndamento) {
            return animacaoAtual[frameAtual]; // Retorna a imagem da animação atual
        } else {
            return imagens[frameAtual]; // Retorna a imagem padrão
        }
    }

    // Define a animação de ataque 1
    public void setAnimacaoAtaque1(Image[] animacao) {
        this.animacaoAtaque1 = animacao;
    }

    // Define a animação de ataque 2
    public void setAnimacaoAtaque2(Image[] animacao) {
        this.animacaoAtaque2 = animacao;
    }

    // Define a animação de ataque do inimigo
    public void setAnimacaoAtaqueInimigo(Image[] animacao) {
        this.animacaoAtaqueInimigo = animacao;
    }

    // Inicia a animação de ataque 1
    public void iniciarAnimacaoAtaque1() {
        this.animacaoAtual = animacaoAtaque1; // Define a animação atual como ataque 1
        this.frameAtual = 0; // Reseta o frame atual
        this.animacaoEmAndamento = true; // Marca a animação como em andamento
        this.animacaoConcluida = false; // Marca a animação como não concluída
        this.ultimoTempoFrame = System.currentTimeMillis(); // Atualiza o tempo do último frame
    }

    // Inicia a animação de ataque 2
    public void iniciarAnimacaoAtaque2() {
        this.animacaoAtual = animacaoAtaque2; // Define a animação atual como ataque 2
        this.frameAtual = 0; // Reseta o frame atual
        this.animacaoEmAndamento = true; // Marca a animação como em andamento
        this.animacaoConcluida = false; // Marca a animação como não concluída
        this.ultimoTempoFrame = System.currentTimeMillis(); // Atualiza o tempo do último frame
    }

    // Inicia a animação de ataque do inimigo
    public void iniciarAnimacaoAtaqueInimigo() {
        this.animacaoAtual = animacaoAtaqueInimigo; // Define a animação atual como ataque do inimigo
        this.frameAtual = 0; // Reseta o frame atual
        this.animacaoEmAndamento = true; // Marca a animação como em andamento
        this.animacaoConcluida = false; // Marca a animação como não concluída
        this.ultimoTempoFrame = System.currentTimeMillis(); // Atualiza o tempo do último frame
    }

    // Define a imagem padrão do monstro (quando não está em animação)
    public void setImagemPadrao() {
        this.animacaoEmAndamento = false; // Marca a animação como não em andamento
        this.animacaoConcluida = true; // Marca a animação como concluída
        this.frameAtual = 0; // Reseta o frame atual
        this.ultimoTempoFrame = System.currentTimeMillis(); // Atualiza o tempo do último frame
    }

    // Verifica se a animação foi concluída
    public boolean isAnimacaoConcluida() {
        return animacaoConcluida;
    }

    // Métodos getters e setters para coordenadas e saúde do monstro
    public int getX() { return x; } // Obtém a coordenada x
    public int getY() { return y; } // Obtém a coordenada y
    public void setX(int x) { this.x = x; } // Define a coordenada x
    public void setY(int y) { this.y = y; } // Define a coordenada y
    public int getSaude() { return saude; } // Obtém a saúde atual
    public int getSaudeMaxima() { return saudeMaxima; } // Obtém a saúde máxima
    public void receberDano(int dano) { // Aplica dano ao monstro
        saude -= dano; // Subtrai o dano da saúde
        if (saude < 0) {
            saude = 0; // Garante que a saúde não fique negativa
        }
    }
    public boolean estaDerrotado() { // Verifica se o monstro está derrotado
        return saude <= 0; // Retorna true se a saúde for menor ou igual a 0
    }
}
