package org.example; // Altere para o seu pacote atual se usar outro (ex: nucleo)

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


//  GamePanel - O Coração e o Gerenciador do Jogo
//  Responsável por rodar o Game Loop (60 FPS) e gerenciar
//  qual tela está ativa no momento (Troca de Canal)

//esse 'contrato' com o (Runnable) faz que vc crie um metodo run para rodar os 60FPS
//JPanel funciona como a tela de uma TV, diferente da class janela principal que é apenas como se fosse a moldura da tv
//o JPanel deixa vc desenhar na tv - colocar imagens, pq literalmete ele vai ser a tela
public class GamePanel extends JPanel implements Runnable {

    // Resolução fixa definida para o projeto (640x480 - Proporção 4:3)
    public static final int LARGURA = 760;
    public static final int ALTURA = 560;

    // Thread para rodar o Game Loop de forma independente em segundo plano
    private Thread gameThread;
    private boolean rodando = false;

    // FPS alvo (60 quadros por segundo para garantir suavidade)
    private final int FPS = 60;
    private final long TEMPO_POR_FRAME = 1000000000 / FPS; // em nanossegundos

    // Referência para a tela atual que está sendo exibida (Gerenciador de Telas)
    private static Tela telaAtual;

    public GamePanel() {
        // Configurações básicas do painel visual
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBackground(Color.decode("#0B1026")); // Cor de fundo base (Paleta do Maicon)
        setFocusable(true);

        // Adiciona o ouvinte de teclado para capturar comandos e repassar para a tela ativa
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //essse if, é uma caso o usuario por algum motivo apertar alguma tecla antes de qualquer tela ser carregada
                //seria enviado um erro nullPOinterException que crasharia o jogo
                //entao com isso ele fala so repasse a tecla se realmente tiver uma tela mostrando no momento
                if (telaAtual != null) {
                    telaAtual.keyPressed(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (telaAtual != null) {
                    telaAtual.keyReleased(e);
                }
            }
        });
    }

    // Metodo estático para trocar de tela facilmente de qualquer lugar do projeto
    public static void carregarTela(Tela novaTela) {
        telaAtual = novaTela;
    }

    // Inicia a Thread do Game Loop
    //agora a tela esta a 60fps esperando detectar alguma informacao como de alguma tela virar a tela atual
    //e tambem atualiza a logica, se foi apertado algum botao para o lado por exemplo
    public void iniciarGameLoop() {
        gameThread = new Thread(this);
        rodando = true;
        gameThread.start();
    }

    // O metodo run() gerencia o relógio principal do jogo a 60 FPS
    @Override
    public void run() {
        long ultimoTempo = System.nanoTime();
        long tempoAcumulado = 0;

        // O Game Loop principal (Roda enquanto o jogo estiver aberto)
        while (rodando) {
            long agora = System.nanoTime();
            long tempoDecorrido = agora - ultimoTempo;
            ultimoTempo = agora;
            tempoAcumulado += tempoDecorrido;

            // Se passou o tempo necessário para o próximo frame (60 FPS)
            if (tempoAcumulado >= TEMPO_POR_FRAME) {
                atualizar();
                repaint(); // Redesenha a tela chamando o paintComponent
                tempoAcumulado -= TEMPO_POR_FRAME;
            }

            try {
                Thread.sleep(2); // Pequena pausa para aliviar o processador
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Atualiza a lógica da tela atual (ex: posições, colisões, etc.)
    private void atualizar() {
        if (telaAtual != null) {
            telaAtual.atualizar();
        }
    }

    // Responsável por renderizar os gráficos na tela
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Desenha a tela atual ativa no gerenciador
        if (telaAtual != null) {
            telaAtual.desenhar(g2d);
        }
    }
}