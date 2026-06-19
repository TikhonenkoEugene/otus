package org.qadev.atm.strategy;

import org.qadev.atm.cassette.Cassette;
import org.qadev.atm.domain.Banknote;

import java.util.List;

/**
 * Стратегия расчета выдачи банкнот при снятии средств.
 * Позволяет изменять алгоритм выдачи без модификации ATM (Open-Closed Principle).
 */
public interface WithdrawStrategy {

    /**
     * Рассчитывает, какие банкноты выдать для заданной суммы.
     *
     * @param availableCassettes доступные кассеты (не должна модифицироваться)
     * @param amount             запрашиваемая сумма
     * @return список банкнот для выдачи или пустой список, если сумму невозможно набрать
     */
    List<Banknote> calculateWithdrawal(List<Cassette> availableCassettes, int amount);
}
