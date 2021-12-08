package com.yushun.lego_repo.orderOperate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushun.lego_repo.R;
import com.yushun.lego_repo.pojo.Basket;
import com.yushun.lego_repo.pojo.Order;
import com.yushun.lego_repo.utils.LoadImage;

import java.util.ArrayList;

public class OrderList extends ArrayAdapter {
    private ArrayList<Order> orderList;
    private Activity context;

    public OrderList(Activity context, ArrayList<Order> orderList) {
        super(context, R.layout.row_item_order, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item_order, null, true);

        TextView textViewOrderNumber = (TextView) row.findViewById(R.id.textViewOrderNumber);
        TextView textViewOrderDate = (TextView) row.findViewById(R.id.textViewOrderDate);

        textViewOrderNumber.setText("Order number: " + orderList.get(position).getOrder_number());
        textViewOrderDate.setText("Order date: " + orderList.get(position).getOrder_date());

        return row;
    }
}