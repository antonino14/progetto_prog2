package clients;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/** Client di test per alcune funzionalità relative alle <strong>aziende</strong>. */
public class OmonimiaBorsaClient {

  /** . */
  private OmonimiaBorsaClient() {}

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Set<String> borse = new TreeSet<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (!line.isBlank()) {
        borse.add(line);
      }
    }

    for (String borsa : borse) {
      System.out.println(borsa);
    }
  }
}