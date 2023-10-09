import java.util.Random;

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