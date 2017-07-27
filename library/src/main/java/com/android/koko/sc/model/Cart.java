package com.android.koko.sc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.android.koko.sc.exception.NotFound;
import com.android.koko.sc.exception.OutOfRange;

//購物車序列
public class Cart implements Serializable {
    private static final long serialVersionUID = 42L;

    private Map<Saleable, Integer> cartItemMap = new HashMap<Saleable, Integer>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private int totalQuantity = 0;

    //向購物車添加數量,服飾可被加入到購物車中.添加數量
    public void add(final Saleable sellable, int quantity) {
        if (cartItemMap.containsKey(sellable)) {//如果商品是可售出的狀態
            cartItemMap.put(sellable, cartItemMap.get(sellable) + quantity);//添加數量
        } else {//若不是將保持原數值
            cartItemMap.put(sellable, quantity);
        }

        totalPrice = totalPrice.add(sellable.getPrice().multiply(BigDecimal.valueOf(quantity)));//總價格加上
        totalQuantity += quantity;//總數量
    }

    //更新添加商品新數量至購物車,產品更新.更新分配.找不到的顯示.訂購數量變成負數
    public void update(final Saleable sellable, int quantity) throws NotFound, OutOfRange {
        if (!cartItemMap.containsKey(sellable)) throw new NotFound();//如果購物車map中沒有這項產品
        if (quantity < 0)//數量如果小於0
            throw new OutOfRange(quantity + "非有效數量");

        int productQuantity = cartItemMap.get(sellable);
        BigDecimal productPrice = sellable.getPrice().multiply(BigDecimal.valueOf(productQuantity));//商品價格

        cartItemMap.put(sellable, quantity);//更新數量

        totalQuantity = totalQuantity - productQuantity + quantity;//總數量為原總數量扣除商品數量再添加新數量
        totalPrice = totalPrice.subtract(productPrice).add(sellable.getPrice().multiply(BigDecimal.valueOf(quantity)));//總價更新
    }

    //從購物車刪除數量,刪除商品.移除刪除掉商品的數量.如果在購物車中找不到則顯示找不到.若數量為負數或超過
    public void remove(final Saleable sellable, int quantity) throws NotFound, OutOfRange {
        if (!cartItemMap.containsKey(sellable)) throw new NotFound();//若商品不再購物車中則not found

        int productQuantity = cartItemMap.get(sellable);

        if (quantity < 0 || quantity > productQuantity)//如果產品數量小於0或大於商品數量
            throw new OutOfRange(quantity + "非有效數量.並且小於商品當前數量");

        if (productQuantity == quantity) {
            cartItemMap.remove(sellable);
        } else {
            cartItemMap.put(sellable, productQuantity - quantity);
        }

        totalPrice = totalPrice.subtract(sellable.getPrice().multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    //刪除產品,移除產品.若找不到則NotFound
    public void remove(final Saleable sellable) throws NotFound {
        if (!cartItemMap.containsKey(sellable)) throw new NotFound();

        int quantity = cartItemMap.get(sellable);
        cartItemMap.remove(sellable);
        totalPrice = totalPrice.subtract(sellable.getPrice().multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    //清除所有
    public void clear() {
        cartItemMap.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    //在購物車中獲取數量
    public int getQuantity(final Saleable sellable) throws NotFound {
        if (!cartItemMap.containsKey(sellable)) throw new NotFound();
        return cartItemMap.get(sellable);
    }

    //商品的費用
    public BigDecimal getCost(final Saleable sellable) throws NotFound {
        if (!cartItemMap.containsKey(sellable)) throw new NotFound();
        return sellable.getPrice().multiply(BigDecimal.valueOf(cartItemMap.get(sellable)));
    }

    //總價
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    //總數
    public int getTotalQuantity() {
        return totalQuantity;
    }

    //商品
    public Set<Saleable> getProducts() {
        return cartItemMap.keySet();
    }

    //商品及其對應的數量
    public Map<Saleable, Integer> getItemWithQuantity() {
        Map<Saleable, Integer> cartItemMap = new HashMap<Saleable, Integer>();
        cartItemMap.putAll(this.cartItemMap);
        return cartItemMap;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (Entry<Saleable, Integer> entry : cartItemMap.entrySet()) {
            strBuilder.append(String.format("Product: %s, Unit Price: %f, Quantity: %d%n", entry.getKey().getName(), entry.getKey().getPrice(), entry.getValue()));
        }
        strBuilder.append(String.format("Total Quantity: %d, Total Price: %f", totalQuantity, totalPrice));

        return strBuilder.toString();
    }
}
