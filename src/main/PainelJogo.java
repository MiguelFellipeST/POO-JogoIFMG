package main;

import javax.swing.JFrame;  // Importa a classe JFrame, que representa uma janela de aplicativo em uma interface gráfica Swing.
import javax.swing.JPanel;  // Importa a classe JPanel, que representa um painel que pode ser usado para desenhar ou organizar componentes na interface gráfica Swing.
import java.awt.Color;  // Importa a classe Color, que fornece cores usadas para desenhar e preencher elementos gráficos.
import java.awt.Dimension;  // Importa a classe Dimension, que define a largura e a altura de um componente ou tela.
import java.awt.Graphics;  // Importa a classe Graphics, que fornece métodos para desenhar em um componente.
import java.awt.Graphics2D;  // Importa a classe Graphics2D, que é uma subclasse de Graphics e oferece mais opções de desenho avançado.
import java.awt.Image;  // Importa a classe Image, que representa uma imagem que pode ser desenhada na tela.
import java.awt.Rectangle;  // Importa a classe Rectangle, que representa um retângulo e é útil para definir áreas de colisão e limites de desenho.
import java.io.IOException;  // Importa a classe IOException, que é usada para lidar com erros de entrada/saída, como falhas ao ler arquivos.
import java.util.ArrayList;  // Importa a classe ArrayList, que é uma implementação da interface List e fornece uma lista dinâmica que pode crescer e encolher conforme necessário.
import java.util.List;  // Importa a interface List, que define um contrato para listas ordenadas que permitem elementos duplicados e acesso por índice.
import javax.imageio.ImageIO;  // Importa a classe ImageIO, que fornece métodos para ler e escrever imagens em diferentes formatos (como PNG, JPEG) a partir de arquivos ou streams.


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

// Classe GamePanel estende JPanel e implementa Runnable para atualizar e desenhar o jogo
public class PainelJogo extends JPanel implements Runnable {

    /* CONFIGURAÇÕES DA TELA */
    final int tamanhoOriginalTile = 16;  // Tamanho original do tile em pixels
    final int escala = 3;  // Fator de escala para ampliar os gráficos
    final int tamanhoDoTile = tamanhoOriginalTile * escala;  // Tamanho final do tile
    final int vertical = 32;  // Número de tiles na vertical
    final int horizontal = 18;  // Número de tiles na horizontal
    final int tamanhoVertical = tamanhoDoTile * vertical;  // Altura total da tela
    final int tamanhoHorizontal = tamanhoDoTile * horizontal;  // Largura total da tela

    int FPS = 60;  // Frames por segundo
    KeyHandler keyH = new KeyHandler();  // Gerenciador de entradas de teclado
    Thread gameThread;  // Thread do jogo

    // Posição inicial do jogador
    int jogadorX = 50;
    int jogadorY = 200;
    int velocidadeDoJogador = 4;  // Velocidade do jogador

    // Imagens para o fundo e animação do jogador
    private Image imagemFundo;
    private Image[] imagemJogadorFrente = new Image[2];
    private Image[] imagemJogadorEsquerda = new Image[2];
    private Image[] imagemJogadorDireita = new Image[2];
    private Image[] imagemJogadorCima = new Image[2];
    private int frameAnimacao = 0;  // Frame atual da animação
    private long ultimoTempoMudaFrame = 0;  // Último tempo de mudança de frame
    private final long duracaoFrame = 200;  // Duração de cada frame da animação em milissegundos

    // Flags para controlar o movimento do jogador
    private boolean movendoEsquerda = false;
    private boolean movendoDireita = false;
    private boolean movendoCima = false;
    private boolean movendoBaixo = false;

    // NPC no jogo
    private NPC npc;
    private boolean modoBatalha = false;  // Indica se o jogo está no modo batalha

    // Instância da classe Colisoes para gerenciar colisões
    private Colisoes colisoes;

