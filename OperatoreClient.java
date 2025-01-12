package clients;

import borsanova.*;
import java.util.*;

public class OperatoreClient {

    private OperatoreClient() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Borsa> borse = new HashMap<>();
        Map<String, Operatore> operatori = new HashMap<>();

        // Primo Blocco: nome_azienda nome_borsa numero prezzo_unitario
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("--")) break;

            String[] parts = line.split(" ");
            if (parts.length != 4) {
                System.err.println("Formato non valido per il primo blocco.");
                continue;
            }
            String nomeAzienda = parts[0];
            String nomeBorsa = parts[1];
            int numero = Integer.parseInt(parts[2]);
            int prezzoUnitario = Integer.parseInt(parts[3]);

            Borsa borsa = borse.computeIfAbsent(nomeBorsa, k -> new Borsa(k));
            Azione azione = new Azione(nomeAzienda, nomeBorsa, numero, prezzoUnitario);
            borsa.quotaAzione(azione);
        }

        // Secondo Blocco: nome_operatore budget_iniziale
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("--")) break;

            String[] parts = line.split(" ");
            if (parts.length != 2) {
                System.err.println("Formato non valido per il secondo blocco.");
                continue;
            }
            String nomeOperatore = parts[0];
            int budgetIniziale = Integer.parseInt(parts[1]);

            Operatore operatore = new Operatore(nomeOperatore);
            operatore.deposita(budgetIniziale);
            operatori.put(nomeOperatore, operatore);
        }

        // Terzo Blocco: Operazioni
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            if (parts.length < 3) {
                System.err.println("Formato non valido per il terzo blocco.");
                continue;
            }
            String nomeOperatore = parts[0];
            String operazione = parts[1];
            Operatore operatore = operatori.get(nomeOperatore);

            if (operatore == null) {
                System.err.println("Operatore non trovato: " + nomeOperatore);
                continue;
            }

            try {
                switch (operazione) {
                    case "b":
                        if (parts.length != 5) {
                            System.err.println("Formato non valido per l'operazione di acquisto.");
                            continue;
                        }
                        String nomeBorsa = parts[2];
                        String nomeAzienda = parts[3];
                        int prezzoTotale = Integer.parseInt(parts[4]);
                        Borsa borsa = borse.get(nomeBorsa);
                        if (borsa != null) {
                            operatore.compraAzione(borsa, nomeAzienda, prezzoTotale);
                            borsa.aggiornaPrezzo(nomeAzienda, true);
                        } else {
                            System.err.println("Borsa non trovata: " + nomeBorsa);
                        }
                        break;
                    case "s":
                        if (parts.length != 5) {
                            System.err.println("Formato non valido per l'operazione di vendita.");
                            continue;
                        }
                        nomeBorsa = parts[2];
                        nomeAzienda = parts[3];
                        int numeroAzioni = Integer.parseInt(parts[4]);
                        borsa = borse.get(nomeBorsa);
                        if (borsa != null) {
                            operatore.vendiAzione(borsa, nomeAzienda, numeroAzioni);
                            borsa.aggiornaPrezzo(nomeAzienda, false);
                        } else {
                            System.err.println("Borsa non trovata: " + nomeBorsa);
                        }
                        break;
                    case "d":
                        if (parts.length != 3) {
                            System.err.println("Formato non valido per l'operazione di deposito.");
                            continue;
                        }
                        int valore = Integer.parseInt(parts[2]);
                        operatore.deposita(valore);
                        break;
                    case "w":
                        if (parts.length != 3) {
                            System.err.println("Formato non valido per l'operazione di prelievo.");
                            continue;
                        }
                        valore = Integer.parseInt(parts[2]);
                        operatore.preleva(valore);
                        break;
                    default:
                        System.err.println("Operazione sconosciuta: " + operazione);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Errore: " + e.getMessage());
            }
        }

        // Stampa l'elenco degli operatori
        List<Operatore> elencoOperatori = new ArrayList<>(operatori.values());
        elencoOperatori.sort(Comparator.comparing(Operatore::getNome));

        for (Operatore operatore : elencoOperatori) {
            System.out.println(operatore.generaOutput(borse));
        }
    }
}