package com.android.koko.sc.exception;

/**
 * Throw this exception to indicate invalid quantity to be used on a shopping cart product.
 */
public class OutOfRange extends RuntimeException {
    private static final long serialVersionUID = 44L;

    private static final String DEFAULT_MESSAGE = "訂購數量已超過最大值";

    public OutOfRange() {
        super(DEFAULT_MESSAGE);
    }

    public OutOfRange(String message) {
        super(message);
    }
}
