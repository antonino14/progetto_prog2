/*

Copyright 2024 Massimo Santini

This file is part of "Programmazione 2 @ UniMI" teaching material.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This material is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this file.  If not, see <https://www.gnu.org/licenses/>.

*/

package clients;

import borsanova.Azione;
import borsanova.Borsa;
import java.util.*;

/** Client di test per alcune funzionalità relative alle <strong>quotazioni</strong>. */
public class QuotazioneClient {

  /** . */
  private QuotazioneClient() {}

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Map<String, Borsa> borse = new HashMap<>();
    Map<String, List<String>> aziendeInBorse = new HashMap<>();

    // Legge le linee di input e quota le aziende nelle borse
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.isEmpty()) continue;
      String[] parts = line.split(" ");
      if (parts.length != 4) {
        System.err.println("Invalid input format. Expected: nome_azienda nome_borsa quantità prezzo");
        continue;
      }
      String nomeAzienda = parts[0];
      String nomeBorsa = parts[1];
      int quantita = Integer.parseInt(parts[2]);
      int prezzo = Integer.parseInt(parts[3]);

      Borsa borsa = borse.computeIfAbsent(nomeBorsa, Borsa::new);
      Azione azione = new Azione(nomeAzienda, nomeBorsa, quantita, prezzo);
      borsa.quotaAzione(azione);

      aziendeInBorse.computeIfAbsent(nomeAzienda, k -> new ArrayList<>()).add(nomeBorsa);
    }

    // Stampa l'elenco delle borse per ciascuna azienda
    List<String> nomiAziende = new ArrayList<>(aziendeInBorse.keySet());
    Collections.sort(nomiAziende);

    for (String nomeAzienda : nomiAziende) {
      System.out.println(nomeAzienda);
      List<String> borseAzienda = aziendeInBorse.get(nomeAzienda);
      Collections.sort(borseAzienda);

      for (String nomeBorsa : borseAzienda) {
        System.out.println("- " + nomeBorsa);
      }
    }

    // Stampa l'elenco delle aziende per ciascuna borsa
    List<String> nomiBorse = new ArrayList<>(borse.keySet());
    Collections.sort(nomiBorse);

    for (String nomeBorsa : nomiBorse) {
      System.out.println(nomeBorsa);
      Borsa borsa = borse.get(nomeBorsa);
      List<Azione> azioni = new ArrayList<>(borsa.getAzioni().values());
      Collections.sort(azioni, Comparator.comparing(Azione::getNomeAzienda));

      for (Azione azione : azioni) {
        System.out.println("- " + azione.getNomeAzienda());
      }
    }
  }
}