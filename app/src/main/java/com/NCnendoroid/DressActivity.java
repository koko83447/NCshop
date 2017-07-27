package com.NCnendoroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;

import com.NCnendoroid.constant.Constant;
import com.NCnendoroid.model.Dress;
import com.android.koko.sc.model.Cart;
import com.android.koko.sc.util.CartHelper;

public class DressActivity extends AppCompatActivity {
    private static final String TAG = "DressActivity";

    TextView tvDressName;
    TextView tvDressDesc;
    ImageView ivDressImage;
    Spinner spQuantity;
    Button bOrder;
    Dress dress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dress);

        Bundle data = getIntent().getExtras();
        dress = (Dress) data.getSerializable("dress");//序列

        Log.d(TAG, "Dress hashCode: " + dress.hashCode());

        //設置購物車連結
        setCarLink();
        //檢視
        retrieveViews();
        //產品屬性
        setDressProperties();
        //初始化 數量
        initializeQuantity();
        //訂購產品
        onOrder();
    }

    //頂部購物車連結
    private void setCarLink() {
        TextView tvCarView = (TextView)findViewById(R.id.tvCar);
        tvCarView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DressActivity.this, CarActivity.class);
                startActivity(intent);
            }
        });
    }

    //對應xml的物件
    private void retrieveViews() {
        tvDressName = (TextView) findViewById(R.id.tvDressName);
        tvDressDesc = (TextView) findViewById(R.id.tvDressDesc);
        ivDressImage = (ImageView) findViewById(R.id.ivDressPic);
        spQuantity = (Spinner) findViewById(R.id.spQuantity);
        bOrder = (Button) findViewById(R.id.bOrder);
    }

    //服飾產品屬性
    private void setDressProperties() {
        tvDressName.setText(dress.getName());//設置抓取服裝名稱
        tvDressDesc.setText(dress.getDescription());//設置抓取服裝介紹
        ivDressImage.setImageResource(this.getResources().getIdentifier(dress.getImageName(), "drawable", this.getPackageName()));//設置抓取服裝圖片
    }

    //選取數量
    private void initializeQuantity() {
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, Constant.QUANTITY_LIST);//抓取Constant.QUANTITY_LIST產品數量
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//設置拉條項目
        spQuantity.setAdapter(dataAdapter);//設置選取數量
    }

    //訂購
    private void onOrder() {
        bOrder.setOnClickListener(new OnClickListener() {//訂購按鈕
            @Override
            public void onClick(View v) {
                Cart cart = CartHelper.getCart();
                Log.d(TAG,dress.getName());//加入商品名稱
                cart.add(dress, Integer.valueOf(spQuantity.getSelectedItem().toString()));//加入商品數量
                Intent intent = new Intent(DressActivity.this, CarActivity.class);
                startActivity(intent);
            }
        });
    }
}
