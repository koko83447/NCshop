package com.android.koko.sc.demo.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.android.koko.sc.demo.model.Product;

public final class Constant {
    public static final List<Integer> QUANTITY_LIST = new ArrayList<Integer>();

    static {
        for (int i = 1; i < 11; i++) QUANTITY_LIST.add(i);
    }

    public static final Product PRODUCT1 = new Product(1, "Flowers1", BigDecimal.valueOf(100), "商品介紹文章11111", "ictest1");
    public static final Product PRODUCT2 = new Product(2, "Flowers2", BigDecimal.valueOf(150), "商品介紹文章22222", "ictest2");
    public static final Product PRODUCT3 = new Product(3, "Flowers3", BigDecimal.valueOf(200), "商品介紹文章33333", "ictest3");
    public static final Product PRODUCT4 = new Product(4, "Flowers1", BigDecimal.valueOf(100), "商品介紹文章11111", "ictest1");
    public static final Product PRODUCT5 = new Product(5, "花色服2", BigDecimal.valueOf(150), "商品介紹文章22222", "ictest2");
    public static final Product PRODUCT6 = new Product(6, "花色服3", BigDecimal.valueOf(200), "商品介紹文章33333", "ictest3");
    public static final Product PRODUCT7 = new Product(7, "Flowers1", BigDecimal.valueOf(100), "商品介紹文章11111", "ictest1");
    public static final Product PRODUCT8 = new Product(8, "花色服2", BigDecimal.valueOf(150), "商品介紹文章22222", "ictest2");
    public static final Product PRODUCT9 = new Product(9, "花色服3", BigDecimal.valueOf(200), "商品介紹文章33333", "ictest3");
    public static final Product PRODUCT10 = new Product(10, "Flowers1", BigDecimal.valueOf(100), "商品介紹文章11111", "ictest1");
    public static final Product PRODUCT11 = new Product(11, "花色服2", BigDecimal.valueOf(150), "商品介紹文章22222", "ictest2");
    public static final Product PRODUCT12 = new Product(12, "花色服3", BigDecimal.valueOf(200), "商品介紹文章33333", "ictest3");

    public static final List<Product> PRODUCT_LIST = new ArrayList<Product>();

    static {
        PRODUCT_LIST.add(PRODUCT1);
        PRODUCT_LIST.add(PRODUCT2);
        PRODUCT_LIST.add(PRODUCT3);
        PRODUCT_LIST.add(PRODUCT4);
        PRODUCT_LIST.add(PRODUCT5);
        PRODUCT_LIST.add(PRODUCT6);
        PRODUCT_LIST.add(PRODUCT7);
        PRODUCT_LIST.add(PRODUCT8);
        PRODUCT_LIST.add(PRODUCT9);
        PRODUCT_LIST.add(PRODUCT10);
        PRODUCT_LIST.add(PRODUCT11);
        PRODUCT_LIST.add(PRODUCT12);
    }

    public static final String CURRENCY = "NT$";
}
