package org.qadev.atm.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(long requestedAmount, long availableBalance) {
        super(
                "ERR: Недостаток средств: запрошено " + requestedAmount + " руб, доступно " + availableBalance + " руб."
        );
    }
}
