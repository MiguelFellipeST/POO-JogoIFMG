package main; // define o pacote 
import javax.swing.JFrame; // Importa a classe JFrame para criar a janela do jogo

public class Main {
        public static void main(String[] args) {

            JFrame janela = new JFrame();  // Cria uma nova janela para o jogo
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Fecha quando clica o X na tela 
            janela.setResizable(false); // Impede o redimensionamento da janela, deixando um tamanho fixo
            janela.setTitle("I Fight Monsters Game(IFMG)"); // Define o titulo do jogo

            PainelJogo jogoPainel = new PainelJogo(); // Cria uma instância do Painel do Jogo (onde o jogo será desenhado)
            janela.add(jogoPainel); // Adiciona a Painel do Jogo à janela do jogo

            janela.pack(); // Ajusta o tamanho da janela com base no tamanho do Painel do Jogo
            janela.setLocationRelativeTo(null); // Centraliza a janela na tela
            janela.setVisible(true);   // Torna a janela visível
            jogoPainel.iniciarThreadDoJogo();  // Inicia o loop do jogo
        }
}
