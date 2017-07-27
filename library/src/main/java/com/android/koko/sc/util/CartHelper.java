package com.android.koko.sc.util;

import android.util.Log;

import com.android.koko.sc.model.Cart;
import com.android.koko.sc.model.Saleable;

import java.util.Map;

//在購物車執行操作前呼叫getCart
public class CartHelper {
    public static final String TAG = CartHelper.class.getSimpleName();
    private static Cart cart = new Cart();

//檢索購物車
    public static Cart getCart() {
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }

//購物車上所有商品MAP組成的字串
    public static String getCartItemString() {
        Cart cart = getCart();

        StringBuilder stringBuilder = new StringBuilder();
        String itemName;
        Integer itemCount;
        //商品名稱及其數量字串
        for (Map.Entry<Saleable, Integer> item : cart.getItemWithQuantity().entrySet()) {
            itemName = item.getKey().getName();
            itemCount = item.getValue();

            stringBuilder.append("\n"+itemName);
            stringBuilder.append("一共");
            stringBuilder.append(itemCount + "件 ");
        }

        Log.d(TAG, "getCartItemString: " + stringBuilder.toString());
        return stringBuilder.toString();
    }
}
