package borsanova;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Allocazione {

  private final String nomeAzienda;
  private final String nomeBorsa;
  private final Map<String, Integer> allocazioni;

  public Allocazione(String nomeAzienda, String nomeBorsa) {
    this.nomeAzienda = Objects.requireNonNull(nomeAzienda, "Nome azienda non può essere nullo.");
    this.nomeBorsa = Objects.requireNonNull(nomeBorsa, "Nome borsa non può essere nullo.");
    this.allocazioni = new HashMap<>();
  }

  public String getNomeAzienda() {
    return nomeAzienda;
  }

  public String getNomeBorsa() {
    return nomeBorsa;
  }

  public Map<String, Integer> getAllocazioni() {
    return new HashMap<>(allocazioni);
  }

  public void aggiungiAllocazione(String nomeOperatore, int numeroAzioni) {
    allocazioni.merge(nomeOperatore, numeroAzioni, Integer::sum);
  }

  public void rimuoviAllocazione(String nomeOperatore, int numeroAzioni) {
    allocazioni.merge(nomeOperatore, -numeroAzioni, Integer::sum);
    if (allocazioni.get(nomeOperatore) <= 0) {
      allocazioni.remove(nomeOperatore);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Allocazione other)) return false;
    return nomeAzienda.equals(other.nomeAzienda) && nomeBorsa.equals(other.nomeBorsa);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nomeAzienda, nomeBorsa);
  }
}