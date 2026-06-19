package org.qadev.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.qadev.atm.cassette.Cassette;
import org.qadev.atm.cassette.DenominationCassette;
import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;
import org.qadev.atm.dto.DepositResult;
import org.qadev.atm.dto.WithdrawResult;
import org.qadev.atm.exception.AmountCannotBeDispensedException;
import org.qadev.atm.exception.InsufficientFundsException;
import org.qadev.atm.exception.UnsupportedDenominationException;
import org.qadev.atm.strategy.GreedyWithdrawStrategy;
import org.qadev.atm.strategy.WithdrawStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для основного модуля ATM")
class AtmTest {
    private Atm atm;
    private Map<Denomination, Cassette> cassettes;
    private WithdrawStrategy withdrawStrategy;

    @BeforeEach
    void setUp() {
        cassettes = new HashMap<>();
        cassettes.put(Denomination.ONE_HUNDRED, new DenominationCassette(Denomination.ONE_HUNDRED, 100));
        cassettes.put(Denomination.FIVE_HUNDRED, new DenominationCassette(Denomination.FIVE_HUNDRED, 100));
        cassettes.put(Denomination.ONE_THOUSAND, new DenominationCassette(Denomination.ONE_THOUSAND, 100));
        cassettes.put(Denomination.FIVE_THOUSAND, new DenominationCassette(Denomination.FIVE_THOUSAND, 100));

        withdrawStrategy = new GreedyWithdrawStrategy();
        atm = new Atm(cassettes, withdrawStrategy);
    }


    @Test
    @DisplayName("Успешное внесение банкнот разного номинала")
    void testDepositSuccess() {
        List<Banknote> banknotes = Arrays.asList(
            new Banknote(Denomination.ONE_HUNDRED),
            new Banknote(Denomination.FIVE_HUNDRED),
            new Banknote(Denomination.ONE_THOUSAND)
        );

        DepositResult result = atm.deposit(banknotes);

        assertEquals(3, result.getAcceptedBanknotes().size());
        assertEquals(1600, result.getTotalAmount());
        assertEquals(1, result.getDenominationBreakdown().get(Denomination.ONE_HUNDRED));
        assertEquals(1, result.getDenominationBreakdown().get(Denomination.FIVE_HUNDRED));
        assertEquals(1, result.getDenominationBreakdown().get(Denomination.ONE_THOUSAND));
    }

    @Test
    @DisplayName("UnsupportedDenominationException при попытке положить в банкомат купюру кассеты которой нет в АТМ")
    void testDepositUnsupportedDenomination() {
        Cassette limitedCassette = new DenominationCassette(Denomination.ONE_HUNDRED, 10);
        Map<Denomination, Cassette> limitedCassettes = new HashMap<>();
        limitedCassettes.put(Denomination.ONE_HUNDRED, limitedCassette);
        Atm limitedAtm = new Atm(limitedCassettes, withdrawStrategy);

        List<Banknote> banknotes = Arrays.asList(
            new Banknote(Denomination.ONE_HUNDRED),
            new Banknote(Denomination.FIVE_HUNDRED) // Этого нет в ATM
        );

        assertThrows(UnsupportedDenominationException.class,
                () -> limitedAtm.deposit(banknotes));
    }

    @Test
    @DisplayName("IllegalStateException при переполненной кассете в АТМ")
    void testDepositCassetteOverflow() {
        Cassette smallCassette = new DenominationCassette(Denomination.ONE_HUNDRED, 2);
        Map<Denomination, Cassette> smallCassettes = new HashMap<>();
        smallCassettes.put(Denomination.ONE_HUNDRED, smallCassette);
        Atm smallAtm = new Atm(smallCassettes, withdrawStrategy);

        // Пытаемся добавить 3 банкноты в кассету вместимостью 2
        List<Banknote> banknotes = Arrays.asList(
            new Banknote(Denomination.ONE_HUNDRED),
            new Banknote(Denomination.ONE_HUNDRED),
            new Banknote(Denomination.ONE_HUNDRED)
        );

        assertThrows(IllegalStateException.class, () -> smallAtm.deposit(banknotes));
    }

    @Test
    @DisplayName("Получение пустого результата при пустом внесении банкнот")
    void testDepositEmpty() {
        DepositResult result = atm.deposit(List.of());
        assertTrue(result.getAcceptedBanknotes().isEmpty());
        assertEquals(0, result.getTotalAmount());
    }

    @Test
    @DisplayName("Успешный вывод банкнот")
    void testWithdrawSuccess() {
        // Сначала вносим деньги 4000
        List<Banknote> deposit = Arrays.asList(
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.FIVE_HUNDRED),
            new Banknote(Denomination.FIVE_HUNDRED)
        );
        atm.deposit(deposit);

        // Снимаем 2500
        WithdrawResult result = atm.withdraw(2500);

