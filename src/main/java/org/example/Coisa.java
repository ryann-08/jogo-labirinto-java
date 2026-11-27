package org.example;

import java.awt.Graphics2D;

public abstract class Coisa {

    private int linha;
    private int coluna;

    public Coisa(int linha, int coluna) {
        validarPosicao(linha, coluna);
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    protected void definirPosicao(int linha, int coluna) {
        validarPosicao(linha, coluna);
        this.linha = linha;
        this.coluna = coluna;
    }

    private void validarPosicao(int linha, int coluna) {
        if (linha < 0 || coluna < 0) {
            throw new IllegalArgumentException(
                    "As coordenadas não podem ser negativas."
            );
        }
    }


    public abstract void desenhar(Graphics2D g);
}