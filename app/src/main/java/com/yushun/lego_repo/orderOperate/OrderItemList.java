package com.yushun.lego_repo.orderOperate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yushun.lego_repo.R;
import com.yushun.lego_repo.pojo.Order;


import java.util.ArrayList;

public class OrderItemList extends ArrayAdapter {
    private ArrayList<Order> orderList;
    private Activity context;

    public OrderItemList(Activity context, ArrayList<Order> orderList) {
        super(context, R.layout.row_item_order_item, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item_order_item, null, true);

        TextView textViewOrderName = (TextView) row.findViewById(R.id.textViewOrderName);
        TextView textViewOrderNumber = (TextView) row.findViewById(R.id.textViewOrderNumber);
        TextView textViewOrderPrice = (TextView) row.findViewById(R.id.textViewOrderPrice);
        TextView textViewOrderQuantity = (TextView) row.findViewById(R.id.textViewOrderQuantity);

        textViewOrderName.setText(orderList.get(position).getSet_name());
        textViewOrderNumber.setText(orderList.get(position).getSet_number());
        textViewOrderPrice.setText(orderList.get(position).getSet_price());
        textViewOrderQuantity.setText("Quantity: " + orderList.get(position).getSet_quantity());

        return row;
    }
}