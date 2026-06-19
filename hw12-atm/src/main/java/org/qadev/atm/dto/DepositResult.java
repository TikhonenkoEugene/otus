package org.qadev.atm.dto;

import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Результат операции внесения наличных.
 */
public class DepositResult {
    private final List<Banknote> acceptedBanknotes;
    private final Map<Denomination, Integer> denominationBreakdown;

    public DepositResult(List<Banknote> acceptedBanknotes) {
        this.acceptedBanknotes = Collections.unmodifiableList(acceptedBanknotes);
        this.denominationBreakdown = acceptedBanknotes.stream()
            .collect(Collectors.groupingBy(
                Banknote::getDenomination,
                Collectors.summingInt(b -> 1)
            ));
    }

    public List<Banknote> getAcceptedBanknotes() {
        return acceptedBanknotes;
    }

    public Map<Denomination, Integer> getDenominationBreakdown() {
        return denominationBreakdown;
    }

    public int getTotalAmount() {
        return acceptedBanknotes.stream().mapToInt(Banknote::getValue).sum();
    }

    @Override
    public String toString() {
        return "Результат депозита: Всего " + getTotalAmount() + " рублей, количество банкнот каждого номинала: " +
                getDenominationBreakdown();
    }
}
