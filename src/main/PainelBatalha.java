package main;
import javax.swing.JPanel; // Classe base para criar um painel (área) em uma interface gráfica
import javax.swing.JFrame; // Classe que representa a janela principal da aplicação
import java.awt.Graphics; // Classe básica para desenho gráfico
import java.awt.Graphics2D; // Classe derivada de Graphics que fornece melhor controle sobre o desenho
import java.awt.Color; // Classe para representar cores
import java.awt.Image; // Classe para manipulação de imagens
import java.awt.Toolkit; // Classe para acesso a recursos de sistema como imagens
import java.awt.event.MouseAdapter; // Classe que facilita o tratamento de eventos de mouse
import java.awt.event.MouseEvent; // Classe que representa um evento de mouse
import java.awt.event.MouseMotionAdapter; // Classe que facilita o tratamento de eventos de movimentação do mouse

// Importa classes do pacote javax.swing para criar e manipular componentes gráficos
import javax.swing.Timer; // Classe que fornece uma forma de executar ações após um intervalo de tempo
import javax.swing.SwingUtilities; // Classe com utilitários para manipulação de eventos e componentes Swing

/**
 * Classe que representa o painel de batalha do jogo.
 * Extende JPanel para criar uma área onde a batalha entre monstros é desenhada.
 */
public class PainelBatalha extends JPanel {
    private Monstro monstroJogador; // O monstro controlado pelo jogador
    private Monstro monstroInimigo; // O monstro inimigo
    private boolean turnoJogador = true; // Indica se é o turno do jogador
    private int dano1 = 10; // Dano causado pelo primeiro ataque
    private int dano2 = 30; // Dano causado pelo segundo ataque
    private Image imagemFundo; // Imagem do fundo do campo de batalha
    private Image imagemVitoria; // Imagem exibida ao vencer a batalha
    private Image imagemHUD; // Imagem da interface do usuário (HUD)
    private Image imagemAtaque1Normal; // Imagem do botão de ataque 1 em estado normal
    private Image imagemAtaque1Hover; // Imagem do botão de ataque 1 quando o cursor está sobre ele
    private Image imagemAtaque2Normal; // Imagem do botão de ataque 2 em estado normal
    private Image imagemAtaque2Hover; // Imagem do botão de ataque 2 quando o cursor está sobre ele
    private boolean jogoVencido = false; // Indica se o jogo foi vencido
    private boolean ataque1Hover = false; // Indica se o cursor está sobre o botão de ataque 1
    private boolean ataque2Hover = false; // Indica se o cursor está sobre o botão de ataque 2
    private Timer temporizadorInimigo; // Timer para controlar o ataque do inimigo
    private Timer temporizadorAnimacao; // Timer para animações dos ataques

    // Imagens para animações de ataque dos monstros
    private Image[] animacaoAtaque1Capivara;
    private Image[] animacaoAtaque2Capivara;
    private Image[] animacaoAtaqueMonstroInimigo;