        // Проверяем что 2х1000 и 1х500
        assertEquals(2500, result.getTotalAmount());
        assertEquals(2, result.getDenominationBreakdown().get(Denomination.ONE_THOUSAND));
        assertEquals(1, result.getDenominationBreakdown().get(Denomination.FIVE_HUNDRED));
    }

    @Test
    @DisplayName("InsufficientFundsException если в банкомате не достаточно средств для выдачи")
    void testWithdrawInsufficientFunds() {
        List<Banknote> deposit = Arrays.asList(
            new Banknote(Denomination.ONE_HUNDRED),
            new Banknote(Denomination.ONE_HUNDRED)
        );
        atm.deposit(deposit);

        assertThrows(InsufficientFundsException.class,
                () -> atm.withdraw(500)); // выводим 500 при наличии 200
    }

    @Test
    @DisplayName("AmountCannotBeDispensedException если нет нужных купюр для выдачи запрашиваемой суммы")
    void testWithdrawCannotDispense() {
        List<Banknote> deposit = Arrays.asList(
            new Banknote(Denomination.FIVE_HUNDRED),
            new Banknote(Denomination.FIVE_HUNDRED)
        );
        atm.deposit(deposit);

        assertThrows(AmountCannotBeDispensedException.class,
                () -> atm.withdraw(350));
    }

    @Test
    @DisplayName("IllegalArgumentException при выводе не корректной суммы")
    void testWithdrawNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(-100));
        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(0));
    }

    @Test
    @DisplayName("Проверка текущего баланса АТМ по всем номиналам")
    void testGetBalance() {
        List<Banknote> banknotes = Arrays.asList(
            new Banknote(Denomination.FIVE_THOUSAND),
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.ONE_HUNDRED)
        );
        atm.deposit(banknotes);

        assertEquals(6100, atm.getBalance());
    }

    @Test
    @DisplayName("Проверка детализированного баланса по каждой купюре в АТМ")
    void testGetDetailedBalance() {
        List<Banknote> banknotes = Arrays.asList(
            new Banknote(Denomination.FIVE_THOUSAND),
            new Banknote(Denomination.FIVE_THOUSAND),
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.ONE_HUNDRED)
        );
        atm.deposit(banknotes);

        Map<Denomination, Integer> detailed = atm.getDetailedBalance();

        assertEquals(2, detailed.get(Denomination.FIVE_THOUSAND));
        assertEquals(1, detailed.get(Denomination.ONE_THOUSAND));
        assertEquals(1, detailed.get(Denomination.ONE_HUNDRED));
        assertEquals(0, detailed.get(Denomination.FIVE_HUNDRED));
    }

    @Test
    @DisplayName("Проверка суммы всех купюр в кассете")
    void testGetCassetteBalance() {
        List<Banknote> banknotes = Arrays.asList(
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.ONE_THOUSAND)
        );
        atm.deposit(banknotes);

        assertEquals(2000, atm.getCassetteBalance(Denomination.ONE_THOUSAND));
        assertEquals(0, atm.getCassetteBalance(Denomination.FIVE_HUNDRED));
    }

    @Test
    @DisplayName("Проверка количества купюр в кассете")
    void testGetCassetteCount() {
        List<Banknote> banknotes = Arrays.asList(
            new Banknote(Denomination.FIVE_HUNDRED),
            new Banknote(Denomination.FIVE_HUNDRED),
            new Banknote(Denomination.FIVE_HUNDRED)
        );
        atm.deposit(banknotes);

        assertEquals(3, atm.getCassetteCount(Denomination.FIVE_HUNDRED));
        assertEquals(0, atm.getCassetteCount(Denomination.ONE_THOUSAND));
    }

    @Test
    @DisplayName("Проверка что сохраняется внесение и вывод в очереди")
    void testDepositWithdrawSequence() {
        // Первое внесение
        List<Banknote> deposit1 = Arrays.asList(
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.ONE_THOUSAND)
        );
        atm.deposit(deposit1);
        assertEquals(2000, atm.getBalance());

        // Первое снятие
        WithdrawResult withdraw1 = atm.withdraw(1000);
        assertEquals(1000, withdraw1.getTotalAmount());
        assertEquals(1000, atm.getBalance());

        // Второе внесение
        List<Banknote> deposit2 = Arrays.asList(
            new Banknote(Denomination.FIVE_HUNDRED),
            new Banknote(Denomination.FIVE_HUNDRED)
        );
        atm.deposit(deposit2);
        assertEquals(2000, atm.getBalance());

        // Второе снятие (снимаем 500)
        WithdrawResult withdraw2 = atm.withdraw(500);
        assertEquals(500, withdraw2.getTotalAmount());
        assertEquals(1500, atm.getBalance());
    }

    @Test
    @DisplayName("Проверяем что алгоритм отсчитывает сначала крупными купюрами")
    void testGreedyWithdrawStrategy() {
        List<Banknote> deposit = Arrays.asList(
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.ONE_THOUSAND),
            new Banknote(Denomination.FIVE_HUNDRED),
            new Banknote(Denomination.FIVE_HUNDRED),
            new Banknote(Denomination.ONE_HUNDRED),
            new Banknote(Denomination.ONE_HUNDRED)
        );
        atm.deposit(deposit);

        // Снимаем 3000 — жадный алгоритм использует 3x1000
        WithdrawResult result = atm.withdraw(3000);

        assertEquals(3000, result.getTotalAmount());
        assertEquals(3, result.getDenominationBreakdown().get(Denomination.ONE_THOUSAND));
    }
}
