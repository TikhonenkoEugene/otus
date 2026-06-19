package org.qadev.atm.dto;

import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Результат операции выдачи наличных.
 */
public class WithdrawResult {
    private final List<Banknote> dispensedBanknotes;
    private final Map<Denomination, Integer> denominationBreakdown;

    public WithdrawResult(List<Banknote> dispensedBanknotes) {
        this.dispensedBanknotes = Collections.unmodifiableList(dispensedBanknotes);
        this.denominationBreakdown = dispensedBanknotes.stream()
            .collect(Collectors.groupingBy(
                Banknote::getDenomination,
                Collectors.summingInt(b -> 1)
            ));
    }

    public List<Banknote> getDispensedBanknotes() {
        return dispensedBanknotes;
    }

    public Map<Denomination, Integer> getDenominationBreakdown() {
        return denominationBreakdown;
    }

    public int getTotalAmount() {
        return dispensedBanknotes.stream().mapToInt(Banknote::getValue).sum();
    }

    public boolean isEmpty() {
        return dispensedBanknotes.isEmpty();
    }

    @Override
    public String toString() {
        return "Результат вывода: Всего " + getTotalAmount() + " рублей, количество банкнот каждого номинала: " +
                getDenominationBreakdown();
    }
}
