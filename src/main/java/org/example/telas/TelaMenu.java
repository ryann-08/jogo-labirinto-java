package org.example.telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import org.example.visual.Cores;
import org.example.visual.EstiloVisual;

// Tela inicial do jogo com os 4 botoes principais
public class TelaMenu {

    private final int largura;
    private final int altura;

    private final String[] textos = {"JOGAR", "FASES", "CONFIGURAÇÕES", "SAIR"};
    private final String[] acoes = {"JOGAR", "FASES", "CONFIGURACOES", "SAIR"};
    private final Color[] coresFundo = {
            Cores.BTN_JOGAR_FUNDO, Cores.BTN_FASES_FUNDO, Cores.BTN_CONFIG_FUNDO, Cores.BTN_SAIR_FUNDO
    };
    private final Color[] coresBorda = {
            Cores.BTN_JOGAR_BORDA, Cores.BTN_FASES_BORDA, Cores.BTN_CONFIG_BORDA, Cores.BTN_SAIR_BORDA
    };

    private final Rectangle[] botoes = new Rectangle[4];
    private int botaoHover = -1;
    private final Font fonteBotao = new Font("SansSerif", Font.BOLD, 21);

    public TelaMenu(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        configurarBotoes();
    }

    // Posiciona os 4 botoes centralizados abaixo do logo
    private void configurarBotoes() {
        int larguraBtn = 270;
        int alturaBtn = 52;
        int x = (largura - larguraBtn) / 2;
        int yInicial = 250;
        int espaco = 64;

        for (int i = 0; i < 4; i++) {
            botoes[i] = new Rectangle(x, yInicial + i * espaco, larguraBtn, alturaBtn);
        }
    }

    // Destaca o botao quando o mouse passa por cima
    public void atualizarMouse(int x, int y) {
        botaoHover = -1;
        for (int i = 0; i < botoes.length; i++) {
            if (botoes[i].contains(x, y)) {
                botaoHover = i;
                break;
            }
        }
    }

    // Retorna qual botao foi clicado ("JOGAR", "FASES", "CONFIGURACOES" ou "SAIR")
    public String verificarClique(int x, int y) {
        for (int i = 0; i < botoes.length; i++) {
            if (botoes[i].contains(x, y)) {
                return acoes[i];
            }
        }
        return null;
    }

    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        EstiloVisual.desenharFundo(g2d, largura, altura);
        EstiloVisual.desenharLogo(g2d, largura, 145);

        // Desenha os 4 botoes com icones
        g2d.setFont(fonteBotao);
        for (int i = 0; i < botoes.length; i++) {
            Rectangle btn = botoes[i];
            EstiloVisual.desenharBotaoChanfrado(g2d, btn, coresFundo[i], coresBorda[i], i == botaoHover);

            int centroIconeX = btn.x + 42;
            int centroY = btn.y + btn.height / 2;
            if (i == 0) {
                EstiloVisual.desenharIconePlay(g2d, centroIconeX, centroY);
            } else if (i == 1) {
                EstiloVisual.desenharIconeGrade(g2d, centroIconeX, centroY);
            } else if (i == 2) {
                EstiloVisual.desenharIconeEngrenagem(g2d, centroIconeX, centroY);
            } else {
                EstiloVisual.desenharIconeSair(g2d, centroIconeX, centroY);
            }

            // Texto do botao
            g2d.setColor(Cores.TEXTO);
            FontMetrics fm = g2d.getFontMetrics();
            int xTexto = btn.x + 70 + ((btn.width - 80) - fm.stringWidth(textos[i])) / 2;
            int yTexto = btn.y + ((btn.height - fm.getHeight()) / 2) + fm.getAscent();
            g2d.drawString(textos[i], xTexto, yTexto);
        }
    }
}