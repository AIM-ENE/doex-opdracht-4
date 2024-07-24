package aimene.doex.bestelling.model;

import jakarta.validation.constraints.NotNull;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public record Geld(int aantalCenten,
                   @NotNull Valuta valuta) {

    public Geld plus(Geld geld) {
        if (!isValutaGelijk(geld)) {
            throw new IllegalArgumentException("Valuta is niet gelijk");
        }

        return new Geld(aantalCenten() + geld.aantalCenten(), geld.valuta());
    }

    public Geld min(Geld geld) {
        if (!isValutaGelijk(geld)) {
            throw new IllegalArgumentException("Valuta is niet gelijk");
        }

        return new Geld(aantalCenten() - geld.aantalCenten(), geld.valuta());
    }

    public Geld keer(int factor) {
        return new Geld(aantalCenten() * factor, valuta());
    }

    public double getBedrag() {
        return aantalCenten / 100.0;
    }

    private boolean isValutaGelijk(Geld geld) {
        return valuta == geld.valuta();
    }

    @Override
    public String toString() {
        return "Geld{" +
                "aantalCenten=" + aantalCenten +
                ", valuta=" + valuta +
                '}';
    }
}
