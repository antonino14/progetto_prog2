package clients;

import borsanova.Azione;
import borsanova.Borsa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/** Client di test per alcune funzionalità relative alle <strong>azioni</strong>. */
public class AzioneClient {

  /** . */
  private AzioneClient() {}

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: java AzioneClient <nome_borsa>");
      System.exit(1);
    }

    String nomeBorsa = args[0];
    Borsa borsa = new Borsa(nomeBorsa);
    Scanner scanner = new Scanner(System.in);
    List<Azione> azioni = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.isBlank()) {
        break;
      }
      
      String[] parts = line.split(" ");
      if (parts.length != 3) {
        System.err.println("Invalid input format. Expected: nome_azienda numero prezzo_unitario");
        System.exit(1);
      }

      String nomeAzienda = parts[0];
      int numero;
      int prezzoUnitario;

      try {
        numero = Integer.parseInt(parts[1]);
        prezzoUnitario = Integer.parseInt(parts[2]);
      } catch (NumberFormatException e) {
        System.err.println("Invalid number format.");
        System.exit(1);
        return; // to satisfy compiler
      }

      Azione azione = new Azione(nomeAzienda, nomeBorsa, numero, prezzoUnitario);
      azioni.add(azione);
      borsa.quotaAzione(azione);
    }

    Collections.sort(azioni, (a1, a2) -> a1.getNomeAzienda().compareTo(a2.getNomeAzienda()));

    for (Azione azione : azioni) {
      System.out.println(azione.getNomeAzienda() + ", " + azione.getPrezzoUnitario() + ", " + azione.getNumero());
    }
  }
}