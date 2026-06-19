package org.qadev.atm.exception;

public class AmountCannotBeDispensedException extends RuntimeException {

    public AmountCannotBeDispensedException(long requestedAmount) {
        super(
                "ERR: Выдача денежных средств в сумме " + requestedAmount + " рублей невозможна!"
        );
    }

}
