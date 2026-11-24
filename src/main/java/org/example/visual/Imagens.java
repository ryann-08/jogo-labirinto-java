package org.example.visual;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

// Carrega imagens uma vez e guarda em cache para uso no jogo
public class Imagens {

    private static final Map<String, BufferedImage> cache = new HashMap<>();

    // Carrega um arquivo da pasta src/main/resources/imagens
    public static void carregar(String nome) {
        if (cache.containsKey(nome)) {
            return;
        }
        try (InputStream entrada = Imagens.class.getResourceAsStream("/imagens/" + nome)) {
            if (entrada != null) {
                cache.put(nome, ImageIO.read(entrada));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: " + nome);
        }
    }

    // Devolve a imagem ja carregada
    public static BufferedImage obter(String nome) {
        if (!cache.containsKey(nome)) {
            carregar(nome);
        }
        return cache.get(nome);
    }

    // Procura o fundo de Sao Paulo nos formatos .jpg ou .png
    public static BufferedImage obterFundoSP() {
        BufferedImage img = obter("fundo_sp.jpg");
        if (img == null) {
            img = obter("fundo_sp.png");
        }
        return img;
    }
}