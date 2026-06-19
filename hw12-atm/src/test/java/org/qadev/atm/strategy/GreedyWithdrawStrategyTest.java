package org.qadev.atm.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.qadev.atm.cassette.Cassette;
import org.qadev.atm.cassette.DenominationCassette;
import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты на алгоритм выдачи")
class GreedyWithdrawStrategyTest {
    private WithdrawStrategy strategy;
    private List<Cassette> cassettes;

    @BeforeEach
    void setUp() {
        strategy = new GreedyWithdrawStrategy();
        cassettes = new ArrayList<>();
        cassettes.add(new DenominationCassette(Denomination.ONE_HUNDRED, 100));
        cassettes.add(new DenominationCassette(Denomination.FIVE_HUNDRED, 100));
        cassettes.add(new DenominationCassette(Denomination.ONE_THOUSAND, 100));
        cassettes.add(new DenominationCassette(Denomination.FIVE_THOUSAND, 100));

        for (int i = 0; i < 10; i++) {
            cassettes.get(0).deposit(new Banknote(Denomination.ONE_HUNDRED));
            cassettes.get(1).deposit(new Banknote(Denomination.FIVE_HUNDRED));
            cassettes.get(2).deposit(new Banknote(Denomination.ONE_THOUSAND));
            cassettes.get(3).deposit(new Banknote(Denomination.FIVE_THOUSAND));
        }
    }

    @Test
    @DisplayName("Проверка жадного алгоритма от больших к меньшим номиналам")
    void testGreedyAlgorithm() {
        List<Banknote> result = strategy.calculateWithdrawal(cassettes, 6000);

        assertFalse(result.isEmpty());
        assertEquals(6000, result.stream().mapToInt(Banknote::getValue).sum());
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Проверка что список пустой если не возможно выдать сумму")
    void testCannotDispense() {
        // Выводим все банкноты кроме 500
        cassettes.getFirst().withdraw(10);
        cassettes.get(2).withdraw(10);
        cassettes.get(3).withdraw(10);

        // Пытаемся снять 350
        List<Banknote> result = strategy.calculateWithdrawal(cassettes, 350);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Проверка что отдаем пустой список если запрашиваемая сумма больше чем есть в банкомате")
    void testInsufficientBalance() {
        List<Banknote> result = strategy.calculateWithdrawal(cassettes, 1000000);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Проверка что получаем пустой список если пытаемся вывести отрицательную сумму")
    void testZeroAndNegativeAmounts() {
        assertTrue(strategy.calculateWithdrawal(cassettes, 0).isEmpty());
        assertTrue(strategy.calculateWithdrawal(cassettes, -100).isEmpty());
    }

    @Test
    @DisplayName("Should work with exact denomination match")
    void testExactDenominationMatch() {
        List<Banknote> result = strategy.calculateWithdrawal(cassettes, 5000);

        assertFalse(result.isEmpty());
        assertEquals(5000, result.stream().mapToInt(Banknote::getValue).sum());
        assertEquals(1, result.size());
        assertEquals(Denomination.FIVE_THOUSAND, result.get(0).getDenomination());
    }

    @Test
    @DisplayName("Проверка что получаем вывод разными купюрами")
    void testMultipleDenominationCombination() {
        List<Banknote> result = strategy.calculateWithdrawal(cassettes, 1600);

        assertFalse(result.isEmpty());
        assertEquals(1600, result.stream().mapToInt(Banknote::getValue).sum());

        // 1x1000 + 1x500 + 1x100
        long count1000 = result.stream().filter(b -> b.getDenomination() == Denomination.ONE_THOUSAND).count();
        long count500 = result.stream().filter(b -> b.getDenomination() == Denomination.FIVE_HUNDRED).count();
        long count100 = result.stream().filter(b -> b.getDenomination() == Denomination.ONE_HUNDRED).count();

        assertEquals(1, count1000);
        assertEquals(1, count500);
        assertEquals(1, count100);
    }

    @Test
    @DisplayName("Проверка успешного вывода средств")
    void testRespectCassetteLimits() {
        List<Banknote> result = strategy.calculateWithdrawal(cassettes, 2000);

        assertFalse(result.isEmpty());
        assertEquals(2000, result.stream().mapToInt(Banknote::getValue).sum());
    }

    @Test
    @DisplayName("Проверка что возвращенный лист является имутабельным")
    void testReturnImmutableList() {
        List<Banknote> result = strategy.calculateWithdrawal(cassettes, 1000);

        assertThrows(UnsupportedOperationException.class,
                () -> result.add(new Banknote(Denomination.ONE_HUNDRED)));
    }
}
