package com.android.koko.sc.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.koko.sc.demo.adapter.ProductAdapter;
import com.android.koko.sc.demo.constant.Constant;
import com.android.koko.sc.demo.model.Product;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvViewShoppingCart = (TextView)findViewById(R.id.tvViewShoppingCart);
        SpannableString content = new SpannableString(getText(R.string.shopping_cart));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvViewShoppingCart.setText(content);
        tvViewShoppingCart.setOnClickListener(new View.OnClickListener() {//給予頂部購物車文字按鈕功能
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);//啟動購物車頁面
                startActivity(intent);
            }
        });

        //ListView內容
        ListView lvProducts = (ListView) findViewById(R.id.lvProducts);
        lvProducts.addHeaderView(getLayoutInflater().inflate(R.layout.product_list_header, lvProducts, false));//設置表單頂部

        ProductAdapter productAdapter = new ProductAdapter(this);
        productAdapter.updateProducts(Constant.PRODUCT_LIST);//抓取constant更新表單

        lvProducts.setAdapter(productAdapter);//增加資料

        //使表單有點擊效果
        lvProducts.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Product product = Constant.PRODUCT_LIST.get(position - 1);//contest位置-1
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);//intent商品內容頁面
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                Log.d(TAG, /*"View product: " +*/ product.getName());//取得商品名稱
                intent.putExtras(bundle);
                startActivity(intent);//啟動商品內容頁面
            }
        });
    }
}
