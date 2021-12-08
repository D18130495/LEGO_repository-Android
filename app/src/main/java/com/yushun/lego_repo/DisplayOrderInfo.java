package com.yushun.lego_repo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yushun.lego_repo.basketOperate.BasketList;
import com.yushun.lego_repo.orderOperate.OrderDBManager;
import com.yushun.lego_repo.orderOperate.OrderItemList;
import com.yushun.lego_repo.pojo.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DisplayOrderInfo extends AppCompatActivity {
    private String orderNumber;
    ArrayList<Order> orderList = new ArrayList<Order>(); // list use to display order item list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_order_info);

        orderList.clear(); // clear the list to avoid display product many times

        int quantity = 0;
        float price = 0, itemPrice = 0, sum = 0;

        Bundle b = getIntent().getExtras();
        orderNumber = b.getString("orderNumber");
        System.out.println(orderNumber);
        OrderDBManager orderDBManager = new OrderDBManager(this);
        orderDBManager.open();

        Cursor orderCursor = orderDBManager.getItemByOrderNumber(orderNumber);

        if (orderCursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setSet_name(orderCursor.getString(1));
                order.setSet_number(orderCursor.getString(2));
                order.setSet_price(orderCursor.getString(3));
                order.setSet_quantity(orderCursor.getString(4));
                price = Float.parseFloat(orderCursor.getString(3).substring(1));
                quantity = Integer.parseInt(orderCursor.getString(4));
                itemPrice = price * quantity;
                sum = sum + itemPrice;
                orderList.add(order);
            } while (orderCursor.moveToNext());
        }

        orderDBManager.close();

        ListView orderItemView = (ListView)findViewById(R.id.orderItemList);

        OrderItemList orderItemViewList = new OrderItemList(this, orderList);
        orderItemView.setAdapter(orderItemViewList);

        TextView textViewOrderTotal = (TextView) findViewById(R.id.textViewOrderTotal);

        DecimalFormat decimalFormat = new DecimalFormat("00.00");

        textViewOrderTotal.setText("Total: " + "€" + decimalFormat.format(sum));
    }
}