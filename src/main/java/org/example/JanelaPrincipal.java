package org.example;

import javax.swing.JFrame;

public class JanelaPrincipal extends JFrame { // Herda de JFrame para se tornar a nossa janela principal

    public JanelaPrincipal() {
        // Criando a nossa "TV" (a janela principal do jogo)
        // (Como estamos usando herança, configuramos o título direto na classe)
        setTitle("Jogo de Labirinto");

        // Garante que o programa fecha de verdade quando o usuário clica no "X"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Impede que o jogador fique redimensionando a janela e quebrando o visual
        setResizable(false);

        //  Instancia o motor do jogo (GamePanel) que gerencia os 60 FPS e as telas
        GamePanel painelDoJogo = new GamePanel();

        // Encaixa o GamePanel dentro da JanelaPrincipal (a tela de vidro na carcaça)
        add(painelDoJogo);

        //  Ajusta o tamanho da janela automaticamente com base nas dimensões do GamePanel (760x560)
        pack();

        // Faz a janela abrir bem no centro do monitor
        setLocationRelativeTo(null);

        // Manda a janela aparecer de fato na tela do usuário
        setVisible(true);

        //  Dá a partida no Game Loop (inicia a Thread rodando a 60 FPS)
        painelDoJogo.iniciarGameLoop();
    }

    public static void main(String[] args) {
        // Bloco de segurança do Java Swing para iniciar a interface gráfica
        //esse comando SwingUtilities.invokeLater diz ao java quando o computador estiver pronto e
        //seguro rode a criacao dessa tela nessa linha
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            //aqui estamos escrevendo o metodo run
            //que veio de ruunnable do tipo interface que é obtigatorio a sobreescrever
            //e com isso quando der o run faz new janela principal que manda criar essa janela
            //de 760x560
            @Override
            public void run() {
                new JanelaPrincipal();
            }
        });
    }
}