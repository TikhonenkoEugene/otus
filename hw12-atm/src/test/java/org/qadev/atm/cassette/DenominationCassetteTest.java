package org.qadev.atm.cassette;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.qadev.atm.domain.Banknote;
import org.qadev.atm.domain.Denomination;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты кассет с банкнотами")
class DenominationCassetteTest {
    private Cassette cassette;

    @BeforeEach
    void setUp() {
        cassette = new DenominationCassette(Denomination.ONE_THOUSAND, 50);
    }

    @Test
    @DisplayName("Проверка успешного внесения средств")
    void testDepositSuccess() {
        Banknote banknote = new Banknote(Denomination.ONE_THOUSAND);
        cassette.deposit(banknote);

        assertEquals(1, cassette.getCount());
        assertEquals(1000, cassette.getTotalValue());
    }

    @Test
    @DisplayName("IllegalArgumentException при попытке положить в кассету не поддерживаемую банкноту")
    void testDepositWrongDenomination() {
        Banknote banknote = new Banknote(Denomination.FIVE_HUNDRED);

        assertThrows(IllegalArgumentException.class, () -> cassette.deposit(banknote));
    }

    @Test
    @DisplayName("IllegalStateException при попытке добавить банкноту в переполненную кассету")
    void testDepositFull() {
        Cassette fullCassette = new DenominationCassette(Denomination.ONE_THOUSAND, 1);
        fullCassette.deposit(new Banknote(Denomination.ONE_THOUSAND));
        Banknote banknote = new Banknote(Denomination.ONE_THOUSAND);

        assertThrows(IllegalStateException.class, () -> fullCassette.deposit(banknote));
    }

    @Test
    @DisplayName("Успешный вывод банкнот из кассеты")
    void testWithdrawSuccess() {
        for (int i = 0; i < 5; i++) {
            Banknote banknote = new Banknote(Denomination.ONE_THOUSAND);
            cassette.deposit(banknote);
        }

        List<Banknote> withdrawn = cassette.withdraw(3);

        assertEquals(3, withdrawn.size());  // выведено
        assertEquals(2, cassette.getCount()); // остаток
        assertEquals(2000, cassette.getTotalValue()); // сумма
    }

    @Test
    @DisplayName("IllegalStateException при попытке вывести из кассеты больше банкнот чем она содержит")
    void testWithdrawInsufficientBanknotes() {
        Banknote banknote = new Banknote(Denomination.ONE_THOUSAND);
        cassette.deposit(banknote);

        assertThrows(IllegalStateException.class, () -> cassette.withdraw(5));
    }

    @Test
    @DisplayName("Проверка номинала кассеты")
    void testGetDenomination() {
        assertEquals(Denomination.ONE_THOUSAND, cassette.getDenomination());
    }

    @Test
    @DisplayName("Проверка максимальной емкости кассеты")
    void testGetMaxCapacity() {
        assertEquals(50, cassette.getMaxCapacity());
    }

    @Test
    @DisplayName("Проверка доступности внесения купюр в кассету если она не переполнена")
    void testCanDeposit() {
        assertTrue(cassette.canDeposit());

        Cassette fullCassette = new DenominationCassette(Denomination.ONE_THOUSAND, 1);
        Banknote banknote = new Banknote(Denomination.ONE_THOUSAND);
        fullCassette.deposit(banknote);

        assertFalse(fullCassette.canDeposit());
    }

    @Test
    @DisplayName("Проверка возможности вывода доступных купюр из кассеты")
    void testCanWithdraw() {
        assertFalse(cassette.canWithdraw(1));

        for (int i = 0; i < 2; i++) {
            Banknote banknote = new Banknote(Denomination.ONE_THOUSAND);
            cassette.deposit(banknote);
        }

        assertTrue(cassette.canWithdraw(2));
        assertFalse(cassette.canWithdraw(3));
    }

    @Test
    @DisplayName("IllegalArgumentException при указании не корректной емкости кассеты")
    void testNegativeMaxCapacity() {
        assertThrows(IllegalArgumentException.class,
                () -> new DenominationCassette(Denomination.ONE_THOUSAND, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new DenominationCassette(Denomination.ONE_THOUSAND, -10));
    }

    @Test
    @DisplayName("Проверка что вернули имутабельный лист банкнот")
    void testWithdrawImmutable() {
        cassette.deposit(new Banknote(Denomination.ONE_THOUSAND));
        cassette.deposit(new Banknote(Denomination.ONE_THOUSAND));

        List<Banknote> withdrawn = cassette.withdraw(2);

        assertThrows(UnsupportedOperationException.class,
                () -> withdrawn.add(new Banknote(Denomination.ONE_THOUSAND)));
    }
}
