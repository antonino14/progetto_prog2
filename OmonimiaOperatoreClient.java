package clients;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/** Client di test per alcune funzionalità relative alle <strong>aziende</strong>. */
public class OmonimiaOperatoreClient {

  /** . */
  private OmonimiaOperatoreClient() {}

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Set<String> operatori = new TreeSet<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (!line.isBlank()) {
        operatori.add(line);
      }
    }

    for (String operatore : operatori) {
      System.out.println(operatore);
    }
  }
}