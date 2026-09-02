public class Filme {

    private int id;
    private String nomeFilme;

    // Construtor vazio
    public Filme() {}

    // Construtor completo
    public Filme(int id, String nomeFilme) {
        this.id = id;
        this.nomeFilme = nomeFilme;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeFilme() { return nomeFilme; }
    public void setNomeFilme(String nomeFilme) { this.nomeFilme = nomeFilme; }



}

