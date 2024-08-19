package main;

import java.awt.event.KeyEvent; // Importa a classe KeyEvent para lidar com eventos de teclado
import java.awt.event.KeyListener; // Importa a interface KeyListener para capturar eventos de teclado

// Classe KeyHandler implementa a interface KeyListener para capturar e processar eventos de teclado
public class KeyHandler implements KeyListener {

    // Variáveis booleanas para indicar se as teclas direcionais foram pressionadas
    public boolean WPressionado, SPressionado, APressinado, DPressionado;

    // Método chamado quando uma tecla é digitada (pressionada e liberada rapidamente)
    @Override
    public void keyTyped(KeyEvent e) {
        // Este método está vazio, pois não precisamos dele para o controle do jogo
    }

    // Método chamado quando uma tecla é pressionada
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Obtém o código da tecla pressionada

        // Verifica qual tecla foi pressionada e ajusta a variável correspondente
        if (code == KeyEvent.VK_W) { // Se a tecla 'W' for pressionada
            WPressionado = true; // Seta WPressionado como true
        }
        if (code == KeyEvent.VK_S) { // Se a tecla 'S' for pressionada
            SPressionado = true; // Seta SPressionado como true
        }
        if (code == KeyEvent.VK_A) { // Se a tecla 'A' for pressionada
            APressinado = true; // Seta APressinado como true
        }
        if (code == KeyEvent.VK_D) { // Se a tecla 'D' for pressionada
            DPressionado = true; // Seta DPressionado como true
        }
    }

    // Método chamado quando uma tecla é liberada
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // Obtém o código da tecla liberada

        // Verifica qual tecla foi liberada e ajusta a variável correspondente
        if (code == KeyEvent.VK_W) { // Se a tecla 'W' for liberada
            WPressionado = false; // Seta WPressionado como false
        }
        if (code == KeyEvent.VK_S) { // Se a tecla 'S' for liberada
            SPressionado = false; // Seta SPressionado como false
        }
        if (code == KeyEvent.VK_A) { // Se a tecla 'A' for liberada
            APressinado = false; // Seta APressinado como false
        }
        if (code == KeyEvent.VK_D) { // Se a tecla 'D' for liberada
            DPressionado = false; // Seta DPressionado como false
        }
    }
}
