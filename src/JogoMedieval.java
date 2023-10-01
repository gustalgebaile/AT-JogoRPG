import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract class Personagem {
    private String nome;
    private int pontosVida;
    private int forca;
    private int defesa;
    private int agilidade;
    private int fatorDano;

    public Personagem(String nome, int pontosVida, int forca, int defesa, int agilidade, int fatorDano) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.forca = forca;
        this.defesa = defesa;
        this.agilidade = agilidade;
        this.fatorDano = fatorDano;
    }

    public String getNome() {
        return nome;
    }

    public boolean estaVivo() {
        return pontosVida > 0;
    }

    public int calcularIniciativa() {
        Random random = new Random();
        return random.nextInt(10) + agilidade;
    }

    public int calcularAtaque() {
        Random random = new Random();
        return random.nextInt(10) + agilidade + forca;
    }

    public int calcularDefesa() {
        Random random = new Random();
        return random.nextInt(10) + agilidade + defesa;
    }

    public int calcularDano() {
        Random random = new Random();
        int dado = random.nextInt(fatorDano) + 1;
        return dado * forca;
    }

    public void receberDano(int dano) {
        pontosVida -= dano;
        if (pontosVida < 0) {
            pontosVida = 0;
        }
    }
}

class Guerreiro extends Personagem {
    public Guerreiro(String nome) {
        super(nome, 12, 4, 3, 3, 4);
    }
}

class Barbaro extends Personagem {
    public Barbaro(String nome) {
        super(nome, 13, 6, 1, 3, 6);
    }
}

class Paladino extends Personagem {
    public Paladino(String nome) {
        super(nome, 15, 2, 5, 1, 4);
    }
}

class MortoVivo extends Personagem {
    public MortoVivo() {
        super("Morto-Vivo", 25, 4, 0, 1, 4);
    }
}

class Orc extends Personagem {
    public Orc() {
        super("Orc", 20, 6, 2, 2, 8);
    }
}

class Kobold extends Personagem {
    public Kobold() {
        super("Kobold", 20, 4, 2, 4, 2);
    }
}

public class JogoMedieval {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do jogador: ");
        String nomeJogador = scanner.nextLine();

        System.out.println("Escolha sua classe de herói:");
        System.out.println("1. Guerreiro");
        System.out.println("2. Bárbaro");
        System.out.println("3. Paladino");
        int escolhaHeroi = scanner.nextInt();

        Personagem heroi = criarHeroi(escolhaHeroi, nomeJogador);
        Personagem monstro = criarMonstroAleatorio();

        int rodada = 1;

        while (heroi.estaVivo() && monstro.estaVivo()) {
            System.out.println("----- Rodada " + rodada + " -----");

            int iniciativaHeroi = heroi.calcularIniciativa();
            int iniciativaMonstro = monstro.calcularIniciativa();

            if (iniciativaHeroi > iniciativaMonstro) {
                realizarAtaque(heroi, monstro);
            } else if (iniciativaMonstro > iniciativaHeroi) {
                realizarAtaque(monstro, heroi);
            } else {
                System.out.println("Empate na iniciativa. Nova rodada.");
            }

            rodada++;
        }

        String resultado = heroi.estaVivo() ? "GANHOU" : "PERDEU";
        registrarResultadoBatalha(nomeJogador, heroi.getClass().getSimpleName(), resultado, monstro.getClass().getSimpleName(), rodada - 1);
    }

    private static Personagem criarHeroi(int escolha, String nomeJogador) {
        switch (escolha) {
            case 1:
                return new Guerreiro(nomeJogador);
            case 2:
                return new Barbaro(nomeJogador);
            case 3:
                return new Paladino(nomeJogador);
            default:
                throw new IllegalArgumentException("Escolha de herói inválida.");
        }
    }

    private static Personagem criarMonstroAleatorio() {
        Random random = new Random();
        int escolhaMonstro = random.nextInt(3) + 1;

        switch (escolhaMonstro) {
            case 1:
                return new MortoVivo();
            case 2:
                return new Orc();
            case 3:
                return new Kobold();
            default:
                throw new IllegalStateException("Erro ao criar o monstro.");
        }
    }

    private static void realizarAtaque(Personagem atacante, Personagem defensor) {
        int fatorAtaque = atacante.calcularAtaque();
        int fatorDefesa = defensor.calcularDefesa();

        if (fatorAtaque > fatorDefesa) {
            int dano = atacante.calcularDano();
            defensor.receberDano(dano);
            System.out.println(atacante.getNome() + " causou " + dano + " de dano a " + defensor.getNome());
        } else {
            System.out.println(atacante.getNome() + " errou o ataque em " + defensor.getNome());
        }
    }

    private static void registrarResultadoBatalha(String nomeJogador, String classeHeroi, String resultado, String classeMonstro, int rodadas) {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date();
        String dataFormatada = formatoData.format(dataAtual);

        String nomeArquivo = nomeJogador + ".csv";

        try {
            FileWriter arquivo = new FileWriter("temp/" + nomeArquivo, true);
            arquivo.write(dataFormatada + ";" + classeHeroi + ";" + resultado + ";" + classeMonstro + ";" + rodadas + "\n");
            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo de log.");
        }
    }
}
