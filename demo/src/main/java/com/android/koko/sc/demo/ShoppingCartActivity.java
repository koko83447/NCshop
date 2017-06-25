package com.android.koko.sc.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.koko.sc.demo.adapter.CartItemAdapter;
import com.android.koko.sc.demo.constant.Constant;
import com.android.koko.sc.demo.model.CartItem;
import com.android.koko.sc.demo.model.Product;
import com.android.koko.sc.model.Cart;
import com.android.koko.sc.model.Saleable;
import com.android.koko.sc.util.CartHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.Attributes;

public class ShoppingCartActivity extends AppCompatActivity {
    private static final String TAG = "ShoppingCartActivity";

    ListView lvCartItems;
    Button bClear;
    Button bShop;
    Button bBuy;
    TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        lvCartItems = (ListView) findViewById(R.id.lvCartItems);
        LayoutInflater layoutInflater = getLayoutInflater();

        final Cart cart = CartHelper.getCart();
        final TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);


        tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));//設置總額 金額符號+價格

        lvCartItems.addHeaderView(layoutInflater.inflate(R.layout.cart_header, lvCartItems, false));//加入表單頂部

        final CartItemAdapter cartItemAdapter = new CartItemAdapter(this);
        cartItemAdapter.updateCartItems(getCartItems(cart));

        lvCartItems.setAdapter(cartItemAdapter);

        bClear = (Button) findViewById(R.id.bClear);
        bShop = (Button) findViewById(R.id.bShop);
        bBuy = (Button) findViewById(R.id.bBuy);

        bClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clearing the shopping cart");
                cart.clear();//清除購物車
                cartItemAdapter.updateCartItems(getCartItems(cart));//更新購物車內容
                cartItemAdapter.notifyDataSetChanged();//改變購物車內容
                tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));//總價格更新
            }
        });

        bShop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        //長按商品
        lvCartItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ShoppingCartActivity.this)
                        .setTitle(getResources().getString(R.string.delete_item))
                        .setMessage(getResources().getString(R.string.delete_item_message))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<CartItem> cartItems = getCartItems(cart);
                                cart.remove(cartItems.get(position-1).getProduct());
                                cartItems.remove(position-1);
                                cartItemAdapter.updateCartItems(cartItems);
                                cartItemAdapter.notifyDataSetChanged();
                                tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)
                        .show();
                return true;
            }
        });

        //點擊商品
        lvCartItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                List<CartItem> cartItems = getCartItems(cart);
                Product product = cartItems.get(position-1).getProduct();
                Log.d(TAG, "Viewing product: " + product.getName());
                bundle.putSerializable("product", product);
                Intent intent = new Intent(ShoppingCartActivity.this, ProductActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        bBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_VIEW);

                it.setData(Uri.parse("mailto:koko83447@gmail.com"));
                //it.putExtra(Intent.EXTRA_CC,new String[] {"請輸入您的電子郵件"});
                it.putExtra(Intent.EXTRA_SUBJECT,"余月黏土人服飾訂購");

                    it.putExtra(Intent.EXTRA_TEXT, "您好,\n您訂購的總金額為" + Constant.CURRENCY + String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)) +
                            "\n您訂購的商品為" + cart.getItemWithQuantity() +
                    "\n新字串" );

                startActivity(it);
            }
        });

    }


    private   List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        Log.d(TAG, "Current shopping cart: " + cart);

        Map<Saleable, Integer> itemMap = cart.getItemWithQuantity();

        for (Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }

        Log.d(TAG, "Cart item list: " + cartItems);
        return cartItems;
    }



}
