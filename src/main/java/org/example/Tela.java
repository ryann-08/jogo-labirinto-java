package org.example;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


 // Interface Tela (Contrato)
 // Define os métodos obrigatórios que qualquer tela do jogo
 // (Menu, Jogo, Pausa) deve possuir para funcionar no gerenciador.

public interface Tela {

    // Atualiza a lógica da tela (ex: posições, pontuação, etc.)
    void atualizar();

    // Desenha os elementos gráficos na tela
    void desenhar(Graphics2D g2d);

    // Captura as teclas pressionadas pelo usuário
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
}
