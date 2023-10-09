import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        registrarLog(nomeJogador, heroi.getClass().getSimpleName(), resultado, monstro.getClass().getSimpleName(), rodada - 1);
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

    private static void registrarLog(String nomeJogador, String classeHeroi, String resultado, String classeMonstro, int rodadas) {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date();
        String dataFormatada = formatoData.format(dataAtual);

        String nomeArquivo = "src/temp/" + nomeJogador + ".csv";

        try {
            FileWriter arquivo = new FileWriter(nomeArquivo, true);
            arquivo.write(dataFormatada + ";" + classeHeroi + ";" + resultado + ";" + classeMonstro + ";" + rodadas + "\n");
            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo de log.");
        }
        RelatorioJogador.gerarRelatorio(nomeJogador);
    }
}
