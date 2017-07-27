package com.NCnendoroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.NCnendoroid.add.CarAdd;
import com.NCnendoroid.constant.Constant;
import com.NCnendoroid.model.CartItem;
import com.NCnendoroid.model.Dress;
import com.android.koko.sc.model.Cart;
import com.android.koko.sc.model.Saleable;
import com.android.koko.sc.util.CartHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CarActivity extends AppCompatActivity {
    private static final String TAG = "CarActivity";

    ListView lvCarDress;
    Button bClear;
    Button bShop;
    Button bBuy;
    TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvCarDress = (ListView) findViewById(R.id.lvItem);
        LayoutInflater inflater = getLayoutInflater();//下的xml用來抓取layout

        final Cart cart = CartHelper.getCart();
        final TextView tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);

        tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));//設置總額 金額符號+價格

        lvCarDress.addHeaderView(inflater.inflate(R.layout.car_header, lvCarDress, false));//加入表單頂部

        final CarAdd carAdd = new CarAdd(this);
        carAdd.updateCartItems(getCartItems(cart));

        lvCarDress.setAdapter(carAdd);

        bClear = (Button) findViewById(R.id.bClear);
        bShop = (Button) findViewById(R.id.bShop);
        bBuy = (Button) findViewById(R.id.bBuy);

        //清除按鈕
        bClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clearing the shopping cart");
                cart.clear();//清除購物車
                carAdd.updateCartItems(getCartItems(cart));//更新購物車內容
                carAdd.notifyDataSetChanged();//改變購物車內容
                tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));//總價格更新
            }
        });

        //回去逛逛按鈕
        bShop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //長按商品刪除項目
        lvCarDress.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(CarActivity.this)
                        .setTitle(getResources().getString(R.string.delete_item))
                        .setMessage(getResources().getString(R.string.delete_item_message))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<CartItem> cartItems = getCartItems(cart);
                                cart.remove(cartItems.get(position-1).getProduct());
                                cartItems.remove(position-1);//移除商品
                                carAdd.updateCartItems(cartItems);//更新購物車
                                carAdd.notifyDataSetChanged();//改變購物車內容
                                tvTotalPrice.setText(Constant.CURRENCY+String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), null)//選否為空值不做動作
                        .show();
                return true;
            }
        });

        //點擊商品
        lvCarDress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                List<CartItem> cartItems = getCartItems(cart);
                Dress product = cartItems.get(position-1).getProduct();
                Log.d(TAG, "Viewing dress: " + product.getName());
                bundle.putSerializable("dress", product);
                Intent intent = new Intent(CarActivity.this, DressActivity.class);//該服飾頁面
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

                    it.putExtra(Intent.EXTRA_TEXT, "您好,\n您訂購的商品為" + CartHelper.getCartItemString() +
                                    "\n您訂購的總金額為" + Constant.CURRENCY + String.valueOf(cart.getTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)) +
                    "\n如訂單沒問題請發送此信件並等待我的回信\n謝謝您的訂購,我會竟快回覆您");

                startActivity(it);
            }
        });


    }


    private   List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        Log.d(TAG, "Current shopping cart: " + cart);

        Map<Saleable, Integer> itemMap = cart.getItemWithQuantity();//取得商品與數量

        for (Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Dress) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }


        Log.d(TAG, "Cart item list: " + cartItems);
        return cartItems;
    }




}
