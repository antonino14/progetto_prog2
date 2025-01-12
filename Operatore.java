package borsanova;

import java.util.*;

public class Operatore {
    private String nome;
    private int budget;
    private Map<String, Map<String, Integer>> azioniPerBorsa;

    public Operatore(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        this.nome = nome;
        this.budget = 0;
        this.azioniPerBorsa = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public int getBudget() {
        return budget;
    }

    public void deposita(int valore) {
        if (valore <= 0) {
            throw new IllegalArgumentException("Il deposito deve essere un numero positivo");
        }
        budget += valore;
    }

    public void preleva(int valore) {
        if (valore <= 0) {
            throw new IllegalArgumentException("Il prelievo deve essere un numero positivo");
        }
        if (valore > budget) {
            throw new IllegalArgumentException("Fondi insufficienti per il prelievo");
        }
        budget -= valore;
    }

    public void compraAzione(Borsa borsa, String nomeAzienda, int prezzoTotale) {
        Azione azione = borsa.getAzione(nomeAzienda);
        if (azione == null) {
            throw new IllegalArgumentException("Azione non trovata: " + nomeAzienda);
        }
        if (prezzoTotale <= 0) {
            throw new IllegalArgumentException("Il prezzo totale deve essere positivo");
        }
        int prezzoUnitario = azione.getPrezzoUnitario();
        int numeroAcquistabili = prezzoTotale / prezzoUnitario;
        if (numeroAcquistabili <= 0 || prezzoTotale > budget) {
            throw new IllegalArgumentException("Budget insufficiente o importo non valido");
        }
        budget -= numeroAcquistabili * prezzoUnitario;
        azione.decrementa(numeroAcquistabili);
        azioniPerBorsa.computeIfAbsent(borsa.getNome(), k -> new HashMap<>())
                     .merge(nomeAzienda, numeroAcquistabili, Integer::sum);
    }

    public void vendiAzione(Borsa borsa, String nomeAzienda, int numeroAzioni) {
        if (numeroAzioni <= 0) {
            throw new IllegalArgumentException("Il numero di azioni deve essere positivo");
        }
        Map<String, Integer> azioni = azioniPerBorsa.get(borsa.getNome());
        if (azioni == null || !azioni.containsKey(nomeAzienda) || azioni.get(nomeAzienda) < numeroAzioni) {
            throw new IllegalArgumentException("Numero di azioni insufficiente o inesistente");
        }
        int prezzoUnitario = borsa.getAzione(nomeAzienda).getPrezzoUnitario();
        azioni.put(nomeAzienda, azioni.get(nomeAzienda) - numeroAzioni);
        if (azioni.get(nomeAzienda) == 0) {
            azioni.remove(nomeAzienda);
        }
        budget += numeroAzioni * prezzoUnitario;
        borsa.getAzione(nomeAzienda).incrementa(numeroAzioni);
    }

    public int calcolaValoreAzioni(Map<String, Borsa> borse) {
        int valoreTotale = 0;
        for (Map.Entry<String, Map<String, Integer>> entry : azioniPerBorsa.entrySet()) {
            String nomeBorsa = entry.getKey();
            Map<String, Integer> azioni = entry.getValue();
            Borsa borsa = borse.get(nomeBorsa);
            if (borsa == null) continue;
            for (Map.Entry<String, Integer> azioneEntry : azioni.entrySet()) {
                Azione azione = borsa.getAzione(azioneEntry.getKey());
                if (azione != null) {
                    valoreTotale += azione.getPrezzoUnitario() * azioneEntry.getValue();
                }
            }
        }
        return valoreTotale;
    }

    public Map<String, Map<String, Integer>> getAzioniPerBorsa() {
        return new HashMap<>(azioniPerBorsa);
    }

    public String generaOutput(Map<String, Borsa> borse) {
        StringBuilder output = new StringBuilder();
        output.append(nome).append(", ").append(budget).append(", ").append(calcolaValoreAzioni(borse));

        List<String> borseOrdinate = new ArrayList<>(azioniPerBorsa.keySet());
        Collections.sort(borseOrdinate);

        for (String nomeBorsa : borseOrdinate) {
            Map<String, Integer> azioni = azioniPerBorsa.get(nomeBorsa);
            List<String> aziendeOrdinate = new ArrayList<>(azioni.keySet());
            Collections.sort(aziendeOrdinate);

            for (String nomeAzienda : aziendeOrdinate) {
                int numeroAzioni = azioni.get(nomeAzienda);
                output.append(System.lineSeparator()).append("- ").append(nomeBorsa).append(", ").append(nomeAzienda).append(", ").append(numeroAzioni);
            }
        }

        return output.toString();
    }
}