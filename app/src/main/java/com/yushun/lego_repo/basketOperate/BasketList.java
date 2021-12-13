package com.yushun.lego_repo.basketOperate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushun.lego_repo.R;
import com.yushun.lego_repo.pojo.Basket;
import com.yushun.lego_repo.pojo.Set;
import com.yushun.lego_repo.utils.LoadImage;

import java.util.ArrayList;

public class BasketList extends ArrayAdapter {
    private ArrayList<Basket> basketList;
    private Activity context;

    public BasketList(Activity context, ArrayList<Basket> basketList) {
        super(context, R.layout.row_item_basket, basketList);
        this.context = context;
        this.basketList = basketList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item_basket, null, true);

        TextView textViewBasketName = (TextView) row.findViewById(R.id.textViewBasketName);
        TextView textViewBasketNumber = (TextView) row.findViewById(R.id.textViewBasketNumber);
        TextView textViewBasketPrice = (TextView) row.findViewById(R.id.textViewBasketPrice);
        TextView textViewBasketQuantity = (TextView) row.findViewById(R.id.textViewBasketQuantity);
        ImageView imageViewBasketImage = (ImageView) row.findViewById(R.id.imageViewBasketImage);

        textViewBasketName.setText(basketList.get(position).getSet_name());
        textViewBasketNumber.setText(basketList.get(position).getSet_number());
        textViewBasketPrice.setText(basketList.get(position).getSet_price());
        textViewBasketQuantity.setText(basketList.get(position).getSet_quantity());
        imageViewBasketImage.setTag(basketList.get(position).getSet_image());

        new LoadImage(imageViewBasketImage).execute();

        return row;
    }
}