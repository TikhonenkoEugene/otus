package org.qadev.atm.cassette;

import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;

import java.util.List;

/**
 * Интерфейс кассеты — хранилища для банкнот одного номинала.
 */
public interface Cassette {

    /**
     * Возвращает номинал, с которым работает эта кассета.
     */
    Denomination getDenomination();

    /**
     * Максимальное количество банкнот в кассете.
     */
    int getMaxCapacity();

    /**
     * Текущее количество банкнот в кассете.
     */
    int getCount();

    /**
     * Общая сумма средств в кассете.
     */
    int getTotalValue();

    /**
     * Попытка добавить банкноту в кассету.
     * @throws IllegalStateException если кассета переполнена
     */
    void deposit(Banknote banknote);

    /**
     * Попытка извлечь указанное количество банкнот из кассеты.
     * @throws IllegalStateException если недостаточно банкнот
     */
    List<Banknote> withdraw(int count);

    /**
     * Проверяет, можно ли добавить банкноту (не переполнена ли).
     */
    boolean canDeposit();

    /**
     * Проверяет, достаточно ли банкнот для выдачи.
     */
    boolean canWithdraw(int count);
}
