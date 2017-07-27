package com.NCnendoroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.NCnendoroid.add.DressAdd;
import com.NCnendoroid.constant.Constant;
import com.NCnendoroid.model.Dress;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tvCar = (TextView)findViewById(R.id.tvCar);
        tvCar.setOnClickListener(new View.OnClickListener() {//給予頂部購物車文字按鈕功能
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarActivity.class);//啟動購物車頁面
                startActivity(intent);
            }
        });

        //ListView內容
        ListView lvDress = (ListView) findViewById(R.id.lvDress);
        lvDress.addHeaderView(getLayoutInflater().inflate(R.layout.dress_header, lvDress, false));//設置表單頂部

        DressAdd dressAdd = new DressAdd(this);
        dressAdd.updateProducts(Constant.PRODUCT_LIST);//抓取constant更新表單

        lvDress.setAdapter(dressAdd);//增加資料

        //使表單有點擊效果
        lvDress.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Dress product = Constant.PRODUCT_LIST.get(position - 1);//contest位置-1
                Intent intent = new Intent(MainActivity.this, DressActivity.class);//intent商品內容頁面
                Bundle bundle = new Bundle();
                bundle.putSerializable("dress", product);//序列
                Log.d(TAG, product.getName());//取得商品名稱
                intent.putExtras(bundle);
                startActivity(intent);//啟動商品內容頁面
            }
        });
    }
}
