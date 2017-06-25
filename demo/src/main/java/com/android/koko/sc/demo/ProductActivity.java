package com.android.koko.sc.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import com.android.koko.sc.demo.constant.Constant;
import com.android.koko.sc.demo.model.Product;
import com.android.koko.sc.model.Cart;
import com.android.koko.sc.util.CartHelper;

public class ProductActivity extends AppCompatActivity {
    private static final String TAG = "ProductActivity";

    TextView tvProductName;
    TextView tvProductDesc;
    ImageView ivProductImage;
    Spinner spQuantity;
    Button bOrder;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);

        Bundle data = getIntent().getExtras();
        product = (Product) data.getSerializable("product");

        Log.d(TAG, "Product hashCode: " + product.hashCode());

        //設置購物車連結
        setShoppingCartLink();

        //檢視
        retrieveViews();

        //產品屬性
        setProductProperties();

        //初始化 數量
        initializeQuantity();

        //訂購產品
        onOrderProduct();
    }

    //頂部購物車連結
    private void setShoppingCartLink() {
        TextView tvViewShoppingCart = (TextView)findViewById(R.id.tvViewShoppingCart);
        SpannableString content = new SpannableString(getText(R.string.shopping_cart));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvViewShoppingCart.setText(content);
        tvViewShoppingCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }

    //對應xml的物件
    private void retrieveViews() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvProductDesc = (TextView) findViewById(R.id.tvProductDesc);
        ivProductImage = (ImageView) findViewById(R.id.ivProductImage);
        spQuantity = (Spinner) findViewById(R.id.spQuantity);
        bOrder = (Button) findViewById(R.id.bOrder);
    }

    private void setProductProperties() {
        tvProductName.setText(product.getName());//設置抓取名稱
        tvProductDesc.setText(product.getDescription());//設置抓取內文
        ivProductImage.setImageResource(this.getResources().getIdentifier(product.getImageName(), "drawable", this.getPackageName()));//設置抓取圖片
    }

    private void initializeQuantity() {
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, Constant.QUANTITY_LIST);//抓取Constant.QUANTITY_LIST產品數量
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//設置拉條項目
        spQuantity.setAdapter(dataAdapter);//設置數量
    }

    private void onOrderProduct() {
        bOrder.setOnClickListener(new OnClickListener() {//訂購按鈕
            @Override
            public void onClick(View v) {
                Cart cart = CartHelper.getCart();
                Log.d(TAG, /*"Adding product: " + */product.getName());//加入商品名稱
                cart.add(product, Integer.valueOf(spQuantity.getSelectedItem().toString()));//加入商品數量
                Intent intent = new Intent(ProductActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }
}
