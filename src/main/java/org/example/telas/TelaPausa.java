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

// Caixinha visual de pausa exibida por cima do jogo
public class TelaPausa {

    private final int largura;
    private final int altura;

    private final String[] opcoes = {"CONTINUAR", "REINICIAR FASE", "VOLTAR AO MENU"};
    private final Color[] coresFundo = {Cores.BTN_JOGAR_FUNDO, Cores.BTN_FASES_FUNDO, Cores.BTN_SAIR_FUNDO};
    private final Color[] coresBorda = {Cores.BTN_JOGAR_BORDA, Cores.BTN_FASES_BORDA, Cores.BTN_SAIR_BORDA};
    private final Rectangle[] botoes = new Rectangle[3];
    private final Rectangle caixa;

    private int opcaoSelecionada = 0;
    private final Font fonteTitulo = new Font("SansSerif", Font.BOLD, 30);
    private final Font fonteBotao = new Font("SansSerif", Font.BOLD, 18);

    public TelaPausa(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.caixa = new Rectangle((largura - 340) / 2, (altura - 320) / 2, 340, 320);

        int xBtn = caixa.x + 45;
        int yInicial = caixa.y + 92;
        for (int i = 0; i < 3; i++) {
            botoes[i] = new Rectangle(xBtn, yInicial + i * 66, 250, 48);
        }
    }

    // Navegacao opcional pelo teclado
    public void proximaOpcao() {
        opcaoSelecionada = (opcaoSelecionada + 1) % opcoes.length;
    }

    public void opcaoAnterior() {
        opcaoSelecionada = (opcaoSelecionada - 1 + opcoes.length) % opcoes.length;
    }

    public String getOpcaoSelecionada() {
        return opcoes[opcaoSelecionada];
    }

    // Destaca o botao quando o mouse passa por cima
    public void atualizarMouse(int x, int y) {
        for (int i = 0; i < botoes.length; i++) {
            if (botoes[i].contains(x, y)) {
                opcaoSelecionada = i;
                break;
            }
        }
    }

    // Retorna "CONTINUAR", "REINICIAR FASE", "VOLTAR AO MENU" ou null
    public String verificarClique(int x, int y) {
        for (int i = 0; i < botoes.length; i++) {
            if (botoes[i].contains(x, y)) {
                return opcoes[i];
            }
        }
        return null;
    }

    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Escurece o fundo do jogo atras da caixinha
        g2d.setColor(Cores.FUNDO_PAUSA);
        g2d.fillRect(0, 0, largura, altura);

        // Caixinha central
        EstiloVisual.desenharBotaoChanfrado(g2d, caixa, Cores.CAIXA_PAUSA, Cores.BOTAO_HOVER, false);

        // Titulo "PAUSA"
        g2d.setFont(fonteTitulo);
        g2d.setColor(Cores.TEXTO);
        FontMetrics fmTitulo = g2d.getFontMetrics();
        int xTitulo = caixa.x + (caixa.width - fmTitulo.stringWidth("PAUSA")) / 2;
        g2d.drawString("PAUSA", xTitulo, caixa.y + 56);

        // Desenha os 3 botoes
        g2d.setFont(fonteBotao);
        for (int i = 0; i < opcoes.length; i++) {
            Rectangle btn = botoes[i];
            EstiloVisual.desenharBotaoChanfrado(g2d, btn, coresFundo[i], coresBorda[i], i == opcaoSelecionada);

            g2d.setColor(Cores.TEXTO);
            FontMetrics fm = g2d.getFontMetrics();
            int xTexto = btn.x + (btn.width - fm.stringWidth(opcoes[i])) / 2;
            int yTexto = btn.y + ((btn.height - fm.getHeight()) / 2) + fm.getAscent();
            g2d.drawString(opcoes[i], xTexto, yTexto);
        }
    }
}