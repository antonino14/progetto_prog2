package borsanova;

public abstract class PoliticaPrezzo {
    public abstract int calcolaPrezzo(int prezzoCorrente, boolean compra);

    public static class IncrementoCostante extends PoliticaPrezzo {
        private final int incremento;

        public IncrementoCostante(int incremento) {
            this.incremento = incremento;
        }

        @Override
        public int calcolaPrezzo(int prezzoCorrente, boolean compra) {
            if (compra) {
                return prezzoCorrente + incremento;
            } else {
                return prezzoCorrente; // Nessun cambiamento per la vendita
            }
        }
    }

    public static class DecrementoCostante extends PoliticaPrezzo {
        private final int decremento;

        public DecrementoCostante(int decremento) {
            this.decremento = Math.abs(decremento);
        }

        @Override
        public int calcolaPrezzo(int prezzoCorrente, boolean compra) {
            if (compra) {
                return prezzoCorrente; // Nessun cambiamento per l'acquisto
            } else {
                int nuovoPrezzo = prezzoCorrente - decremento;
                return Math.max(nuovoPrezzo, 1); // Il prezzo non può scendere sotto 1
            }
        }
    }
}