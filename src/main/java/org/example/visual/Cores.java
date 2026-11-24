package org.example.visual;

import java.awt.Color;

// Central oficial de cores do projeto Labirinto SP
public final class Cores {

    // Paleta principal das telas e do labirinto
    public static final Color FUNDO = Color.decode("#0B1026");
    public static final Color PAREDE = Color.decode("#35206D");
    public static final Color PAREDE_CLARA = Color.decode("#5B3BB0");
    public static final Color PAREDE_SOMBRA = Color.decode("#1E1145");
    public static final Color DESTAQUE = Color.decode("#7045C5");
    public static final Color BORDA_TELA = Color.decode("#4C32A8");

    // Cores das entidades do jogo
    public static final Color JOGADOR = Color.decode("#36DDF2");
    public static final Color PONTUACAO = Color.decode("#FFE16B");
    public static final Color PONTO = PONTUACAO;
    public static final Color INIMIGO = Color.decode("#FF527A");
    public static final Color SAIDA = Color.decode("#A8F06A");
    public static final Color ESPINHO = Color.decode("#FF7A2F");

    // Elementos de UI e botoes
    public static final Color BOTAO_NORMAL = Color.decode("#168CAB");
    public static final Color BOTAO_HOVER = Color.decode("#36DDF2");
    public static final Color BOTAO_ERRO = Color.decode("#C83E69");
    public static final Color TEXTO = Color.decode("#FFFFFF");
    public static final Color TEXTO_SUAVE = Color.decode("#C8C2E8");

    // Cores dos 4 botoes do menu inicial
    public static final Color BTN_JOGAR_FUNDO = Color.decode("#621DC9");
    public static final Color BTN_JOGAR_BORDA = Color.decode("#C07BFF");
    public static final Color BTN_FASES_FUNDO = Color.decode("#0B4B6B");
    public static final Color BTN_FASES_BORDA = Color.decode("#36DDF2");
    public static final Color BTN_CONFIG_FUNDO = Color.decode("#1C154D");
    public static final Color BTN_CONFIG_BORDA = Color.decode("#7045C5");
    public static final Color BTN_SAIR_FUNDO = Color.decode("#591032");
    public static final Color BTN_SAIR_BORDA = Color.decode("#C83E69");

    // Fases bloqueadas, fundo do labirinto e caixa de pausa
    public static final Color FASE_BLOQUEADA_FUNDO = Color.decode("#1F1A42");
    public static final Color FASE_BLOQUEADA_BORDA = Color.decode("#6F6199");
    public static final Color CADEADO = Color.decode("#8E82B5");
    public static final Color FUNDO_LABIRINTO = new Color(11, 16, 38, 225);
    public static final Color FUNDO_PAUSA = new Color(8, 10, 26, 200);
    public static final Color CAIXA_PAUSA = Color.decode("#16153D");

    private Cores() {
    }
}