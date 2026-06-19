package org.qadev.atm.cassette;

import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DenominationCassette implements Cassette {
    private final Denomination denomination;
    private final int maxCapacity;
    private int count;

    public DenominationCassette(Denomination denomination, int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException(
                    "ERR: Максимальный объем кассеты должен быть больше нуля"
            );
        }
        this.denomination = denomination;
        this.maxCapacity = maxCapacity;
        this.count = 0;
    }

    @Override
    public Denomination getDenomination() {
        return denomination;
    }

    @Override
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getTotalValue() {
        return count * denomination.getValue();
    }

    @Override
    public void deposit(Banknote banknote) {
        if (banknote.getDenomination() != this.denomination) {
            throw new IllegalArgumentException(
                    "ERR: Кассета не поддерживает банкноты номиналом "
                            + banknote.getDenomination().getValue() + " рублей"
            );
        }
        if (!canDeposit()) {
            throw new IllegalStateException(
                    "ERR: Кассета переполнена и не может больше принять банкноты "
                            + count + "/" + maxCapacity
            );
        }
        count++;
    }

    @Override
    public List<Banknote> withdraw(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count must be non-negative");
        }
        if (!canWithdraw(count)) {
            throw new IllegalStateException(
                "Insufficient banknotes in cassette: requested=" + count + ", available=" + this.count
            );
        }
        this.count -= count;
        List<Banknote> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(new Banknote(this.denomination));
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public boolean canDeposit() {
        return this.count < maxCapacity;
    }

    @Override
    public boolean canWithdraw(int count) {
        return this.count >= count;
    }

    @Override
    public String toString() {
        return "Кассета с банкнотами номиналом " + denomination.getValue()
            + " рублей: " + count + "/" + maxCapacity + "}";
    }
}
