package com.android.koko.sc.model;

import java.math.BigDecimal;

//可訂購之服飾價格與名稱
public interface Saleable {
    BigDecimal getPrice();

    String getName();
}
