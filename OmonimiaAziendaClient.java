package clients;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/** Client di test per alcune funzionalità relative alle <strong>aziende</strong>. */
public class OmonimiaAziendaClient {

  /** . */
  private OmonimiaAziendaClient() {}

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Set<String> aziende = new TreeSet<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (!line.isBlank()) {
        aziende.add(line);
      }
    }

    for (String azienda : aziende) {
      System.out.println(azienda);
    }
  }
}