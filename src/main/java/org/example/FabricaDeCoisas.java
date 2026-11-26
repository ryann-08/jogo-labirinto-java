package org.example;

public interface FabricaDeCoisas {
    /**
     * Cria um objeto do jogo de acordo com o símbolo recebido.
     *
     * @param simbolo símbolo que representa o objeto na fase
     * @param linha linha onde o objeto será posicionado
     * @param coluna coluna onde o objeto será posicionado
     * @return objeto criado pela fábrica
     */
    Coisa criar(char simbolo, int linha, int coluna);

}
