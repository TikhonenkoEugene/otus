package org.qadev.atm.exception;

import org.qadev.atm.domain.Denomination;

public class UnsupportedDenominationException extends RuntimeException {

    public UnsupportedDenominationException(Denomination denomination) {
        super("ERR: Не поддерживаемый номинал: " + denomination.getValue());
    }
}
