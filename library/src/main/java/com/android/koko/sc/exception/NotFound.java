package com.android.koko.sc.exception;

/**
 * Throw this exception to indicate invalid operation on product which does not belong to a shopping cart
 */
public class NotFound extends RuntimeException {
    private static final long serialVersionUID = 43L;

    private static final String DEFAULT_MESSAGE = "購物車中找不到這件商品";

    public NotFound() {
        super(DEFAULT_MESSAGE);
    }

    public NotFound(String message) {
        super(message);
    }
}
