package org.qadev.atm.strategy;

import org.qadev.atm.cassette.Cassette;
import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Стратегия выдачи: начинает с максимального номинала и идёт к минимальному.
 */
public class GreedyWithdrawStrategy implements WithdrawStrategy {

    @Override
    public List<Banknote> calculateWithdrawal(List<Cassette> availableCassettes, int amount) {
        if (amount <= 0) {
            return Collections.emptyList();
        }

        // Собираем доступные номиналы в порядке убывания
        List<Cassette> sortedCassettes = availableCassettes.stream()
            .filter(c -> c.getCount() > 0)
            .sorted((c1, c2) ->
                    Integer.compare(c2.getDenomination().getValue(), c1.getDenomination().getValue()))
            .toList();

        // Сначала проверяем, можно ли вообще набрать сумму
        int totalAvailable = sortedCassettes.stream()
            .mapToInt(Cassette::getTotalValue)
            .sum();

        if (totalAvailable < amount) {
            return Collections.emptyList();
        }

        // Алгоритм: от большего номинала к меньшему
        int remaining = amount;
        List<Banknote> result = new ArrayList<>();

        for (Cassette cassette : sortedCassettes) {
            if (remaining <= 0) {
                break;
            }

            int denominationValue = cassette.getDenomination().getValue();
            int maxAvailable = cassette.getCount();

            // Сколько банкнот этого номинала можно использовать
            int needed = remaining / denominationValue;
            int toUse = Math.min(needed, maxAvailable);

            for (int i = 0; i < toUse; i++) {
                Banknote banknote = new Banknote(cassette.getDenomination());
                result.add(banknote);
            }
            remaining -= toUse * denominationValue;
        }

        // Если осталось что-то, что нельзя набрать — возвращаем пустой список
        if (remaining > 0) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(result);
    }
}
