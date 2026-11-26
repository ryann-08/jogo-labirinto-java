package org.example;

    public record Posicao(int linha, int coluna) {

        public Posicao {
            if (linha < 0 || coluna < 0) {
                throw new IllegalArgumentException(
                        "As coordenadas não podem ser negativas."
                );
            }
        }
    }


