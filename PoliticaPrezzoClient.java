package clients;

import borsanova.*;
import java.util.*;

public class PoliticaPrezzoClient {

    private PoliticaPrezzoClient() {}

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java PoliticaPrezzoClient <nome_borsa> <valore> <budget_iniziale>");
            System.exit(1);
        }

        String nomeBorsa = args[0];
        int valore = Integer.parseInt(args[1]);
        int budgetIniziale = Integer.parseInt(args[2]);

        // Determina la politica di prezzo in base al valore
        PoliticaPrezzo politicaPrezzo;
        if (valore > 0) {
            politicaPrezzo = new PoliticaPrezzo.IncrementoCostante(valore);
        } else {
            politicaPrezzo = new PoliticaPrezzo.DecrementoCostante(valore);
        }

        // Crea la borsa e imposta la politica di prezzo
        Borsa borsa = new Borsa(nomeBorsa);
        borsa.setPoliticaPrezzo(politicaPrezzo);

        Scanner scanner = new Scanner(System.in);

        // Primo blocco: nome_azienda numero prezzo_unitario
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("--")) break;
            String[] parts = line.split(" ");
            if (parts.length != 3) {
                System.err.println("Invalid input format for first block. Expected: nome_azienda numero prezzo_unitario");
                System.exit(1);
            }
            String nomeAzienda = parts[0];
            int numero = Integer.parseInt(parts[1]);
            int prezzoUnitario = Integer.parseInt(parts[2]);

            Azione azione = new Azione(nomeAzienda, nomeBorsa, numero, prezzoUnitario);
            borsa.quotaAzione(azione);
        }

        // Crea l'operatore
        Operatore operatore = new Operatore("Operatore");
        operatore.deposita(budgetIniziale);

        // Secondo blocco: b nome_azienda prezzo_totale [oppure] s nome_azienda numero_azioni
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(" ");
            if (parts.length < 3) {
                System.err.println("Invalid input format for second block.");
                System.exit(1);
            }
            String operazione = parts[0];
            String nomeAzienda = parts[1];

            switch (operazione) {
                case "b":
                    if (parts.length != 3) {
                        System.err.println("Invalid input format for buy operation. Expected: b nome_azienda prezzo_totale");
                        System.exit(1);
                    }
                    int prezzoTotale = Integer.parseInt(parts[2]);
                    operatore.compraAzione(borsa, nomeAzienda, prezzoTotale);
                    borsa.aggiornaPrezzo(nomeAzienda, true); // Aggiorna il prezzo dopo l'acquisto
                    break;
                case "s":
                    if (parts.length != 3) {
                        System.err.println("Invalid input format for sell operation. Expected: s nome_azienda numero_azioni");
                        System.exit(1);
                    }
                    int numeroAzioni = Integer.parseInt(parts[2]);
                    operatore.vendiAzione(borsa, nomeAzienda, numeroAzioni);
                    borsa.aggiornaPrezzo(nomeAzienda, false); // Aggiorna il prezzo dopo la vendita
                    break;
                default:
                    System.err.println("Unknown operation: " + operazione);
                    System.exit(1);
            }
        }

        // Stampa l'elenco delle azioni in ordine alfabetico seguito dal prezzo
        List<Azione> azioni = new ArrayList<>(borsa.getAzioni().values());
        azioni.sort(Comparator.comparing(Azione::getNomeAzienda));

        for (Azione azione : azioni) {
            System.out.println(azione.getNomeAzienda() + ", " + azione.getPrezzoUnitario());
        }
    }
}