    // Construtor da classe GamePanel
    public PainelJogo() {
        // Define o tamanho da tela e outras configurações visuais
        this.setPreferredSize(new Dimension(tamanhoVertical, tamanhoHorizontal));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        try {
            // Carrega as imagens de fundo e as animações do jogador
            imagemFundo = ImageIO.read(getClass().getResourceAsStream("/imagens/background.png"));
            imagemJogadorFrente[0] = ImageIO.read(getClass().getResourceAsStream("/imagens/MCFrente.png"));
            imagemJogadorFrente[1] = ImageIO.read(getClass().getResourceAsStream("/imagens/MCFrente2.png"));
            imagemJogadorEsquerda[0] = ImageIO.read(getClass().getResourceAsStream("/imagens/MCEsquerda.png"));
            imagemJogadorEsquerda[1] = ImageIO.read(getClass().getResourceAsStream("/imagens/MCParadoEsquerda.png"));
            imagemJogadorDireita[0] = ImageIO.read(getClass().getResourceAsStream("/imagens/MCDireito.png"));
            imagemJogadorDireita[1] = ImageIO.read(getClass().getResourceAsStream("/imagens/MCParadoDireito.png"));
            imagemJogadorCima[0] = ImageIO.read(getClass().getResourceAsStream("/imagens/MCostas.png"));
            imagemJogadorCima[1] = ImageIO.read(getClass().getResourceAsStream("/imagens/MCostas2.png"));

            // Carrega a imagem do NPC
            npc = new NPC(870, 330, "/imagens/NPC.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inicializa a classe de colisões
        colisoes = new Colisoes();
    }

    // Inicia a thread do jogo
    public void iniciarThreadDoJogo() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double intervaloDesenho = 1000000000 / FPS;  // Intervalo entre cada frame em nanosegundos
        double proximoTempoDesenho = System.nanoTime() + intervaloDesenho;

        while (gameThread != null) {
            atualizar();  // Atualiza a lógica do jogo
            repaint();  // Redesenha a tela

            try {
                double tempoRestante = proximoTempoDesenho - System.nanoTime();
                tempoRestante = tempoRestante / 1000000;

                if (tempoRestante < 0) {
                    tempoRestante = 0;
                }

                Thread.sleep((long) tempoRestante);  // Faz a thread dormir até o próximo frame

                proximoTempoDesenho += intervaloDesenho;  // Atualiza o tempo para o próximo frame
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Atualiza a lógica do jogo a cada frame
    public void atualizar() {
        if (modoBatalha) {
            return;  // Não atualiza o jogo se estiver no modo batalha
        }

        // Atualiza as flags de movimento com base nas teclas pressionadas
        movendoEsquerda = keyH.APressinado;
        movendoDireita = keyH.DPressionado;
        movendoCima = keyH.WPressionado;
        movendoBaixo = keyH.SPressionado;

        int novoJogadorX = jogadorX;
        int novoJogadorY = jogadorY;

        // Calcula a nova posição do jogador com base nas teclas pressionadas
        if (movendoEsquerda) {
            novoJogadorX -= velocidadeDoJogador;
        }
        if (movendoDireita) {
            novoJogadorX += velocidadeDoJogador;
        }
        if (movendoCima) {
            novoJogadorY -= velocidadeDoJogador;
        }
        if (movendoBaixo) {
            novoJogadorY += velocidadeDoJogador;
        }

        // Verifica colisão antes de atualizar a posição do jogador
        if (!colisoes.verificarColisao(novoJogadorX, novoJogadorY, tamanhoDoTile)) {
            jogadorX = novoJogadorX;
            jogadorY = novoJogadorY;
        }

        // Atualiza a animação do jogador se ele estiver se movendo
        if (movendoEsquerda || movendoDireita || movendoCima || movendoBaixo) {
            long tempoAtual = System.currentTimeMillis();
            if (tempoAtual - ultimoTempoMudaFrame >= duracaoFrame) {
                ultimoTempoMudaFrame = tempoAtual;
                frameAnimacao = (frameAnimacao + 1) % 2;  // Alterna entre os frames da animação
            }
        } else {
            frameAnimacao = 0;  // Reseta a animação se o jogador parar de se mover
        }

        // Inicia a batalha se o jogador colidir com o NPC
        if (jogadorX < npc.getX() + tamanhoDoTile &&
            jogadorX + tamanhoDoTile > npc.getX() &&
            jogadorY < npc.getY() + tamanhoDoTile &&
            jogadorY + tamanhoDoTile > npc.getY()) {
            iniciarBatalha();
        }
    }

    // Inicia o modo batalha
    private void iniciarBatalha() {
        modoBatalha = true;
        JFrame janelaBatalha = new JFrame();  // Cria uma nova janela para a batalha
        janelaBatalha.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaBatalha.setTitle("Batalha");
        janelaBatalha.setSize(800, 600);  // Define o tamanho da janela de batalha
        janelaBatalha.setLocationRelativeTo(null);  // Centraliza a janela na tela
        janelaBatalha.add(new PainelBatalha());  // Adiciona o painel de batalha à janela
        janelaBatalha.setVisible(true);  // Torna a janela visível
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (!modoBatalha) {
            // Desenha o fundo
            g2.drawImage(imagemFundo, 0, 0, tamanhoVertical, tamanhoHorizontal, this);

            // Define a imagem atual do jogador com base na direção do movimento
            Image imagemAtual = imagemJogadorFrente[frameAnimacao];
            if (movendoCima) {
                imagemAtual = imagemJogadorCima[frameAnimacao];
            } else if (movendoEsquerda) {
                imagemAtual = imagemJogadorEsquerda[frameAnimacao];
            } else if (movendoDireita) {
                imagemAtual = imagemJogadorDireita[frameAnimacao];
            }

            // Desenha o jogador e o NPC na tela
            g2.drawImage(imagemAtual, jogadorX, jogadorY, tamanhoDoTile, tamanhoDoTile, this);
            g2.drawImage(npc.getImage(), npc.getX(), npc.getY(), tamanhoDoTile, tamanhoDoTile, this);
        }

        g2.dispose();  // Libera os recursos gráficos
    }
}
