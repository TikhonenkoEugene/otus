package org.qadev.atm.domain;

/**
 * Класс банкноты с фиксированным номиналом
 */
public final class Banknote {
    private final Denomination denomination;
    public Banknote(Denomination denomination) {
        this.denomination = denomination;
    }
    public Denomination getDenomination() {
        return denomination;
    }
    public int getValue() {
        return denomination.getValue();
    }

    @Override
    public String toString() {
        return "Банкнота номиналом " + denomination.getValue() + " рублей.";
    }
}
