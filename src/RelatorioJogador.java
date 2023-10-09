import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RelatorioJogador {
    public static void gerarRelatorio(String nomeJogador) {
        String arquivo = "src/temp/" + nomeJogador + ".csv";
        ArrayList<RegistroBatalha> registros = lerRegistros(arquivo);

        if (registros.isEmpty()) {
            System.out.println("Nenhum registro encontrado para o jogador " + nomeJogador);
        } else {
            System.out.println("Relatório de Pontuação para o Jogador " + nomeJogador + ":");
            System.out.println("Heroi mais jogado: " + heroiMaisJogado(registros));
            System.out.println("Monstro mais enfrentado: " + monstroMaisEnfrentado(registros));
            System.out.println("Pontuação total: " + calcularPontuacaoTotal(registros));
        }
    }

    private static ArrayList<RegistroBatalha> lerRegistros(String arquivo) {
        ArrayList<RegistroBatalha> registros = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                if (campos.length == 5) {
                    RegistroBatalha registro = new RegistroBatalha(campos[0], campos[1], campos[2], campos[3], Integer.parseInt(campos[4]));
                    registros.add(registro);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de registros.");
        }

        return registros;
    }

    private static String heroiMaisJogado(ArrayList<RegistroBatalha> registros) {
        Map<String, Integer> contadorHerois = new HashMap<>();

        for (RegistroBatalha registro : registros) {
            String heroi = registro.getHeroi();
            contadorHerois.put(heroi, contadorHerois.getOrDefault(heroi, 0) + 1);
        }

        String heroiMaisJogado = "";
        int maxJogado = 0;

        for (Map.Entry<String, Integer> entry : contadorHerois.entrySet()) {
            if (entry.getValue() > maxJogado) {
                heroiMaisJogado = entry.getKey();
                maxJogado = entry.getValue();
            }
        }

        return heroiMaisJogado;
    }

    private static String monstroMaisEnfrentado(ArrayList<RegistroBatalha> registros) {
        Map<String, Integer> contadorMonstros = new HashMap<>();

        for (RegistroBatalha registro : registros) {
            String monstro = registro.getMonstro();
            contadorMonstros.put(monstro, contadorMonstros.getOrDefault(monstro, 0) + 1);
        }

        String monstroMaisEnfrentado = "";
        int maxEnfrentado = 0;

        for (Map.Entry<String, Integer> entry : contadorMonstros.entrySet()) {
            if (entry.getValue() > maxEnfrentado) {
                monstroMaisEnfrentado = entry.getKey();
                maxEnfrentado = entry.getValue();
            }
        }

        return monstroMaisEnfrentado;
    }

    private static int calcularPontuacaoTotal(ArrayList<RegistroBatalha> registros) {
        int pontuacaoTotal = 0;

        for (RegistroBatalha registro : registros) {
            pontuacaoTotal += 100 - registro.getRodadas();
        }

        return pontuacaoTotal;
    }
}

class RegistroBatalha {
    private String data;
    private String heroi;
    private String resultado;
    private String monstro;
    private int rodadas;

    public RegistroBatalha(String data, String heroi, String resultado, String monstro, int rodadas) {
        this.data = data;
        this.heroi = heroi;
        this.resultado = resultado;
        this.monstro = monstro;
        this.rodadas = rodadas;
    }

    public String getData() {
        return data;
    }

    public String getHeroi() {
        return heroi;
    }

    public String getResultado() {
        return resultado;
    }

    public String getMonstro() {
        return monstro;
    }

    public int getRodadas() {
        return rodadas;
    }
}
