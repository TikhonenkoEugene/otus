package org.qadev.atm;

import org.qadev.atm.cassette.Cassette;
import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;
import org.qadev.atm.dto.DepositResult;
import org.qadev.atm.dto.WithdrawResult;
import org.qadev.atm.exception.AmountCannotBeDispensedException;
import org.qadev.atm.exception.InsufficientFundsException;
import org.qadev.atm.exception.UnsupportedDenominationException;
import org.qadev.atm.strategy.WithdrawStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер высокого уровня банкомата.
 * Управляет набором кассет и координирует процессы приема, выдачи и аудита средств.
 */
public class Atm {
    private final Map<Denomination, Cassette> cassettes;
    private final WithdrawStrategy withdrawStrategy;

    public Atm(Map<Denomination, Cassette> cassettes, WithdrawStrategy withdrawStrategy) {
        if (cassettes == null) {
            throw new IllegalArgumentException("Cassettes map cannot be null");
        }
        if (withdrawStrategy == null) {
            throw new IllegalArgumentException("Withdraw strategy cannot be null");
        }
        this.cassettes = new HashMap<>(cassettes);
        this.withdrawStrategy = withdrawStrategy;
    }

    /**
     * Прием наличных
     */
    public DepositResult deposit(List<Banknote> banknotes) {
        if (banknotes == null || banknotes.isEmpty()) {
            return new DepositResult(Collections.emptyList());
        }

        // Первая фаза: валидация
        for (Banknote banknote : banknotes) {
            Denomination denom = banknote.getDenomination();
            if (!cassettes.containsKey(denom)) {
                throw new UnsupportedDenominationException(denom);
            }
        }

        // Вторая фаза: проверка вместимости
        Map<Denomination, Integer> denomCount = new HashMap<>();
        for (Banknote banknote : banknotes) {
            Denomination denom = banknote.getDenomination();
            denomCount.put(denom, denomCount.getOrDefault(denom, 0) + 1);
        }

        for (Map.Entry<Denomination, Integer> entry : denomCount.entrySet()) {
            Cassette cassette = cassettes.get(entry.getKey());
            int needed = entry.getValue();
            int available = cassette.getMaxCapacity() - cassette.getCount();
            if (needed > available) {
                throw new IllegalStateException("ERR: Кассета переполнена номиналом " + entry.getKey().getValue()
                    + " руб, необходимо: " + needed + ", доступно: " + available);
            }
        }

        // Третья фаза: фактическое добавление банкнот
        List<Banknote> accepted = new ArrayList<>();
        for (Banknote banknote : banknotes) {
            cassettes.get(banknote.getDenomination()).deposit(banknote);
            accepted.add(banknote);
        }

        return new DepositResult(accepted);
    }

    /**
     * Выдача наличных
     */
    public WithdrawResult withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("ERR: Сумма должна быть положительной");
        }

        int balance = getBalance();
        if (balance < amount) {
            throw new InsufficientFundsException(amount, balance);
        }

        // Используем стратегию для расчета банкнот
        List<Cassette> availableCassettes = new ArrayList<>(cassettes.values());
        List<Banknote> toDispense = withdrawStrategy.calculateWithdrawal(availableCassettes, amount);

        if (toDispense.isEmpty()) {
            throw new AmountCannotBeDispensedException(amount);
        }

        // Фактически извлекаем банкноты из кассет (по номиналам)
        Map<Denomination, Integer> denomCount = new HashMap<>();
        for (Banknote banknote : toDispense) {
            Denomination denom = banknote.getDenomination();
            denomCount.put(denom, denomCount.getOrDefault(denom, 0) + 1);
        }

        for (Map.Entry<Denomination, Integer> entry : denomCount.entrySet()) {
            cassettes.get(entry.getKey()).withdraw(entry.getValue());
        }

        return new WithdrawResult(toDispense);
    }

    /**
     * Получить общий баланс (одним числом).
     */
    public int getBalance() {
        return cassettes.values().stream()
            .mapToInt(Cassette::getTotalValue)
            .sum();
    }

    /**
     * Получить детализированный баланс по каждой кассете.
     */
    public Map<Denomination, Integer> getDetailedBalance() {
        Map<Denomination, Integer> detailed = new HashMap<>();
        for (Map.Entry<Denomination, Cassette> entry : cassettes.entrySet()) {
            detailed.put(entry.getKey(), entry.getValue().getCount());
        }
        return Collections.unmodifiableMap(detailed);
    }

    /**
     * Получить количество банкнот в кассете по номиналу.
     */
    public int getCassetteCount(Denomination denomination) {
        if (!cassettes.containsKey(denomination)) {
            throw new IllegalArgumentException("ERR: Не поддерживаемый номинал " + denomination);
        }
        return cassettes.get(denomination).getCount();
    }

    /**
     * Получить текущий остаток в кассете по номиналу.
     */
    public int getCassetteBalance(Denomination denomination) {
        if (!cassettes.containsKey(denomination)) {
            throw new IllegalArgumentException("ERR: Не поддерживаемый номинал " + denomination);
        }
        return cassettes.get(denomination).getTotalValue();
    }
}
