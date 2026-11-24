package org.example.visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// Funcoes de desenho compartilhadas entre as telas do jogo
public final class EstiloVisual {

    private EstiloVisual() {
    }

    // Desenha o fundo de Sao Paulo com a moldura roxa
    public static void desenharFundo(Graphics2D g2d, int largura, int altura) {
        g2d.setColor(Cores.FUNDO);
        g2d.fillRect(0, 0, largura, altura);

        BufferedImage fundo = Imagens.obterFundoSP();
        if (fundo != null) {
            g2d.drawImage(fundo, 12, 12, largura - 24, altura - 24, null);
        } else {
            // Fundo alternativo caso a imagem nao esteja na pasta
            GradientPaint ceu = new GradientPaint(0, 0, Cores.FUNDO, 0, altura, Cores.PAREDE);
            g2d.setPaint(ceu);
            g2d.fillRect(12, 12, largura - 24, altura - 24);
        }

        g2d.setColor(Cores.BORDA_TELA);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(12, 12, largura - 24, altura - 24, 14, 14);
    }

    // Desenha o logo "LABIRINTO SP" com sombra e linhas laterais
    public static void desenharLogo(Graphics2D g2d, int largura, int yTopo) {
        Font fonteTitulo = new Font("SansSerif", Font.BOLD | Font.ITALIC, 62);
        Font fonteSP = new Font("SansSerif", Font.BOLD | Font.ITALIC, 58);

        // Titulo "LABIRINTO"
        g2d.setFont(fonteTitulo);
        FontMetrics fm = g2d.getFontMetrics();
        String titulo = "LABIRINTO";
        int xTitulo = (largura - fm.stringWidth(titulo)) / 2;

        g2d.setColor(Cores.FUNDO);
        g2d.drawString(titulo, xTitulo + 4, yTopo + 4);
        g2d.setPaint(new GradientPaint(0, yTopo - 50, Color.WHITE, 0, yTopo, Cores.TEXTO_SUAVE));
        g2d.drawString(titulo, xTitulo, yTopo);

        // Subtitulo "SP"
        g2d.setFont(fonteSP);
        FontMetrics fmSP = g2d.getFontMetrics();
        String sp = "SP";
        int larguraSP = fmSP.stringWidth(sp);
        int xSP = (largura - larguraSP) / 2;
        int ySP = yTopo + 56;

        // Linhas horizontais dos dois lados do "SP"
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Cores.DESTAQUE);
        int[] deslocamentos = {-34, -22, -10};
        int[] tamanhos = {115, 85, 55};
        for (int i = 0; i < 3; i++) {
            int yLinha = ySP + deslocamentos[i];
            g2d.drawLine(xSP - 20 - tamanhos[i], yLinha, xSP - 20, yLinha);
            g2d.drawLine(xSP + larguraSP + 20, yLinha, xSP + larguraSP + 20 + tamanhos[i], yLinha);
        }

