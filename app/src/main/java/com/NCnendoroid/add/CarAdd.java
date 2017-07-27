package com.NCnendoroid.add;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.NCnendoroid.R;
import com.NCnendoroid.constant.Constant;
import com.NCnendoroid.model.CartItem;
import com.android.koko.sc.model.Cart;
import com.android.koko.sc.util.CartHelper;

public class CarAdd extends BaseAdapter {
    private static final String TAG = "CarAdd";

    private List<CartItem> cartItems = Collections.emptyList();

    private final Context context;

    public CarAdd(Context context) {
        this.context = context;
    }

    public void updateCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public CartItem getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView tvName;
        TextView tvUnitPrice;
        TextView tvQuantity;
        TextView tvPrice;


        //如果未訂購任何服裝則顯示空著的原狀態
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.car_add, parent, false);
            tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvUnitPrice = (TextView) convertView.findViewById(R.id.tvUnitPrice);
            tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity);
            tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            convertView.setTag(new ViewHolder(tvName, tvUnitPrice, tvQuantity, tvPrice));
        } else {//如果不是空值則顯示使用者所訂購之商品
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            tvName = viewHolder.tvCartItemName;
            tvUnitPrice = viewHolder.tvCartItemUnitPrice;
            tvQuantity = viewHolder.tvCartItemQuantity;
            tvPrice = viewHolder.tvCartItemPrice;
        }

        final Cart cart = CartHelper.getCart();
        final CartItem cartItem = getItem(position);
        tvName.setText(cartItem.getProduct().getName());
        tvUnitPrice.setText(Constant.CURRENCY+String.valueOf(cartItem.getProduct().getPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));//設置價格 符號+金額
        tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
        tvPrice.setText(Constant.CURRENCY+String.valueOf(cart.getCost(cartItem.getProduct()).setScale(2, BigDecimal.ROUND_HALF_UP)));


        return convertView;
    }


    private static class ViewHolder {
        public final TextView tvCartItemName;
        public final TextView tvCartItemUnitPrice;
        public final TextView tvCartItemQuantity;
        public final TextView tvCartItemPrice;


        public ViewHolder(TextView tvCartItemName, TextView tvCartItemUnitPrice, TextView tvCartItemQuantity, TextView tvCartItemPrice) {
            this.tvCartItemName = tvCartItemName;
            this.tvCartItemUnitPrice = tvCartItemUnitPrice;
            this.tvCartItemQuantity = tvCartItemQuantity;
            this.tvCartItemPrice = tvCartItemPrice;


        }
    }
}
