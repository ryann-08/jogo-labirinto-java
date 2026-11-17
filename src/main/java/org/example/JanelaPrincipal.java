package org.example;

import javax.swing.JFrame;

public class JanelaPrincipal {
    public static void main(String[] args) {
        // Criando a nossa "TV" (a janela principal do jogo)
        JFrame janela = new JFrame("Jogo de Labirinto");

        // Definindo o tamanho padrão da tela em pixels (largura x altura)
        janela.setSize(640, 480);

        // Garante que o programa fecha de verdade quando o usuário clica no "X"
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Impede que o jogador fique redimensionando a janela e quebrando o visual
        janela.setResizable(false);

        // Faz a janela abrir bem no centro do monitor
        janela.setLocationRelativeTo(null);

        // Manda a janela aparecer de fato na tela do usuário
        janela.setVisible(true);
    }
}