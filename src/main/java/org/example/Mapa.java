
package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mapa {

    // Atributos privados

    private final boolean[][] paredes;
    private final List<Coisa> coisas;

    private final int linhas;
    private final int colunas;

    private final Posicao posicaoInicial;


    // Construtor

    public Mapa(
            boolean[][] paredes,
            List<Coisa> coisas,
            Posicao posicaoInicial
    ) {

        Objects.requireNonNull(
                paredes,
                "A matriz de paredes não pode ser null."
        );

        Objects.requireNonNull(
                posicaoInicial,
                "A posição inicial não pode ser null."
        );

        if (paredes.length == 0 || paredes[0] == null
                || paredes[0].length == 0) {

            throw new IllegalArgumentException(
                    "O mapa precisa possuir linhas e colunas."
            );
        }

        this.linhas = paredes.length;
        this.colunas = paredes[0].length;

        // Cópia defensiva da matriz

        this.paredes = new boolean[linhas][colunas];

        for (int i = 0; i < linhas; i++) {

            if (paredes[i] == null
                    || paredes[i].length != colunas) {

                throw new IllegalArgumentException(
                        "Todas as linhas precisam ter o mesmo tamanho."
                );
            }

            System.arraycopy(
                    paredes[i],
                    0,
                    this.paredes[i],
                    0,
                    colunas
            );
        }

        // Inicialização da lista

        this.coisas = coisas == null
                ? new ArrayList<>()
                : new ArrayList<>(coisas);

        if (this.coisas.contains(null)) {
            throw new IllegalArgumentException(
                    "A lista não pode conter objetos null."
            );
        }

        // Validação da posição inicial

        validarPosicao(
                posicaoInicial.linha(),
                posicaoInicial.coluna()
        );

        if (ehParede(
                posicaoInicial.linha(),
                posicaoInicial.coluna()
        )) {

            throw new IllegalArgumentException(
                    "A posição inicial não pode ser uma parede."
            );
        }

        this.posicaoInicial = posicaoInicial;

        // Validação dos objetos

        for (Coisa coisa : this.coisas) {

            validarPosicao(
                    coisa.getLinha(),
                    coisa.getColuna()
            );

            if (ehParede(
                    coisa.getLinha(),
                    coisa.getColuna()
            )) {

                throw new IllegalArgumentException(
                        "Não é permitido posicionar objetos em paredes."
                );
            }
        }
    }


    // Validação interna das coordenadas

    private void validarPosicao(int linha, int coluna) {

        if (linha < 0 || linha >= linhas
                || coluna < 0 || coluna >= colunas) {

            throw new IllegalArgumentException(
                    "Posição fora dos limites do mapa."
            );
        }
    }


    // Verifica se existe uma parede

    public boolean ehParede(int linha, int coluna) {

        if (linha < 0 || linha >= linhas
                || coluna < 0 || coluna >= colunas) {

            return true;
        }

        return paredes[linha][coluna];
    }


    // Consulta objetos em determinada posição

    public Coisa oQueTem(int linha, int coluna) {

        for (Coisa coisa : coisas) {

            if (coisa.getLinha() == linha
                    && coisa.getColuna() == coluna) {

                return coisa;
            }
        }

        return null;
    }


    // Conta os objetos coletáveis

    public int pontosRestantes() {

        int quantidade = 0;

        for (Coisa coisa : coisas) {

            if (coisa instanceof Coletavel) {
                quantidade++;
            }
        }

        return quantidade;
    }


    // Retorna a posição inicial do jogador

    public Posicao posicaoInicial() {
        return posicaoInicial;
    }


    // Retorna uma cópia não modificável da lista

    public List<Coisa> getCoisas() {
        return List.copyOf(coisas);
    }


    // Remove um objeto do mapa

    public void remover(Coisa coisa) {

        Objects.requireNonNull(
                coisa,
                "O objeto não pode ser null."
        );

        coisas.remove(coisa);
    }


    // Retorna a quantidade de linhas

    public int getLinhas() {
        return linhas;
    }


    // Retorna a quantidade de colunas

    public int getColunas() {
        return colunas;
    }
}

