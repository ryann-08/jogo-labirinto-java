
package org.example;

public abstract class Coisa {

    private final int linha;
    private final int coluna;

    public Coisa(int linha, int coluna) {

        if (linha < 0 || coluna < 0) {
            throw new IllegalArgumentException(
                    "As coordenadas não podem ser negativas."
            );
        }

        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
}