    /**
     * Construtor da classe PainelBatalha.
     * Inicializa os elementos do painel e configura os timers e listeners.
     */
    public PainelBatalha() {
        // Configura o tamanho e a cor de fundo do painel
        this.setPreferredSize(new java.awt.Dimension(800, 600));
        this.setBackground(Color.white);

        Toolkit toolkit = Toolkit.getDefaultToolkit(); // Obtém o Toolkit padrão para carregar imagens
        imagemFundo = toolkit.getImage(getClass().getResource("/imagens/campoDeBatalha.png"));
        imagemVitoria = toolkit.getImage(getClass().getResource("/imagens/vitoria1.png"));
        imagemHUD = toolkit.getImage(getClass().getResource("/imagens/hud.png"));
        imagemAtaque1Normal = toolkit.getImage(getClass().getResource("/imagens/ataque1.png"));
        imagemAtaque1Hover = toolkit.getImage(getClass().getResource("/imagens/ataque1Pressionado.png"));
        imagemAtaque2Normal = toolkit.getImage(getClass().getResource("/imagens/ataque2.png"));
        imagemAtaque2Hover = toolkit.getImage(getClass().getResource("/imagens/ataque2Pressionado.png"));

        // Carrega as imagens para as animações de ataque
        animacaoAtaque1Capivara = new Image[] {
            toolkit.getImage(getClass().getResource("/imagens/capivara1Ataque1.png")),
            toolkit.getImage(getClass().getResource("/imagens/capivara2Ataque1.png")),
            toolkit.getImage(getClass().getResource("/imagens/capivara3Ataque1.png"))
        };
        animacaoAtaque2Capivara = new Image[] {
            toolkit.getImage(getClass().getResource("/imagens/capivaraAtaque1.png")),
            toolkit.getImage(getClass().getResource("/imagens/capivaraAtaque2.png")),
            toolkit.getImage(getClass().getResource("/imagens/capivaraAtaque3.png"))
        };
        animacaoAtaqueMonstroInimigo = new Image[] {
            toolkit.getImage(getClass().getResource("/imagens/pinguimAtaque1.png")),
            toolkit.getImage(getClass().getResource("/imagens/pinguimAtaque2.png")),
            toolkit.getImage(getClass().getResource("/imagens/pinguimAtaque3.png"))
        };

        // Inicializa os monstros
        monstroJogador = new Monstro(170, 330, new String[]{"/imagens/capivara1.png", "/imagens/capivara2.png"}, 100);
        monstroInimigo = new Monstro(540, 150, new String[]{"/imagens/pinguim1.png", "/imagens/pinguim2.png"}, 100);

        // Configura as animações para o monstro jogador
        monstroJogador.setAnimacaoAtaque1(animacaoAtaque1Capivara);
        monstroJogador.setAnimacaoAtaque2(animacaoAtaque2Capivara);

        // Timer para atualizar as animações dos monstros
        Timer temporizador = new Timer(100, e -> {
            monstroJogador.atualizarAnimacao();
            monstroInimigo.atualizarAnimacao();
            repaint();
        });
        temporizador.start();

        // Timer para o turno do inimigo
        temporizadorInimigo = new Timer(2000, e -> {
            if (!turnoJogador && !jogoVencido) {
                monstroInimigo.setAnimacaoAtaque1(animacaoAtaqueMonstroInimigo);
                temporizadorAnimacao.start();
                ataqueInimigo();
                verificarVitoria();
                if (!jogoVencido && !monstroJogador.estaDerrotado()) {
                    turnoJogador = true;
                }
                repaint();
            }
        });

        // Timer para controlar a animação dos ataques
        temporizadorAnimacao = new Timer(100, e -> {
            monstroJogador.atualizarAnimacao();
            monstroInimigo.atualizarAnimacao();
            if (monstroJogador.isAnimacaoConcluida() && monstroInimigo.isAnimacaoConcluida()) {
                temporizadorAnimacao.stop();
                monstroJogador.setImagemPadrao();
                monstroInimigo.setImagemPadrao();
                if (!jogoVencido && !monstroInimigo.estaDerrotado()) {
                    turnoJogador = true;
                    temporizadorInimigo.start();
                }
            }
            repaint();
        });

        // Listener para detectar cliques do mouse
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (turnoJogador) {
                    // Verifica se o clique foi na área do botão de ataque 1
                    if (isClickInArea(e.getX(), e.getY(), 250, 500, 100, 30)) {
                        monstroJogador.iniciarAnimacaoAtaque1();
                        realizarAtaque(dano1);
                        temporizadorAnimacao.start();
                        verificarVitoria();
                    }
                    // Verifica se o clique foi na área do botão de ataque 2
                    else if (isClickInArea(e.getX(), e.getY(), 450, 500, 100, 30)) {
                        monstroJogador.iniciarAnimacaoAtaque2();
                        realizarAtaque(dano2);
                        temporizadorAnimacao.start();
                        verificarVitoria();
                    }
                    turnoJogador = false; // Troca para o turno do inimigo
                    temporizadorInimigo.start();
                }
            }
        });

        // Listener para detectar a movimentação do mouse
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean novoAtaque1Hover = isClickInArea(e.getX(), e.getY(), 250, 500, 100, 30);
                boolean novoAtaque2Hover = isClickInArea(e.getX(), e.getY(), 450, 500, 100, 30);
                
                // Atualiza o estado do botão de ataque 1 se necessário
                if (novoAtaque1Hover != ataque1Hover) {
                    ataque1Hover = novoAtaque1Hover;
                    repaint();
                }
                // Atualiza o estado do botão de ataque 2 se necessário
                if (novoAtaque2Hover != ataque2Hover) {
                    ataque2Hover = novoAtaque2Hover;
                    repaint();
                }
            }
        });
    }

    /**
     * Desenha o conteúdo do painel.
     * @param g O objeto Graphics usado para desenhar
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Desenha o fundo do campo de batalha
        g2.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);

        // Desenha a área inferior do HUD
        g2.setColor(Color.gray);
        g2.fillRect(0, 460, 800, 200);
        g2.drawImage(imagemHUD, 0, 460, 800, 100, this);

        // Desenha os monstros
        g2.drawImage(monstroJogador.obterImagem(), monstroJogador.getX(), monstroJogador.getY(), 100, 100, this);
        g2.drawImage(monstroInimigo.obterImagem(), monstroInimigo.getX(), monstroInimigo.getY(), 100, 100, this);

        // Desenha as barras de saúde dos monstros
        desenharBarraSaude(g2, monstroJogador, 170, 270);
        desenharBarraSaude(g2, monstroInimigo, 540, 100);

        // Desenha os botões de ataque com base no estado do hover
        g2.drawImage(ataque1Hover ? imagemAtaque1Hover : imagemAtaque1Normal, 250, 500, 100, 30, this);
        g2.drawImage(ataque2Hover ? imagemAtaque2Hover : imagemAtaque2Normal, 450, 500, 100, 30, this);

        // Desenha a imagem de vitória se o jogo for vencido
        if (jogoVencido) {
            g2.drawImage(imagemVitoria, 0, 0, getWidth(), getHeight(), this);
        }

        g2.dispose();
    }

    /**
     * Verifica se um clique está dentro de uma área retangular específica.
     * @param x Coordenada X do clique
     * @param y Coordenada Y do clique
     * @param startX Coordenada X do início da área
     * @param startY Coordenada Y do início da área
     * @param width Largura da área
     * @param height Altura da área
     * @return Verdadeiro se o clique estiver dentro da área, falso caso contrário
     */
    private boolean isClickInArea(int x, int y, int startX, int startY, int width, int height) {
        return x >= startX && x <= startX + width && y >= startY && y <= startY + height;
    }

    /**
     * Desenha a barra de saúde de um monstro.
     * @param g2 O objeto Graphics2D usado para desenhar
     * @param monstro O monstro cuja saúde será desenhada
     * @param x Coordenada X da barra de saúde
     * @param y Coordenada Y da barra de saúde
     */
    private void desenharBarraSaude(Graphics2D g2, Monstro monstro, int x, int y) {
        g2.setColor(Color.black);
        g2.drawRect(x, y, 100, 20);

        int larguraSaude = (int) ((monstro.getSaude() / (double) monstro.getSaudeMaxima()) * 100);
        g2.setColor(Color.green);
        g2.fillRect(x, y, larguraSaude, 20);

        g2.setColor(Color.black);
        g2.drawString("VIDA: " + monstro.getSaude(), x + 5, y + 15);
    }

    /**
     * Realiza um ataque ao inimigo.
     * @param dano O dano a ser causado
     */
    private void realizarAtaque(int dano) {
        if (turnoJogador) {
            monstroInimigo.receberDano(dano);
            turnoJogador = false; // Troca para o turno do inimigo após o ataque
        }
    }

    /**
     * Executa o ataque do inimigo ao jogador.
     */
    private void ataqueInimigo() {
        monstroJogador.receberDano(dano1);
        monstroInimigo.iniciarAnimacaoAtaque1();
    }

    /**
     * Verifica se o inimigo foi derrotado e encerra o jogo se necessário.
     */
    private void verificarVitoria() {
        if (monstroInimigo.estaDerrotado()) {
            jogoVencido = true;
            // Timer para fechar a janela após 3 segundos
            Timer temporizadorFechar = new Timer(3000, e -> {
                JFrame janelaTopo = (JFrame) SwingUtilities.getWindowAncestor(PainelBatalha.this);
                if (janelaTopo != null) {
                    janelaTopo.dispose();
                }
            });
            temporizadorFechar.setRepeats(false);
            temporizadorFechar.start();
        }
    }
}
