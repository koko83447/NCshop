package com.NCnendoroid.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.NCnendoroid.model.Dress;

public final class Constant {
    //商品數量
    public static final List<Integer> QUANTITY_LIST = new ArrayList<Integer>();
    static {//最多10個
        for (int i = 1; i < 11; i++) QUANTITY_LIST.add(i);
    }

    //順序,名稱,價格,介紹,圖片
    public static final Dress PRODUCT1 = new Dress(1, "紅梅花", BigDecimal.valueOf(300), "梅花紅色花布,搭配紫色梅花花布腰間與領口", "kimono1");
    public static final Dress PRODUCT2 = new Dress(2, "紫梅花", BigDecimal.valueOf(300), "梅花紫色花布,搭配黑色梅花花布腰間與領口", "kimono2");
    public static final Dress PRODUCT3 = new Dress(3, "黑梅花", BigDecimal.valueOf(300), "梅花黑色花布,搭配紅色梅花花布腰間與領口", "kimono3");
    public static final Dress PRODUCT4 = new Dress(4, "大紅花", BigDecimal.valueOf(300), "較軟的白底紅花花布", "kimono4");
    public static final Dress PRODUCT5 = new Dress(5, "康乃馨白", BigDecimal.valueOf(300), "白底康乃馨花布", "kimono5");
    public static final Dress PRODUCT6 = new Dress(6, "康乃馨粉紅", BigDecimal.valueOf(300), "粉紅底康乃馨花布", "kimono6");
    public static final Dress PRODUCT7 = new Dress(7, "櫻花", BigDecimal.valueOf(300), "櫻花散落花布,搭配黑梅花花布腰間與領口", "kimono7");
    public static final Dress PRODUCT8 = new Dress(8, "小黃花", BigDecimal.valueOf(300), "較軟的小黃花花布", "kimono8");
    public static final Dress PRODUCT9 = new Dress(9, "紅海波", BigDecimal.valueOf(300), "紅色的海波布", "kimono9");
    public static final Dress PRODUCT10 = new Dress(10, "藍海波", BigDecimal.valueOf(300), "深藍色的海波布", "kimono10");

    public static final List<Dress> PRODUCT_LIST = new ArrayList<Dress>();

    static {//放到mainactivity商品目錄
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
    }
    //金額前顯示幣值
    public static final String CURRENCY = "NT$";
}
