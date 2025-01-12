package borsanova;

public class Azione {
    private final String nomeAzienda;
    private final String nomeBorsa;
    private int prezzoUnitario;
    private int numero;

    public Azione(String nomeAzienda, String nomeBorsa, int numero, int prezzoUnitario) {
        this.nomeAzienda = nomeAzienda;
        this.nomeBorsa = nomeBorsa;
        this.numero = numero;
        this.prezzoUnitario = prezzoUnitario;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }

    public String getNomeBorsa() {
        return nomeBorsa;
    }

    public int getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(int prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public int getNumero() {
        return numero;
    }

    public void decrementa(int quantita) {
        if (quantita <= numero) {
            numero -= quantita;
        }
    }

    public void incrementa(int quantita) {
        numero += quantita;
    }

    @Override
    public String toString() {
        return nomeAzienda + ", " + numero + ", " + prezzoUnitario;
    }
}