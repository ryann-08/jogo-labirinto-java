package org.example.telas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import org.example.visual.Cores;
import org.example.visual.EstiloVisual;

// Tela de selecao das 5 fases (Fase 1 liberada e Fases 2 a 5 bloqueadas)
public class TelaFases {

    private static final int TOTAL_FASES = 5;

    private final int largura;
    private final int altura;
    private final boolean[] liberadas = {true, false, false, false, false};
    private final Rectangle[] cards = new Rectangle[TOTAL_FASES];
    private Rectangle botaoVoltar;

    private int cardHover = -1;
    private boolean voltarHover = false;

    private final Font fonteNumero = new Font("SansSerif", Font.BOLD, 34);
    private final Font fonteBotao = new Font("SansSerif", Font.BOLD, 18);

    public TelaFases(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        configurarLayout();
    }

    // Posiciona os 5 cards de fase e o botao Voltar
    private void configurarLayout() {
        int tamanhoCard = 114;
        int espaco = 18;
        int larguraTotal = (TOTAL_FASES * tamanhoCard) + ((TOTAL_FASES - 1) * espaco);
        int xInicial = (largura - larguraTotal) / 2;
        int yCards = 265;

        for (int i = 0; i < TOTAL_FASES; i++) {
            cards[i] = new Rectangle(xInicial + i * (tamanhoCard + espaco), yCards, tamanhoCard, tamanhoCard);
        }

        botaoVoltar = new Rectangle((largura - 180) / 2, 430, 180, 46);
    }

    // Libera uma fase especifica (1 a 5)
    public void desbloquearFase(int numeroFase) {
        if (numeroFase >= 1 && numeroFase <= TOTAL_FASES) {
            liberadas[numeroFase - 1] = true;
        }
    }

    // Define a fase maxima liberada de uma vez (para o Ryan atualizar a progressao)
    public void setFaseMaximaDesbloqueada(int faseMaxima) {
        for (int i = 0; i < TOTAL_FASES; i++) {
            liberadas[i] = (i < faseMaxima);
        }
    }

    public void atualizarMouse(int x, int y) {
        cardHover = -1;
        for (int i = 0; i < TOTAL_FASES; i++) {
            if (liberadas[i] && cards[i].contains(x, y)) {
                cardHover = i;
                break;
            }
        }
        voltarHover = botaoVoltar.contains(x, y);
    }

    // Retorna 1 a 5 se clicou em fase liberada, 0 se clicou em VOLTAR, ou -1
    public int verificarClique(int x, int y) {
        if (botaoVoltar.contains(x, y)) {
            return 0;
        }
        for (int i = 0; i < TOTAL_FASES; i++) {
            if (liberadas[i] && cards[i].contains(x, y)) {
                return i + 1;
            }
        }
        return -1;
    }

    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        EstiloVisual.desenharFundo(g2d, largura, altura);
        EstiloVisual.desenharLogo(g2d, largura, 145);

        for (int i = 0; i < TOTAL_FASES; i++) {
            desenharCard(g2d, i);
        }

        // Botao Voltar
        EstiloVisual.desenharBotaoChanfrado(g2d, botaoVoltar, Cores.BTN_FASES_FUNDO, Cores.BTN_FASES_BORDA, voltarHover);
        g2d.setFont(fonteBotao);
        g2d.setColor(Cores.TEXTO);
        FontMetrics fm = g2d.getFontMetrics();
        int xTexto = botaoVoltar.x + (botaoVoltar.width - fm.stringWidth("VOLTAR")) / 2;
        int yTexto = botaoVoltar.y + ((botaoVoltar.height - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString("VOLTAR", xTexto, yTexto);
    }

    private void desenharCard(Graphics2D g2d, int indice) {
        Rectangle r = cards[indice];
        boolean aberta = liberadas[indice];
        boolean hover = (indice == cardHover);

        Color fundo = aberta ? Cores.BTN_JOGAR_FUNDO : Cores.FASE_BLOQUEADA_FUNDO;
        Color borda = aberta ? Cores.BTN_JOGAR_BORDA : Cores.FASE_BLOQUEADA_BORDA;
        EstiloVisual.desenharBotaoChanfrado(g2d, r, fundo, borda, hover);

        if (aberta) {
            desenharCantoneiras(g2d, r, hover ? Cores.BOTAO_HOVER : Color.WHITE);
        }

        // Numero da fase
        g2d.setFont(fonteNumero);
        g2d.setColor(aberta ? Cores.TEXTO : Cores.CADEADO);
        String numero = String.valueOf(indice + 1);
        FontMetrics fm = g2d.getFontMetrics();
        int xNum = r.x + (r.width - fm.stringWidth(numero)) / 2;
        g2d.drawString(numero, xNum, r.y + 46);

        // Estrelas se liberada, cadeado se bloqueada
        int centroX = r.x + r.width / 2;
        if (aberta) {
            EstiloVisual.desenharEstrela(g2d, centroX - 28, r.y + 82, 11);
            EstiloVisual.desenharEstrela(g2d, centroX, r.y + 82, 11);
            EstiloVisual.desenharEstrela(g2d, centroX + 28, r.y + 82, 11);
        } else {
            EstiloVisual.desenharCadeado(g2d, centroX, r.y + 82);
        }
    }

    // Cantos iluminados no card liberado
    private void desenharCantoneiras(Graphics2D g2d, Rectangle r, Color cor) {
        int m = 6;
        int tam = 16;
        g2d.setColor(cor);
        g2d.setStroke(new BasicStroke(3));

        g2d.drawLine(r.x - m, r.y - m, r.x - m + tam, r.y - m);
        g2d.drawLine(r.x - m, r.y - m, r.x - m, r.y - m + tam);

        g2d.drawLine(r.x + r.width + m, r.y - m, r.x + r.width + m - tam, r.y - m);
        g2d.drawLine(r.x + r.width + m, r.y - m, r.x + r.width + m, r.y - m + tam);

        g2d.drawLine(r.x - m, r.y + r.height + m, r.x - m + tam, r.y + r.height + m);
        g2d.drawLine(r.x - m, r.y + r.height + m, r.x - m, r.y + r.height + m - tam);

        g2d.drawLine(r.x + r.width + m, r.y + r.height + m, r.x + r.width + m - tam, r.y + r.height + m);
        g2d.drawLine(r.x + r.width + m, r.y + r.height + m, r.x + r.width + m, r.y + r.height + m - tam);
    }
}