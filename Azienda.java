package borsanova;

import java.util.HashMap;
import java.util.Map;

public class Azienda {
    private final String nome;
    private final Map<String, Azione> azioni;

    public Azienda(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome dell'azienda non può essere vuoto.");
        }
        this.nome = nome;
        this.azioni = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public void quotaAzione(String nomeBorsa, int numero, int prezzoUnitario) {
        if (azioni.containsKey(nomeBorsa)) {
            throw new IllegalArgumentException("Azione già quotata in questa borsa.");
        }
        Azione azione = new Azione(nome, nomeBorsa, numero, prezzoUnitario);
        azioni.put(nomeBorsa, azione);
    }

    public Map<String, Azione> getAzioni() {
        return azioni;
    }
}