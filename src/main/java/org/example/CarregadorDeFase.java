
package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarregadorDeFase {

    private final FabricaDeCoisas fabrica;


    // Construtor: recebe a fábrica responsável pela criação dos objetos

    public CarregadorDeFase(FabricaDeCoisas fabrica) {

        this.fabrica = Objects.requireNonNull(
                fabrica,
                "A fábrica de coisas não pode ser null."
        );
    }


    // Método principal: carrega um arquivo e retorna um Mapa

    public Mapa carregar(String nomeDoArquivo) {

        if (nomeDoArquivo == null || nomeDoArquivo.isBlank()) {

            throw new IllegalArgumentException(
                    "O nome do arquivo não pode ser vazio."
            );
        }

        String caminho = "/fases/" + nomeDoArquivo;

        // Procura o arquivo dentro dos recursos do projeto

        InputStream input = getClass().getResourceAsStream(caminho);

        if (input == null) {

            throw new IllegalArgumentException(
                    "Arquivo de fase não encontrado: " + caminho
            );
        }

        List<String> linhasArquivo = new ArrayList<>();

        // Leitura preservando os espaços existentes nas linhas

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                input,
                                StandardCharsets.UTF_8
                        )
                )
        ) {

            String linha;

            while ((linha = reader.readLine()) != null) {

                linhasArquivo.add(linha);
            }

        } catch (IOException e) {

            throw new IllegalStateException(
                    "Erro ao ler o arquivo de fase: " + nomeDoArquivo,
                    e
            );
        }

        // Valida toda a estrutura antes de criar os objetos

        int linhas = linhasArquivo.size();

        int colunas = validarArquivo(linhasArquivo, nomeDoArquivo);

        boolean[][] paredes = new boolean[linhas][colunas];

        List<Coisa> coisas = new ArrayList<>();

        Posicao posicaoInicial = null;


        // Interpretação dos símbolos do arquivo

        for (int linha = 0; linha < linhas; linha++) {

            String conteudoLinha = linhasArquivo.get(linha);

            for (int coluna = 0; coluna < colunas; coluna++) {

                char simbolo = conteudoLinha.charAt(coluna);

                switch (simbolo) {

                    case '#' -> {

                        // Parede

                        paredes[linha][coluna] = true;
                    }

                    case 'J' -> {

                        // Posição inicial do jogador

                        posicaoInicial = new Posicao(linha, coluna);

                        paredes[linha][coluna] = false;
                    }

                    case ' ' -> {

                        // Chão vazio

                        paredes[linha][coluna] = false;
                    }

                    case '.', '^', 'S', 'I' -> {

                        // Cria o objeto por meio da fábrica

                        paredes[linha][coluna] = false;

                        Coisa coisa = fabrica.criar(
                                simbolo,
                                linha,
                                coluna
                        );

                        if (coisa == null) {

                            throw new IllegalStateException(
                                    "A fábrica retornou null para o símbolo '"
                                            + simbolo + "' na posição ("
                                            + linha + ", " + coluna + ")."
                            );
                        }

                        if (coisa.getLinha() != linha
                                || coisa.getColuna() != coluna) {

                            throw new IllegalStateException(
                                    "A fábrica criou um objeto na posição incorreta."
                            );
                        }

                        coisas.add(coisa);
                    }

                    default -> {

                        // Não deveria ocorrer após a validação

                        throw new IllegalArgumentException(
                                "Símbolo inválido encontrado: " + simbolo
                        );
                    }
                }
            }
        }

        // Monta e retorna o mapa definitivo

        return new Mapa(
                paredes,
                coisas,
                posicaoInicial
        );
    }


    // Valida dimensões, caracteres e quantidade de elementos obrigatórios

    private int validarArquivo(
            List<String> linhasArquivo,
            String nomeDoArquivo
    ) {

        if (linhasArquivo.isEmpty()) {

            throw new IllegalArgumentException(
                    "O arquivo de fase está vazio: " + nomeDoArquivo
            );
        }

        int colunas = linhasArquivo.get(0).length();

        if (colunas == 0) {

            throw new IllegalArgumentException(
                    "O mapa não pode possuir zero colunas."
            );
        }

        int quantidadeJogadores = 0;
        int quantidadeSaidas = 0;


        for (int linha = 0; linha < linhasArquivo.size(); linha++) {

            String conteudo = linhasArquivo.get(linha);

            // Verifica se todas as linhas possuem o mesmo tamanho

            if (conteudo.length() != colunas) {

                throw new IllegalArgumentException(
                        "Mapa não retangular. Linha " + linha
                                + " possui " + conteudo.length()
                                + " colunas, mas deveria possuir " + colunas + "."
                );
            }


            // Percorre os caracteres de cada linha

            for (int coluna = 0; coluna < colunas; coluna++) {

                char simbolo = conteudo.charAt(coluna);

                switch (simbolo) {

                    case '#' -> {
                        // Parede: caractere válido
                    }

                    case ' ' -> {
                        // Chão vazio: caractere válido
                    }

                    case '.' -> {
                        // Ponto: caractere válido
                    }

                    case '^' -> {
                        // Espinho: caractere válido
                    }

                    case 'I' -> {
                        // Inimigo: caractere válido
                    }

                    case 'J' -> {

                        quantidadeJogadores++;
                    }

                    case 'S' -> {

                        quantidadeSaidas++;
                    }

                    default -> {

                        throw new IllegalArgumentException(
                                "Caractere inválido '" + simbolo
                                        + "' na posição (" + linha
                                        + ", " + coluna + ")."
                        );
                    }
                }
            }
        }


        // Exige exatamente um jogador

        if (quantidadeJogadores != 1) {

            throw new IllegalArgumentException(
                    "A fase deve possuir exatamente um jogador (J). "
                            + "Encontrados: " + quantidadeJogadores
            );
        }


        // Exige exatamente uma saída

        if (quantidadeSaidas != 1) {

            throw new IllegalArgumentException(
                    "A fase deve possuir exatamente uma saída (S). "
                            + "Encontradas: " + quantidadeSaidas
            );
        }

        return colunas;
    }
}