        // Sombra e cor roxa do "SP"
        g2d.setColor(Cores.FUNDO);
        g2d.drawString(sp, xSP + 4, ySP + 4);
        g2d.setPaint(new GradientPaint(0, ySP - 45, Color.decode("#B46BFF"), 0, ySP, Color.decode("#7015E0")));
        g2d.drawString(sp, xSP, ySP);
    }

    // Cria a forma de retangulo com cantos cortados
    public static Polygon criarPoligonoChanfrado(Rectangle r, int corte) {
        int x = r.x;
        int y = r.y;
        int w = r.width;
        int h = r.height;
        int[] xs = {x + corte, x + w - corte, x + w, x + w, x + w - corte, x + corte, x, x};
        int[] ys = {y, y, y + corte, y + h - corte, y + h, y + h, y + h - corte, y + corte};
        return new Polygon(xs, ys, 8);
    }

    // Desenha um botao chanfrado com destaque ao passar o mouse
    public static void desenharBotaoChanfrado(Graphics2D g2d, Rectangle r, Color fundo, Color borda, boolean hover) {
        Polygon forma = criarPoligonoChanfrado(r, 11);

        // Sombra abaixo do botao
        g2d.setColor(new Color(0, 0, 0, 140));
        g2d.translate(0, 4);
        g2d.fillPolygon(forma);
        g2d.translate(0, -4);

        // Fundo e borda
        g2d.setColor(hover ? fundo.brighter() : fundo);
        g2d.fillPolygon(forma);

        g2d.setColor(hover ? Color.WHITE : borda);
        g2d.setStroke(new BasicStroke(hover ? 4f : 3f));
        g2d.drawPolygon(forma);
    }

    // Icone de Play
    public static void desenharIconePlay(Graphics2D g2d, int cx, int cy) {
        g2d.setColor(Cores.TEXTO);
        int[] xs = {cx - 7, cx - 7, cx + 10};
        int[] ys = {cy - 10, cy + 10, cy};
        g2d.fillPolygon(xs, ys, 3);
    }

    // Icone de Grade 2x2 (Fases)
    public static void desenharIconeGrade(Graphics2D g2d, int cx, int cy) {
        g2d.setColor(Cores.BOTAO_HOVER);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawRect(cx - 10, cy - 10, 9, 9);
        g2d.drawRect(cx + 1, cy - 10, 9, 9);
        g2d.drawRect(cx - 10, cy + 1, 9, 9);
        g2d.drawRect(cx + 1, cy + 1, 9, 9);
    }

    // Icone de Engrenagem (Configuracoes)
    public static void desenharIconeEngrenagem(Graphics2D g2d, int cx, int cy) {
        g2d.setColor(Cores.TEXTO_SUAVE);
        for (int i = 0; i < 8; i++) {
            double angulo = Math.toRadians(i * 45);
            int dx = (int) Math.round(Math.cos(angulo) * 10);
            int dy = (int) Math.round(Math.sin(angulo) * 10);
            g2d.fillRect(cx + dx - 3, cy + dy - 3, 6, 6);
        }
        g2d.fillOval(cx - 8, cy - 8, 16, 16);
        g2d.setColor(Cores.BTN_CONFIG_FUNDO);
        g2d.fillOval(cx - 4, cy - 4, 8, 8);
    }

    // Icone de Saida (Sair)
    public static void desenharIconeSair(Graphics2D g2d, int cx, int cy) {
        g2d.setColor(Cores.TEXTO);
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.drawLine(cx - 9, cy - 10, cx + 2, cy - 10);
        g2d.drawLine(cx - 9, cy - 10, cx - 9, cy + 10);
        g2d.drawLine(cx - 9, cy + 10, cx + 2, cy + 10);
        g2d.drawLine(cx - 2, cy, cx + 10, cy);
        g2d.drawLine(cx + 5, cy - 5, cx + 10, cy);
        g2d.drawLine(cx + 5, cy + 5, cx + 10, cy);
    }

    // Icone de Cadeado (Fases bloqueadas)
    public static void desenharCadeado(Graphics2D g2d, int cx, int cy) {
        g2d.setColor(Cores.CADEADO);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawArc(cx - 9, cy - 20, 18, 18, 0, 180);
        g2d.fillRoundRect(cx - 14, cy - 10, 28, 22, 5, 5);
        g2d.setColor(Cores.FASE_BLOQUEADA_FUNDO);
        g2d.fillOval(cx - 3, cy - 4, 6, 6);
        g2d.fillRect(cx - 2, cy + 1, 4, 6);
    }

    // Icone de Estrela amarela (Fase liberada)
    public static void desenharEstrela(Graphics2D g2d, int cx, int cy, int raio) {
        int[] xs = new int[10];
        int[] ys = new int[10];
        for (int i = 0; i < 10; i++) {
            double angulo = Math.toRadians(-90 + i * 36);
            int r = (i % 2 == 0) ? raio : raio / 2;
            xs[i] = cx + (int) Math.round(Math.cos(angulo) * r);
            ys[i] = cy + (int) Math.round(Math.sin(angulo) * r);
        }
        g2d.setColor(Cores.PONTUACAO);
        g2d.fillPolygon(xs, ys, 10);
    }

    // Icone de Coracao em pixel art (Vidas no HUD)
    public static void desenharCoracaoPixel(Graphics2D g2d, int x, int y, boolean cheio) {
        int[][] matriz = {
                {0, 1, 1, 0, 0, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0}
        };
        int p = 3;
        g2d.setColor(cheio ? Cores.INIMIGO : Cores.FASE_BLOQUEADA_BORDA);
        for (int l = 0; l < matriz.length; l++) {
            for (int c = 0; c < matriz[l].length; c++) {
                if (matriz[l][c] == 1) {
                    g2d.fillRect(x + c * p, y + l * p, p, p);
                }
            }
        }
    }
}