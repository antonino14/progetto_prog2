package borsanova;

import java.util.*;

public class Borsa {
    private final String nome;
    private final Map<String, Azione> azioni = new HashMap<>();
    private PoliticaPrezzo politicaPrezzo;

    public Borsa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Map<String, Azione> getAzioni() {
        return new HashMap<>(azioni);
    }

    public Azione getAzione(String nomeAzienda) {
        return azioni.get(nomeAzienda);
    }

    public void quotaAzione(Azione azione) {
        azioni.put(azione.getNomeAzienda(), azione);
    }

    public void aggiornaPrezzo(String nomeAzienda, boolean compra) {
        Azione azione = azioni.get(nomeAzienda);
        if (azione != null && politicaPrezzo != null) {
            int nuovoPrezzo = politicaPrezzo.calcolaPrezzo(azione.getPrezzoUnitario(), compra);
            azione.setPrezzoUnitario(nuovoPrezzo);
        }
    }

    public void setPoliticaPrezzo(PoliticaPrezzo politicaPrezzo) {
        this.politicaPrezzo = politicaPrezzo;
    }
}