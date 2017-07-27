package com.NCnendoroid.add;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.NCnendoroid.R;
import com.NCnendoroid.constant.Constant;
import com.NCnendoroid.model.Dress;

public class DressAdd extends BaseAdapter {
    private static final String TAG = "DressAdd";

    private List<Dress> products = new ArrayList<Dress>();

    private final Context context;

    public DressAdd(Context context) {
        this.context = context;
    }

    public void updateProducts(List<Dress> products) {
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Dress getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvName;
        TextView tvPrice;
        ImageView ivImage;
        if (convertView == null) {//如果沒有服裝商品則顯示原本空的狀態
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.dress_add, parent, false);
            tvName = (TextView) convertView.findViewById(R.id.tvDressName);
            tvPrice = (TextView) convertView.findViewById(R.id.tvDressPrice);
            ivImage = (ImageView) convertView.findViewById(R.id.ivDressPic);
            convertView.setTag(new ViewHolder(tvName, tvPrice, ivImage));
        } else {//如果有則顯示服飾商品資訊
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            tvName = viewHolder.tvProductName;
            tvPrice = viewHolder.tvProductPrice;
            ivImage = viewHolder.ivProductImage;
        }

        final Dress product = getItem(position);
        tvName.setText(product.getName());
        tvPrice.setText(Constant.CURRENCY+String.valueOf(product.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));//商品價格
        Log.d(TAG, "Context package name: " + context.getPackageName());
        ivImage.setImageResource(context.getResources().getIdentifier(
                product.getImageName(), "drawable", context.getPackageName()));//圖片抓取drawable資料夾

        return convertView;
    }

    private static class ViewHolder {
        public final TextView tvProductName;
        public final TextView tvProductPrice;
        public final ImageView ivProductImage;

        public ViewHolder(TextView tvProductName, TextView tvProductPrice, ImageView ivProductImage) {
            this.tvProductName = tvProductName;
            this.tvProductPrice = tvProductPrice;
            this.ivProductImage = ivProductImage;
        }
    }
}
