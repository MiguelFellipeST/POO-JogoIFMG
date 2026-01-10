# I Fight Monsters Game (IFMG)

## Descrição do Projeto
Este documento apresenta o Trabalho Final da disciplina de Programação Orientada a Objetos, feito por Miguel Teixeira e Lucas Abjaud, desenvolvido como parte do curso de Engenharia de Computação do Instituto Federal de Minas Gerais de Bambuí (IFMG-Bambuí).

O projeto resultante é o **I Fight Monsters Game**, um jogo feito em Java de batalha por turnos inspirado na franquia Pokémon, que incorpora elementos do ambiente do IFMG-Bambuí. O software foi projetado para aplicar conceitos fundamentais de Programação Orientada a Objetos, como classes, herança, polimorfismo e encapsulamento.

## Demonstração
<div align="center">
  <p>Clique na imagem abaixo para assistir ao vídeo de demonstração:</p>

  <a href="https://www.youtube.com/watch?v=Sf3CotEy-7k">
    <img src="https://img.youtube.com/vi/Sf3CotEy-7k/0.jpg" alt="Demonstração do Jogo IFMG">
  </a>
</div>

## Funcionalidades Principais
O jogo inclui as seguintes mecânicas e sistemas implementados:

* **Sistema de Batalha:** Combate por turnos onde o jogador pode escolher ataques que executam animações e alteram os pontos de vida do monstro adversário.
* **Movimentação:** Controle do personagem principal pelo mapa em quatro direções.
* **Detecção de Colisão:** Sistema que impede o personagem de atravessar estruturas do cenário (prédios, lago, ônibus) e detecta interações com NPCs.
* **Animações:** Sprites animados para movimentação do personagem e execução de habilidades durante a batalha.

## Arquitetura do Sistema
O código está estruturado em classes que gerenciam aspectos específicos do jogo, refletindo o uso de encapsulamento e modularização:

### Classes Principais
* **Main:** Ponto de entrada do programa. Inicializa a interface gráfica, cria a janela principal e instância o PainelJogo.
* **Personagem:** Modela o protagonista, gerenciando animações de movimento, coordenadas e interação com o ambiente.
* **Monstro:** Entidade utilizada nas batalhas. Contém atributos de estatísticas (vida, ataque) e métodos para gerenciar animações de habilidades.
* **NPC:** Define o personagem rival, herdando características da classe Personagem, incluindo sprite próprio e lógica de colisão.
* **PainelJogo:** Gerencia o ambiente de exploração (fora de batalha), o mapa e as transições de tela.
* **PainelBatalha:** Implementa a lógica de combate, turnos, condições de vitória/derrota e renderização da interface de batalha.
* **Colisoes:** Gerencia a lógica de limites do mapa utilizando "retângulos invisíveis" para definir áreas inacessíveis e gatilhos de eventos.
* **KeyHandler:** Captura e processa eventos de teclado para controle de movimentação e interação.

## Design e Inspiração
**Toda a arte visual e ilustrações do projeto foram desenvolvidas pelo aluno Miguel Teixeira.**

O design visual e conceitual do jogo foi desenvolvido para contextualizar o ambiente do IFMG-Bambuí:

* **Cenário:** Representação resumida do campus, contendo o ônibus, o novo prédio do instituto e o lago.
* **Personagens:** O protagonista é baseado no Prof. Felipe Faria, e o rival em outro docente da instituição.
* **Monstros:**
    * **Capivara:** Monstro do jogador, referenciando a fauna local do campus.
    * **Pinguim:** Monstro do rival, baseado no mascote do Linux (Tux), alinhado às preferências do professor homenageado.

## Tecnologias Utilizadas
* **Linguagem de Programação:** Java
* **Bibliotecas Gráficas:** Swing / AWT (Graphics 2D) para renderização e gerenciamento de janelas.

## Autores
* Miguel Fellipe de Souza Teixeira
* Lucas Martins Abjaud

---
*Trabalho desenvolvido para a disciplina de Programação Orientada a Objetos - IFMG Campus Bambuí, 2024.*
