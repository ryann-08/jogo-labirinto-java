package org.example.telas;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import org.example.visual.Cores;
import org.example.visual.EstiloVisual;

// Tela aberta pelo botao CONFIGURACOES do menu
public class TelaConfiguracoes {

    private final int largura;
    private final int altura;

    private boolean somLigado = true;
    private boolean musicaLigada = true;

    private final Rectangle btnSom;
    private final Rectangle btnMusica;
    private final Rectangle btnVoltar;
    private int hover = -1;

    private final Font fonteTitulo = new Font("SansSerif", Font.BOLD, 28);
    private final Font fonteBotao = new Font("SansSerif", Font.BOLD, 18);
    private final Font fonteInfo = new Font("SansSerif", Font.PLAIN, 15);

    public TelaConfiguracoes(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        int x = (largura - 280) / 2;
        btnSom = new Rectangle(x, 225, 280, 48);
        btnMusica = new Rectangle(x, 290, 280, 48);
        btnVoltar = new Rectangle((largura - 180) / 2, 415, 180, 46);
    }

    public boolean isSomLigado() {
        return somLigado;
    }

    public boolean isMusicaLigada() {
        return musicaLigada;
    }

    public void atualizarMouse(int x, int y) {
        if (btnSom.contains(x, y)) {
            hover = 0;
        } else if (btnMusica.contains(x, y)) {
            hover = 1;
        } else if (btnVoltar.contains(x, y)) {
            hover = 2;
        } else {
            hover = -1;
        }
    }

    // Alterna som/musica ou retorna "VOLTAR" para o menu
    public String verificarClique(int x, int y) {
        if (btnSom.contains(x, y)) {
            somLigado = !somLigado;
            return "SOM";
        }
        if (btnMusica.contains(x, y)) {
            musicaLigada = !musicaLigada;
            return "MUSICA";
        }
        if (btnVoltar.contains(x, y)) {
            return "VOLTAR";
        }
        return null;
    }

    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        EstiloVisual.desenharFundo(g2d, largura, altura);

        // Caixa central
        Rectangle caixa = new Rectangle((largura - 380) / 2, 130, 380, 360);
        EstiloVisual.desenharBotaoChanfrado(g2d, caixa, Cores.CAIXA_PAUSA, Cores.DESTAQUE, false);

        g2d.setFont(fonteTitulo);
        g2d.setColor(Cores.BOTAO_HOVER);
        desenharTextoCentralizado(g2d, "CONFIGURAÇÕES", 185);

        // Botao de Efeitos Sonoros
        String txtSom = "EFEITOS SONOROS: " + (somLigado ? "ON" : "OFF");
        EstiloVisual.desenharBotaoChanfrado(g2d, btnSom,
                somLigado ? Cores.BTN_FASES_FUNDO : Cores.FASE_BLOQUEADA_FUNDO,
                somLigado ? Cores.BOTAO_HOVER : Cores.CADEADO, hover == 0);
        desenharTextoNoBotao(g2d, btnSom, txtSom);

        // Botao de Musica
        String txtMusica = "MÚSICA: " + (musicaLigada ? "ON" : "OFF");
        EstiloVisual.desenharBotaoChanfrado(g2d, btnMusica,
                musicaLigada ? Cores.BTN_FASES_FUNDO : Cores.FASE_BLOQUEADA_FUNDO,
                musicaLigada ? Cores.BOTAO_HOVER : Cores.CADEADO, hover == 1);
        desenharTextoNoBotao(g2d, btnMusica, txtMusica);

        // Dica de controles
        g2d.setFont(fonteInfo);
        g2d.setColor(Cores.TEXTO_SUAVE);
        desenharTextoCentralizado(g2d, "Setas: Mover  |  P: Pausar  |  ESC: Menu", 378);

        // Botao Voltar
        EstiloVisual.desenharBotaoChanfrado(g2d, btnVoltar, Cores.BTN_JOGAR_FUNDO, Cores.BTN_JOGAR_BORDA, hover == 2);
        desenharTextoNoBotao(g2d, btnVoltar, "VOLTAR");
    }

    private void desenharTextoNoBotao(Graphics2D g2d, Rectangle r, String texto) {
        g2d.setFont(fonteBotao);
        g2d.setColor(Cores.TEXTO);
        FontMetrics fm = g2d.getFontMetrics();
        int x = r.x + (r.width - fm.stringWidth(texto)) / 2;
        int y = r.y + ((r.height - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(texto, x, y);
    }

    private void desenharTextoCentralizado(Graphics2D g2d, String texto, int y) {
        FontMetrics fm = g2d.getFontMetrics();
        int x = (largura - fm.stringWidth(texto)) / 2;
        g2d.drawString(texto, x, y);
    }
}